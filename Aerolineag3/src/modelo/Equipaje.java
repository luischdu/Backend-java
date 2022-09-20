/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luischdu
 */
@Entity
@Table(name = "equipaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipaje.findAll", query = "SELECT e FROM Equipaje e")
    , @NamedQuery(name = "Equipaje.findByIdEquipaje", query = "SELECT e FROM Equipaje e WHERE e.equipajePK.idEquipaje = :idEquipaje")
    , @NamedQuery(name = "Equipaje.findByPasajeroidPasajero", query = "SELECT e FROM Equipaje e WHERE e.equipajePK.pasajeroidPasajero = :pasajeroidPasajero")
    , @NamedQuery(name = "Equipaje.findByTipo", query = "SELECT e FROM Equipaje e WHERE e.tipo = :tipo")
    , @NamedQuery(name = "Equipaje.findByPeso", query = "SELECT e FROM Equipaje e WHERE e.peso = :peso")
    , @NamedQuery(name = "Equipaje.findByVolumen", query = "SELECT e FROM Equipaje e WHERE e.volumen = :volumen")})
public class Equipaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EquipajePK equipajePK;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "peso")
    private Long peso;
    @Column(name = "volumen")
    private String volumen;
    @JoinColumn(name = "Pasajero_idPasajero", referencedColumnName = "idPasajero", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pasajero pasajero;

    public Equipaje() {
    }

    public Equipaje(EquipajePK equipajePK) {
        this.equipajePK = equipajePK;
    }

    public Equipaje(int idEquipaje, int pasajeroidPasajero) {
        this.equipajePK = new EquipajePK(idEquipaje, pasajeroidPasajero);
    }

    public EquipajePK getEquipajePK() {
        return equipajePK;
    }

    public void setEquipajePK(EquipajePK equipajePK) {
        this.equipajePK = equipajePK;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipajePK != null ? equipajePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipaje)) {
            return false;
        }
        Equipaje other = (Equipaje) object;
        if ((this.equipajePK == null && other.equipajePK != null) || (this.equipajePK != null && !this.equipajePK.equals(other.equipajePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Equipaje[ equipajePK=" + equipajePK + " ]";
    }
    
}
