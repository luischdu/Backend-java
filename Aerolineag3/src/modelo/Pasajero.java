/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luischdu
 */
@Entity
@Table(name = "pasajero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasajero.findAll", query = "SELECT p FROM Pasajero p")
    , @NamedQuery(name = "Pasajero.findByIdPasajero", query = "SELECT p FROM Pasajero p WHERE p.idPasajero = :idPasajero")
    , @NamedQuery(name = "Pasajero.findByNombre", query = "SELECT p FROM Pasajero p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Pasajero.findByPasaporte", query = "SELECT p FROM Pasajero p WHERE p.pasaporte = :pasaporte")
    , @NamedQuery(name = "Pasajero.findByNacionalidad", query = "SELECT p FROM Pasajero p WHERE p.nacionalidad = :nacionalidad")
    , @NamedQuery(name = "Pasajero.findByEdad", query = "SELECT p FROM Pasajero p WHERE p.edad = :edad")
    , @NamedQuery(name = "Pasajero.findByGenero", query = "SELECT p FROM Pasajero p WHERE p.genero = :genero")})
public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPasajero")
    private Integer idPasajero;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "pasaporte")
    private String pasaporte;
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @Column(name = "edad")
    private Integer edad;
    @Column(name = "genero")
    private String genero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasajero")
    private List<Equipaje> equipajeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasajero")
    private List<Reserva> reservaList;

    public Pasajero() {
    }

    public Pasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
    }

    public Integer getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @XmlTransient
    public List<Equipaje> getEquipajeList() {
        return equipajeList;
    }

    public void setEquipajeList(List<Equipaje> equipajeList) {
        this.equipajeList = equipajeList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasajero != null ? idPasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasajero)) {
            return false;
        }
        Pasajero other = (Pasajero) object;
        if ((this.idPasajero == null && other.idPasajero != null) || (this.idPasajero != null && !this.idPasajero.equals(other.idPasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pasajero[ idPasajero=" + idPasajero + " ]";
    }
    
}
