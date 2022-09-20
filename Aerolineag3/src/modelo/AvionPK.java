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
public class AvionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idAvion")
    private int idAvion;
    @Basic(optional = false)
    @Column(name = "Vuelo_idVuelo")
    private int vueloidVuelo;

    public AvionPK() {
    }

    public AvionPK(int idAvion, int vueloidVuelo) {
        this.idAvion = idAvion;
        this.vueloidVuelo = vueloidVuelo;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getVueloidVuelo() {
        return vueloidVuelo;
    }

    public void setVueloidVuelo(int vueloidVuelo) {
        this.vueloidVuelo = vueloidVuelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAvion;
        hash += (int) vueloidVuelo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvionPK)) {
            return false;
        }
        AvionPK other = (AvionPK) object;
        if (this.idAvion != other.idAvion) {
            return false;
        }
        if (this.vueloidVuelo != other.vueloidVuelo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AvionPK[ idAvion=" + idAvion + ", vueloidVuelo=" + vueloidVuelo + " ]";
    }
    
}
