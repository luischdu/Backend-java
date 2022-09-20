/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unal.airspring.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "avion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avion.findAll", query = "SELECT a FROM Avion a")
    , @NamedQuery(name = "Avion.findByIdAvion", query = "SELECT a FROM Avion a WHERE a.avionPK.idAvion = :idAvion")
    , @NamedQuery(name = "Avion.findByModelo", query = "SELECT a FROM Avion a WHERE a.modelo = :modelo")
    , @NamedQuery(name = "Avion.findByMarca", query = "SELECT a FROM Avion a WHERE a.marca = :marca")
    , @NamedQuery(name = "Avion.findByCapacidadPasajeros", query = "SELECT a FROM Avion a WHERE a.capacidadPasajeros = :capacidadPasajeros")
    , @NamedQuery(name = "Avion.findByCapacidadPeso", query = "SELECT a FROM Avion a WHERE a.capacidadPeso = :capacidadPeso")
    , @NamedQuery(name = "Avion.findByCapacidadVolumen", query = "SELECT a FROM Avion a WHERE a.capacidadVolumen = :capacidadVolumen")
    , @NamedQuery(name = "Avion.findByVueloidVuelo", query = "SELECT a FROM Avion a WHERE a.avionPK.vueloidVuelo = :vueloidVuelo")})
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    protected int avionPK;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "marca")
    private String marca;
    @Column(name = "capacidadPasajeros")
    private Integer capacidadPasajeros;
    @Column(name = "capacidadPeso")
    private Long capacidadPeso;
    @Column(name = "capacidadVolumen")
    private Long capacidadVolumen;
   // @JoinColumn(name = "Vuelo_idVuelo", referencedColumnName = "idVuelo", insertable = false, updatable = false)
   // @ManyToOne(optional = false)
    @Column(name="Vuelo_idVuelo")
    private int vuelo;

    public Avion() {
    }

    public Avion(AvionPK avionPK) {
        this.avionPK = avionPK;
    }

    public Avion(int idAvion, int vueloidVuelo) {
        this.avionPK = new AvionPK(idAvion, vueloidVuelo);
    }

    public AvionPK getAvionPK() {
        return avionPK;
    }

    public void setAvionPK(AvionPK avionPK) {
        this.avionPK = avionPK;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(Integer capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public Long getCapacidadPeso() {
        return capacidadPeso;
    }

    public void setCapacidadPeso(Long capacidadPeso) {
        this.capacidadPeso = capacidadPeso;
    }

    public Long getCapacidadVolumen() {
        return capacidadVolumen;
    }

    public void setCapacidadVolumen(Long capacidadVolumen) {
        this.capacidadVolumen = capacidadVolumen;
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
        hash += (avionPK != null ? avionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avion)) {
            return false;
        }
        Avion other = (Avion) object;
        if ((this.avionPK == null && other.avionPK != null) || (this.avionPK != null && !this.avionPK.equals(other.avionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avion[ avionPK=" + avionPK + " ]";
    }
    
}
