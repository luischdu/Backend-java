/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redflix91.repositorio;

import com.redflix91.modelo.serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luischdu
 */
@Repository
public interface serieRepositorio extends JpaRepository<serie, Long>{
    
}
