/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.util.ArrayList;


public class MailBox {
    String idMailBox;
    int cantidadMensajesMaxima;
    ArrayList<Proceso> suscritos; 
    ArrayList<Mensaje> bufferMensajes;
    ArrayList<MessageRecord> printerRecord;
    
    public MailBox(String idMailBox, int cantidadMensajesMaxima) {
        this.idMailBox = idMailBox;
        this.cantidadMensajesMaxima = cantidadMensajesMaxima;
        this.suscritos = new ArrayList<Proceso>();
        this.bufferMensajes = new ArrayList<Mensaje>();
        this.printerRecord = new ArrayList<MessageRecord>();
    }

    public ArrayList<MessageRecord> getPrinterRecord() {
        return printerRecord;
    }

    public void setPrinterRecord(ArrayList<MessageRecord> printerRecord) {
        this.printerRecord = printerRecord;
    }

    public String getIdMailBox() {
        return idMailBox;
    }

   
    public ArrayList<Proceso> getSuscritos() {
        return suscritos;
    }

    public void setIdMailBox(String idMailBox) {
        this.idMailBox = idMailBox;
    }


    public void setSuscritos(ArrayList<Proceso> suscritos) {
        this.suscritos = suscritos;
    }

    public void setCantidadMensajesMaxima(int cantidadMensajesMaxima) {
        this.cantidadMensajesMaxima = cantidadMensajesMaxima;
    }

    public void setBufferMensajes(ArrayList<Mensaje> bufferMensajes) {
        this.bufferMensajes = bufferMensajes;
    }

    public int getCantidadMensajesMaxima() {
        return cantidadMensajesMaxima;
    }

    public ArrayList<Mensaje> getBufferMensajes() {
        return bufferMensajes;
    }
    
    public void addMessage(Mensaje message){
        bufferMensajes.add(message);
    }

    @Override
    public String toString() {
        return "MailBox{" + "idMailBox=" + idMailBox + ", cantidadMensajesMaxima=" + cantidadMensajesMaxima + ", suscritos=" + suscritos + ", bufferMensajes=" + bufferMensajes + '}';
    }
    
}
