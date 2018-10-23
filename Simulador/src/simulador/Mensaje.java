/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

/**
 *
 * @author FranM
 */
public class Mensaje {
    Integer idMensaje;
    long largo;
    Integer prioridad;
    String texto;   //Definir si imagenes y videos se manejan solo con url.
    String sourceID;
    String destinationID;

    public Mensaje(Integer idMensaje, long largo, Integer prioridad, String texto, String sourceID, String destinationID) {
        this.idMensaje = idMensaje;
        this.largo = largo;
        this.prioridad = prioridad;
        this.texto = texto;
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

    public long getLargo() {
        return largo;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public String getTexto() {
        return texto;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "idMensaje=" + idMensaje + ", tipo=" + ", largo=" + largo + ", prioridad=" + prioridad + ", texto=" + texto + '}';
    }
    
}
