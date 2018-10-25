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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;



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
    
    public void sendMessage(Mensaje mensaje){ // Abarca tanto addressing direct como indirect        
        getProcess(mensaje.getSourceID()).getRecordHistory().add(new MessageRecord(Record_Message_Action.SENT, mensaje));
        System.out.println("Message sent to MailBox: " + getMailBox(mensaje.getDestinationID()).getIdMailBox());
        getMailBox(mensaje.getDestinationID()).addMessage(mensaje);  // Add MessageRecord
    }
    
    public void receiveMessage(String sourceID, String destinationID){
        Proceso receiver = getProcess(sourceID);
        Mensaje message = null;
        MailBox mail = getMailBox(destinationID);
        if(isThereAPendingMessageOnMailBox(destinationID, sourceID)){
            System.out.println("Existing message on MailBox to be delivered");

            message = getMessageFromMailBox(sourceID, destinationID);
            Proceso sender = getProcess(message.getSourceID());
            System.out.println("Was the received message null? " + String.valueOf(message == null));
            
            //receiver.getRecordHistory().add(new MessageRecord(Record_Message_Action.RECEIVED, message));
        }
        printMessage(mail, message);
    }
    
    public void printMessage(MailBox mail, Mensaje message){
        //FileUtils.writeStringToFile(File file, String data, String encoding)  para escribir en el log
        System.out.println("A message is about to be printed");
        String fileName = message.getPath().split("/")[message.getPath().split("/").length-1];
        System.out.println("File name: "+ fileName);//Arrays.toString(pathPieces));
        File source = new File(message.getPath());
        File dest = new File(System.getProperty("user.dir")+"/"+mail.getIdMailBox()+"/"+fileName);
        
        try {
            //FileUtils.copyFileToDirectory(source, dest); no necesitaria el nombre del archivo
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
    private boolean isThereAPendingMessageOnMailBox(String destinationID, String sourceID){
        if(getMailBox(destinationID).getBufferMensajes().size()> 0)
            return true;
        return false;
    }
    
    private Mensaje getMessageFromMailBox(String sourceID, String destinationID){ // Actualizar por prioridad
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
