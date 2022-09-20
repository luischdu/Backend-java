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
@Table(name = "itinireario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itinireario.findAll", query = "SELECT i FROM Itinireario i")
    , @NamedQuery(name = "Itinireario.findByEmpleadoidEmpleado", query = "SELECT i FROM Itinireario i WHERE i.itinirearioPK.empleadoidEmpleado = :empleadoidEmpleado")
    , @NamedQuery(name = "Itinireario.findByVueloidVuelo", query = "SELECT i FROM Itinireario i WHERE i.itinirearioPK.vueloidVuelo = :vueloidVuelo")
    , @NamedQuery(name = "Itinireario.findByVueloAvionidAvion", query = "SELECT i FROM Itinireario i WHERE i.itinirearioPK.vueloAvionidAvion = :vueloAvionidAvion")
    , @NamedQuery(name = "Itinireario.findByTipo", query = "SELECT i FROM Itinireario i WHERE i.tipo = :tipo")})
public class Itinireario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItinirearioPK itinirearioPK;
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "Empleado_idEmpleado", referencedColumnName = "idEmpleado", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "Vuelo_idVuelo", referencedColumnName = "idVuelo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelo vuelo;

    public Itinireario() {
    }

    public Itinireario(ItinirearioPK itinirearioPK) {
        this.itinirearioPK = itinirearioPK;
    }

    public Itinireario(int empleadoidEmpleado, int vueloidVuelo, int vueloAvionidAvion) {
        this.itinirearioPK = new ItinirearioPK(empleadoidEmpleado, vueloidVuelo, vueloAvionidAvion);
    }

    public ItinirearioPK getItinirearioPK() {
        return itinirearioPK;
    }

    public void setItinirearioPK(ItinirearioPK itinirearioPK) {
        this.itinirearioPK = itinirearioPK;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        hash += (itinirearioPK != null ? itinirearioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itinireario)) {
            return false;
        }
        Itinireario other = (Itinireario) object;
        if ((this.itinirearioPK == null && other.itinirearioPK != null) || (this.itinirearioPK != null && !this.itinirearioPK.equals(other.itinirearioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Itinireario[ itinirearioPK=" + itinirearioPK + " ]";
    }
    
}
