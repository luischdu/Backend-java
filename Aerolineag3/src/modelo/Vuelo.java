/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luischdu
 */
@Entity
@Table(name = "vuelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelo.findAll", query = "SELECT v FROM Vuelo v")
    , @NamedQuery(name = "Vuelo.findByIdVuelo", query = "SELECT v FROM Vuelo v WHERE v.idVuelo = :idVuelo")
    , @NamedQuery(name = "Vuelo.findByFecha", query = "SELECT v FROM Vuelo v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Vuelo.findByHora", query = "SELECT v FROM Vuelo v WHERE v.hora = :hora")
    , @NamedQuery(name = "Vuelo.findByReserva", query = "SELECT v FROM Vuelo v WHERE v.reserva = :reserva")
    , @NamedQuery(name = "Vuelo.findByPuertaAbordaje", query = "SELECT v FROM Vuelo v WHERE v.puertaAbordaje = :puertaAbordaje")
    , @NamedQuery(name = "Vuelo.findByTipo", query = "SELECT v FROM Vuelo v WHERE v.tipo = :tipo")})
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVuelo")
    private Integer idVuelo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "reserva")
    private String reserva;
    @Column(name = "puertaAbordaje")
    private String puertaAbordaje;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Trayecto> trayectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Itinireario> itinirearioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Reserva> reservaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Avion> avionList;

    public Vuelo() {
    }

    public Vuelo(Integer idVuelo) {
        this.idVuelo = idVuelo;
    }

    public Integer getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(Integer idVuelo) {
        this.idVuelo = idVuelo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getPuertaAbordaje() {
        return puertaAbordaje;
    }

    public void setPuertaAbordaje(String puertaAbordaje) {
        this.puertaAbordaje = puertaAbordaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Trayecto> getTrayectoList() {
        return trayectoList;
    }

    public void setTrayectoList(List<Trayecto> trayectoList) {
        this.trayectoList = trayectoList;
    }

    @XmlTransient
    public List<Itinireario> getItinirearioList() {
        return itinirearioList;
    }

    public void setItinirearioList(List<Itinireario> itinirearioList) {
        this.itinirearioList = itinirearioList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @XmlTransient
    public List<Avion> getAvionList() {
        return avionList;
    }

    public void setAvionList(List<Avion> avionList) {
        this.avionList = avionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVuelo != null ? idVuelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelo)) {
            return false;
        }
        Vuelo other = (Vuelo) object;
        if ((this.idVuelo == null && other.idVuelo != null) || (this.idVuelo != null && !this.idVuelo.equals(other.idVuelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Vuelo[ idVuelo=" + idVuelo + " ]";
    }
    
}
