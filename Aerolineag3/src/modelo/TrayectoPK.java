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
public class TrayectoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Vuelo_idVuelo")
    private int vueloidVuelo;
    @Basic(optional = false)
    @Column(name = "origen")
    private int origen;
    @Basic(optional = false)
    @Column(name = "destino")
    private int destino;

    public TrayectoPK() {
    }

    public TrayectoPK(int vueloidVuelo, int origen, int destino) {
        this.vueloidVuelo = vueloidVuelo;
        this.origen = origen;
        this.destino = destino;
    }

    public int getVueloidVuelo() {
        return vueloidVuelo;
    }

    public void setVueloidVuelo(int vueloidVuelo) {
        this.vueloidVuelo = vueloidVuelo;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) vueloidVuelo;
        hash += (int) origen;
        hash += (int) destino;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrayectoPK)) {
            return false;
        }
        TrayectoPK other = (TrayectoPK) object;
        if (this.vueloidVuelo != other.vueloidVuelo) {
            return false;
        }
        if (this.origen != other.origen) {
            return false;
        }
        if (this.destino != other.destino) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TrayectoPK[ vueloidVuelo=" + vueloidVuelo + ", origen=" + origen + ", destino=" + destino + " ]";
    }
    
}
