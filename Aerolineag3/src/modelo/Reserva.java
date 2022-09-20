/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByPasajeroidPasajero", query = "SELECT r FROM Reserva r WHERE r.reservaPK.pasajeroidPasajero = :pasajeroidPasajero")
    , @NamedQuery(name = "Reserva.findByVueloidVuelo", query = "SELECT r FROM Reserva r WHERE r.reservaPK.vueloidVuelo = :vueloidVuelo")
    , @NamedQuery(name = "Reserva.findByVueloAvionidAvion", query = "SELECT r FROM Reserva r WHERE r.reservaPK.vueloAvionidAvion = :vueloAvionidAvion")
    , @NamedQuery(name = "Reserva.findByClase", query = "SELECT r FROM Reserva r WHERE r.clase = :clase")
    , @NamedQuery(name = "Reserva.findByValor", query = "SELECT r FROM Reserva r WHERE r.valor = :valor")
    , @NamedQuery(name = "Reserva.findByAsiento", query = "SELECT r FROM Reserva r WHERE r.asiento = :asiento")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservaPK reservaPK;
    @Column(name = "clase")
    private String clase;
    @Column(name = "valor")
    private BigInteger valor;
    @Column(name = "asiento")
    private String asiento;
    @JoinColumn(name = "Pasajero_idPasajero", referencedColumnName = "idPasajero", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pasajero pasajero;
    @JoinColumn(name = "Vuelo_idVuelo", referencedColumnName = "idVuelo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelo vuelo;

    public Reserva() {
    }

    public Reserva(ReservaPK reservaPK) {
        this.reservaPK = reservaPK;
    }

    public Reserva(int pasajeroidPasajero, int vueloidVuelo, int vueloAvionidAvion) {
        this.reservaPK = new ReservaPK(pasajeroidPasajero, vueloidVuelo, vueloAvionidAvion);
    }

    public ReservaPK getReservaPK() {
        return reservaPK;
    }

    public void setReservaPK(ReservaPK reservaPK) {
        this.reservaPK = reservaPK;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservaPK != null ? reservaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.reservaPK == null && other.reservaPK != null) || (this.reservaPK != null && !this.reservaPK.equals(other.reservaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reserva[ reservaPK=" + reservaPK + " ]";
    }
    
}
