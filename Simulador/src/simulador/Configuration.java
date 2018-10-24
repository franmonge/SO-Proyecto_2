/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;
import Config_Enums.Sync_Receive;
import Config_Enums.Sync_Send;
import Config_Enums.Addressing;
import Config_Enums.Format_Content;
import Config_Enums.Format_Length;
import Config_Enums.MailBox_Discipline;
import Config_Enums.Priority;


public class Configuration {
    Sync_Receive receive;
    Sync_Send send;
    Addressing addressing;
    Format_Content content;
    Format_Length length;
    MailBox_Discipline discipline;
    Priority priority;
    Integer bufferSize;

    public Configuration(){}
    
    public Configuration(Sync_Receive receive, Sync_Send send, Addressing addressing, Format_Content content, Format_Length length, MailBox_Discipline discipline, Priority priority, Integer bufferSize) {
        this.receive = receive;
        this.send = send;
        this.addressing = addressing;
        this.content = content;
        this.length = length;
        this.discipline = discipline;
        this.priority = priority;
        this.bufferSize = bufferSize;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }
    

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public Sync_Receive getReceive() {
        return receive;
    }

    public Sync_Send getSend() {
        return send;
    }

    public Addressing getAddressing() {
        return addressing;
    }

    public Format_Content getContent() {
        return content;
    }

    public Format_Length getLength() {
        return length;
    }

    public MailBox_Discipline getDiscipline() {
        return discipline;
    }

    public void setReceive(Sync_Receive receive) {
        this.receive = receive;
    }

    public void setSend(Sync_Send send) {
        this.send = send;
    }

    public void setAddressing(Addressing addressing) {
        this.addressing = addressing;
    }

    public void setContent(Format_Content content) {
        this.content = content;
    }

    public void setLength(Format_Length length) {
        this.length = length;
    }

    public void setDiscipline(MailBox_Discipline discipline) {
        this.discipline = discipline;
    }
    
}
