/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author luischdu
 */
@Embeddable
public class EquipajePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEquipaje")
    private int idEquipaje;
    @Basic(optional = false)
    @Column(name = "Pasajero_idPasajero")
    private int pasajeroidPasajero;

    public EquipajePK() {
    }

    public EquipajePK(int idEquipaje, int pasajeroidPasajero) {
        this.idEquipaje = idEquipaje;
        this.pasajeroidPasajero = pasajeroidPasajero;
    }

    public int getIdEquipaje() {
        return idEquipaje;
    }

    public void setIdEquipaje(int idEquipaje) {
        this.idEquipaje = idEquipaje;
    }

    public int getPasajeroidPasajero() {
        return pasajeroidPasajero;
    }

    public void setPasajeroidPasajero(int pasajeroidPasajero) {
        this.pasajeroidPasajero = pasajeroidPasajero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEquipaje;
        hash += (int) pasajeroidPasajero;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipajePK)) {
            return false;
        }
        EquipajePK other = (EquipajePK) object;
        if (this.idEquipaje != other.idEquipaje) {
            return false;
        }
        if (this.pasajeroidPasajero != other.pasajeroidPasajero) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EquipajePK[ idEquipaje=" + idEquipaje + ", pasajeroidPasajero=" + pasajeroidPasajero + " ]";
    }
    
}
