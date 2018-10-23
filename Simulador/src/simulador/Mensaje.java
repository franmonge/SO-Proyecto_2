/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;


public class Mensaje {
    Integer idMensaje;
    String path;
    String sourceID;
    String destinationID;

    public Mensaje(Integer idMensaje, String texto, String sourceID, String destinationID) {
        this.idMensaje = idMensaje;
        this.path = texto;
        this.sourceID = sourceID;
        this.destinationID = destinationID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public String getPath() {
        return path;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "idMensaje=" + idMensaje + ", path=" + path + ", sourceID=" + sourceID + ", destinationID=" + destinationID + '}';
    }
    
}
