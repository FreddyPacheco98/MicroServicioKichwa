/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.repository;

import com.example.KichwaService.model.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeccionRepository extends JpaRepository<Leccion, Long>{
    
}