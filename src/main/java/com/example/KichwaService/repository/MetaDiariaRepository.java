/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.repository;

import com.example.KichwaService.model.MetaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Asrock Gamer
 */
public interface MetaDiariaRepository extends JpaRepository<MetaDiaria, Long> {
    
}
