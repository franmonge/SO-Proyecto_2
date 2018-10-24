/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import Config_Enums.Addressing;
import Config_Enums.Format_Content;
import Config_Enums.Format_Length;
import Config_Enums.MailBox_Discipline;
import Config_Enums.Priority;
import Config_Enums.Record_Message_Action;
import Config_Enums.Request_Action;
import Config_Enums.Sync_Receive;
import Config_Enums.Sync_Send;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Controller {
    private static Controller instance = null;
    private Configuration configs;
    private ArrayList<Proceso> procesos;
    private ArrayList<Proceso> suscritores;
    private ArrayList<MailBox> mailboxes;
    private ArrayList<Mensaje> systemMailBox;
    private ArrayList<Request> requests;
    public static Integer messageIDCounter;
    
    private Controller(){
        procesos = new ArrayList<Proceso>();
        suscritores = new ArrayList<Proceso>();
        mailboxes = new ArrayList<MailBox>();
        requests = new ArrayList<Request>();
        systemMailBox = new ArrayList<Mensaje>();
        messageIDCounter = 0;
    }
    
    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        return instance;
    }
    
    public void resetSystem(){
        configs = null;
        procesos.clear();
        mailboxes.clear();
        requests.clear();
        messageIDCounter = 0;
    }
    
    public static Integer getMessageCounter(){
        messageIDCounter++;
        return messageIDCounter;
    }
    
    public Integer remainingMessages(MailBox mail){
        return mail.getCantidadMensajesMaxima()-mail.getBufferMensajes().size();
    }
    
    public void receiveMessage(String sourceID, String destinationID){
        Proceso sender = getProcess(destinationID);
        Proceso receiver = getProcess(sourceID);
        
        if(isThereAPendingMessageOnMailBox(destinationID, sourceID)){
            System.out.println("Existing message on MailBox to be delivered");

            Mensaje message = getMessageFromMailBox(sourceID, destinationID);
            sender = getProcess(message.getSourceID());
            System.out.println("Was the received message null? " + String.valueOf(message == null));
            
            receiver.getRecordHistory().add(new MessageRecord(Record_Message_Action.RECEIVED, message));
        }
    }
    
    private boolean isThereAPendingMessageOnMailBox(String destinationID, String sourceID){
        if(isThereAnAccusementRequest(destinationID, sourceID))
            return true;
        
        if(getMailBox(destinationID).getBufferMensajes().size()> 0)
            return true;
        
        return false;
    }
    
    private Mensaje getMessageFromMailBox(String sourceID, String destinationID){
        Mensaje pickedMessage = null;
        MailBox mail = getMailBox(destinationID);
        
        if(mail.getBufferMensajes().size() > 0){ 
            pickedMessage = mail.getBufferMensajes().get(0);
            if(configs.getDiscipline().equals(MailBox_Discipline.FIFO)){
                mail.getBufferMensajes().remove(0);
            }else{ // Priority
                for(Mensaje message: mail.getBufferMensajes()){
                    //if(message.getPrioridad() > pickedMessage.getPrioridad()){
                        pickedMessage = message;
                    //}
                }
                mail.getBufferMensajes().remove(pickedMessage);
            }
        }
        return pickedMessage;
    }     
    
    private boolean isThereAnAccusementRequest(String sourceID, String destinationID){
        Request req = null;
        for(Request request: requests){
            if(request.getDestinationID().equals(sourceID) && request.getSourceID().equals(destinationID)  && request.getAction() == Request_Action.ACCUSEMENT) {    
                req = request;
            }
        }
        if(req != null)
            return true;
        return false;
    }
    
    private boolean isThereAPendingMessageOnSystemMailBox(String destinationID, String sourceID){
        if(isThereAnAccusementRequest(destinationID, sourceID)){
            System.out.println("There's a accusement request for this receive request");
            return true;
        }
        
        for(Mensaje mensaje: systemMailBox)
            if(mensaje.getDestinationID().equals(sourceID) && mensaje.getSourceID().equals(destinationID)){
                //System.out.println("The requested message already exists in the system mailbox");
                return true;
            }
        
        if(configs.getAddressing().equals(Addressing.IMPLICIT)){
            for(Mensaje mensaje: systemMailBox)
                if(mensaje.getDestinationID().equals(sourceID)){
                    System.out.println("IMPLICIT: The requested message already exists in the system mailbox");
                    return true;
                }
        }        
        return false;
    }
    
    private void removeRequest(Mensaje mensaje, Request_Action action){
        if(requests.size() > 0){
            Request toBeDeleted = null;
            boolean directAddressing = (Addressing.EXPLICIT.equals(configs.getAddressing()) || Addressing.IMPLICIT.equals(configs.getAddressing()));
            for(Request request: requests){
                if(directAddressing){
                    if(request.getSourceID().equals(mensaje.getDestinationID()) && request.getDestinationID().equals(mensaje.getSourceID()) && request.getAction() == action){
                        System.out.println("Request to be deleted sourceID: "+ request.getSourceID() + " destinationID: "+ request.getDestinationID() + " type: " + request.getAction().toString());
                        toBeDeleted = request;//requests.remove(request);
                        break;
                    }
                    if(action == Request_Action.ACCUSEMENT){
                        if(request.getSourceID().equals(mensaje.getSourceID()) && request.getDestinationID().equals(mensaje.getDestinationID()) && request.getAction() == action){
                            System.out.println("Request to be deleted sourceID: "+ request.getSourceID() + " destinationID: "+ request.getDestinationID() + " type: " + request.getAction().toString());
                            toBeDeleted = request;//requests.remove(request);
                        }       
                    }
                }
                else{ // Indirect addressing
                    if(request.getDestinationID().equals(mensaje.getDestinationID()) && request.getAction().equals(action)){
                        System.out.println("Request to be deleted sourceID: "+ request.getSourceID() + " destinationID: "+ request.getDestinationID() + " type: " + request.getAction().toString());
                        toBeDeleted = request;//requests.remove(request);
                    }
                }
            }
            if(toBeDeleted != null){
                requests.remove(toBeDeleted);
                System.out.println("Request summary");
                for(Request req: requests)
                    System.out.println("Req SOURCEID: " + req.getSourceID() + " DESTINATIONID: " + req.getDestinationID() + " TYPE: " +req.getAction().toString());
            }
        }
    }
    
    public void sendMessage(Mensaje mensaje){ // Abarca tanto addressing direct como indirect        
        getProcess(mensaje.getSourceID()).getRecordHistory().add(new MessageRecord(Record_Message_Action.SENT, mensaje));
        System.out.println("Message sent to MailBox: " + getMailBox(mensaje.getDestinationID()).getIdMailBox());
        getMailBox(mensaje.getDestinationID()).addMessage(mensaje);  // Add MessageRecord
    }
    
    
    private Proceso getDestinationProcessFromIndirectAddressing(Mensaje message){
        Proceso process = null;
        for(Request req: requests){
            if(req.getDestinationID().equals(message.getDestinationID()) && req.getAction().equals(Request_Action.RECEIVE)){
                process = getProcess(req.getSourceID());
            }
        }
        return process;
    }
    
    private Proceso getSourceProcessFromIndirectAddressing(Mensaje message){
        return getProcess(message.getSourceID());
    }
    
    private Request getRequestFromSource(String destinationID){
        for(Request request: requests){
            if(request.getSourceID().equals(destinationID)){
                return request;
            }
        }
        return null;
    }
    
    private boolean messageWasExpected(Mensaje message){
        if (requests.isEmpty())
            return false;
        else{
             //Si viene de addressing indirecto
                System.out.println("Indirect message evaluation for expected message or not");
                for(Request request: requests){
                    System.out.println("Request source: " + request.getSourceID() + " destination: "+ request.getDestinationID() + " action: " + request.getAction().toString());
                    if(request.getAction().equals(Request_Action.RECEIVE) && request.getDestinationID().equals(message.getDestinationID()))
                        return true;
                }
            
            return false;
        }
    }

    public MailBox getMailBox(String mailBoxID){
        for(MailBox mail: mailboxes){
            if (mailBoxID.equals(mail.getIdMailBox()))
                return mail;
        }
        return null;
    }
    
    public Proceso getProcess(String processID){
        for(Proceso proceso: procesos){
            if (processID.equals(proceso.getIdProceso()))
                return proceso;
        }
        return null;
    }
    
    public void setConfiguration(Sync_Receive receive, Sync_Send send, Addressing addressing, Format_Content content, Format_Length length, MailBox_Discipline discipline, Priority priority, Integer bufferSize){
        configs = new Configuration(receive, send, addressing, content, length, discipline, priority, bufferSize);
    }
    
    public Configuration getConfiguration(){
        return configs;
    }

    public ArrayList<Proceso> getSuscritores() {
        return suscritores;
    }
    
    public void addProcess(Proceso process){
        procesos.add(process);
    }
    
    public void addSubscriber(Proceso subscriber){
        suscritores.add(subscriber);
    }
    
    public void subscribeProcess(MailBox mail, Proceso suscritor){
        mailboxes.get(mailboxes.indexOf(mail)).getSuscritos().add(suscritor);
    }
    
    public ArrayList<Proceso> getProcesses(){
        return procesos;
    }
    
    public void addMailBox(MailBox mailbox){
        mailboxes.add(mailbox);
    }
    
    public ArrayList<MailBox> getMailBoxes(){
        return mailboxes;
    }
    
    
}
