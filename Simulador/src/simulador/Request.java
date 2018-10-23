/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import Config_Enums.Request_Action;


public class Request {
    private String sourceID;
    private String destinationID;
    private Request_Action action;

    public Request(String sourceID, String destinationID, Request_Action action) {
        this.sourceID = sourceID;
        this.destinationID = destinationID;
        this.action = action;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public Request_Action getAction() {
        return action;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public void setAction(Request_Action action) {
        this.action = action;
    }    
}
