/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import Config_Enums.Record_Message_Action;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MessageRecord {
    private String timeStamp;
    private Record_Message_Action action;
    private Mensaje message;

    public MessageRecord(Record_Message_Action action, Mensaje message) {
        this.action = action;
        this.message = message;
        this.timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Record_Message_Action getAction() {
        return action;
    }

    public Mensaje getMessage() {
        return message;
    }
    
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setAction(Record_Message_Action action) {
        this.action = action;
    }

    public void setMessage(Mensaje message) {
        this.message = message;
    }
    
}
