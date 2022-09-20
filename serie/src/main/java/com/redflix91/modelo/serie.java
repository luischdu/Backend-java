/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redflix91.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author luischdu
 */
@Entity
@Table(name= "serie")
public class serie {


    
    @Id
    @Column (name = "id_serie")
    Integer id_serie;
    @Column (name = "titulo")
    String titulo;
    @Column (name = "temporadas")
    Integer temporadas;
    @Column (name = "episodios")
    Integer episodios;

    public serie() {
    }

    public serie(Integer id_serie, String titulo, Integer temporadas, Integer episodios) {
        this.id_serie = id_serie;
        this.titulo = titulo;
        this.temporadas = temporadas;
        this.episodios = episodios;
    }

    public Integer getId_serie() {
        return id_serie;
    }

    public void setId_serie(Integer id_serie) {
        this.id_serie = id_serie;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    public Integer getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Integer episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "serie{" + "id_serie=" + id_serie + ", titulo=" + titulo + ", temporadas=" + temporadas + ", episodios=" + episodios + '}';
    }
    
  
    
}
