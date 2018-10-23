/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.util.ArrayList;


public class Proceso {
    String idProceso;    
    Boolean sending;
    Boolean receiving;
    Boolean blocking;
    Boolean busy;
    ArrayList<MessageRecord> recordHistory; 

    public Proceso(String idProceso) {
        this.idProceso = idProceso;
        this.sending = false;
        this.receiving = false;
        this.blocking = false;
        this.busy = false;
        recordHistory = new ArrayList<MessageRecord>(); 

    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Boolean getBusy() {
        return busy;
    }

    public Boolean getSending() {
        return sending;
    }

    public Boolean getReceiving() {
        return receiving;
    }

    public Boolean getBlocking() {
        return blocking;
    }

    public ArrayList<MessageRecord> getRecordHistory() {
        return recordHistory;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public void setSending(Boolean sending) {
        this.sending = sending;
    }

    public void setReceiving(Boolean receiving) {
        this.receiving = receiving;
    }

    public void setBlocking(Boolean blocking) {
        this.blocking = blocking;
    }

    public void setRecordHistory(ArrayList<MessageRecord> recordHistory) {
        this.recordHistory = recordHistory;
    }

    @Override
    public String toString() {
        return "Proceso{" + "idProceso=" + idProceso + ", sending=" + sending + ", receiving=" + receiving + ", blocking=" + blocking + ", colaMensajes=" + recordHistory + '}';
    }    
}
