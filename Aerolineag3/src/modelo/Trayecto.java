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
@Table(name = "trayecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trayecto.findAll", query = "SELECT t FROM Trayecto t")
    , @NamedQuery(name = "Trayecto.findByVueloidVuelo", query = "SELECT t FROM Trayecto t WHERE t.trayectoPK.vueloidVuelo = :vueloidVuelo")
    , @NamedQuery(name = "Trayecto.findByOrigen", query = "SELECT t FROM Trayecto t WHERE t.trayectoPK.origen = :origen")
    , @NamedQuery(name = "Trayecto.findByDestino", query = "SELECT t FROM Trayecto t WHERE t.trayectoPK.destino = :destino")
    , @NamedQuery(name = "Trayecto.findByDuracion", query = "SELECT t FROM Trayecto t WHERE t.duracion = :duracion")
    , @NamedQuery(name = "Trayecto.findByDistancia", query = "SELECT t FROM Trayecto t WHERE t.distancia = :distancia")})
public class Trayecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrayectoPK trayectoPK;
    @Column(name = "duracion")
    private Long duracion;
    @Column(name = "distancia")
    private Long distancia;
    @JoinColumn(name = "origen", referencedColumnName = "idCiudad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ciudad ciudad;
    @JoinColumn(name = "Vuelo_idVuelo", referencedColumnName = "idVuelo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelo vuelo;

    public Trayecto() {
    }

    public Trayecto(TrayectoPK trayectoPK) {
        this.trayectoPK = trayectoPK;
    }

    public Trayecto(int vueloidVuelo, int origen, int destino) {
        this.trayectoPK = new TrayectoPK(vueloidVuelo, origen, destino);
    }

    public TrayectoPK getTrayectoPK() {
        return trayectoPK;
    }

    public void setTrayectoPK(TrayectoPK trayectoPK) {
        this.trayectoPK = trayectoPK;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Long getDistancia() {
        return distancia;
    }

    public void setDistancia(Long distancia) {
        this.distancia = distancia;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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
        hash += (trayectoPK != null ? trayectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trayecto)) {
            return false;
        }
        Trayecto other = (Trayecto) object;
        if ((this.trayectoPK == null && other.trayectoPK != null) || (this.trayectoPK != null && !this.trayectoPK.equals(other.trayectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Trayecto[ trayectoPK=" + trayectoPK + " ]";
    }
    
}
