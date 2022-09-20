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
public class ItinirearioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Empleado_idEmpleado")
    private int empleadoidEmpleado;
    @Basic(optional = false)
    @Column(name = "Vuelo_idVuelo")
    private int vueloidVuelo;
    @Basic(optional = false)
    @Column(name = "Vuelo_Avion_idAvion")
    private int vueloAvionidAvion;

    public ItinirearioPK() {
    }

    public ItinirearioPK(int empleadoidEmpleado, int vueloidVuelo, int vueloAvionidAvion) {
        this.empleadoidEmpleado = empleadoidEmpleado;
        this.vueloidVuelo = vueloidVuelo;
        this.vueloAvionidAvion = vueloAvionidAvion;
    }

    public int getEmpleadoidEmpleado() {
        return empleadoidEmpleado;
    }

    public void setEmpleadoidEmpleado(int empleadoidEmpleado) {
        this.empleadoidEmpleado = empleadoidEmpleado;
    }

    public int getVueloidVuelo() {
        return vueloidVuelo;
    }

    public void setVueloidVuelo(int vueloidVuelo) {
        this.vueloidVuelo = vueloidVuelo;
    }

    public int getVueloAvionidAvion() {
        return vueloAvionidAvion;
    }

    public void setVueloAvionidAvion(int vueloAvionidAvion) {
        this.vueloAvionidAvion = vueloAvionidAvion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empleadoidEmpleado;
        hash += (int) vueloidVuelo;
        hash += (int) vueloAvionidAvion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItinirearioPK)) {
            return false;
        }
        ItinirearioPK other = (ItinirearioPK) object;
        if (this.empleadoidEmpleado != other.empleadoidEmpleado) {
            return false;
        }
        if (this.vueloidVuelo != other.vueloidVuelo) {
            return false;
        }
        if (this.vueloAvionidAvion != other.vueloAvionidAvion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ItinirearioPK[ empleadoidEmpleado=" + empleadoidEmpleado + ", vueloidVuelo=" + vueloidVuelo + ", vueloAvionidAvion=" + vueloAvionidAvion + " ]";
    }
    
}
