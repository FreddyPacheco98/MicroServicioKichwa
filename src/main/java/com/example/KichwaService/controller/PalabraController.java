/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.controller;

import com.example.KichwaService.exception.ResourceNotFoundException;
import com.example.KichwaService.model.Palabra;
import com.example.KichwaService.repository.PalabraRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/palabra")
public class PalabraController {
    @Autowired
    PalabraRepository palabraRepository;
    
    @GetMapping("/listar")
    @CrossOrigin
    public List<Palabra> listarPalabras(){
        return this.palabraRepository.findAll();
    }
    
    @GetMapping("/buscar/{id_palabra}")
    @CrossOrigin
    public ResponseEntity<Palabra> getPalabraById(@PathVariable (value = "id_palabra") Long id_palabra)
    throws ResourceNotFoundException{
        Palabra palabra = palabraRepository.findById(id_palabra)
                .orElseThrow(()-> new ResourceNotFoundException("Palabra no encontrada por el id :: "+id_palabra));
        return ResponseEntity.ok().body(palabra);
    }
    
    @GetMapping("/buscarByNombre/{sig_esp}")
    @CrossOrigin
    public List<Palabra> getPalabraByNombre(@PathVariable (value = "sig_esp") String sig_esp){
        System.out.println(""+sig_esp);
        return this.palabraRepository.getPalabraLike(sig_esp);
    }
    
    @PostMapping("/crear")
    @CrossOrigin
    public Palabra crearPalabra(@RequestBody Palabra palabra){
        return this.palabraRepository.save(palabra);
    }
    
    @DeleteMapping("/eliminar/{id_palabra}")
    @CrossOrigin
    public void eliminarPalabra(@PathVariable Long id_palabra){
        this.palabraRepository.deleteById(id_palabra);
    }
    
    @PutMapping("/modificar/{id_palabra}")
    @CrossOrigin
//    public Palabra modificarPalabra(@RequestBody Palabra palabra, Long id_palabra){
//        this.palabraRepository.deleteById(id_palabra);
//        return this.palabraRepository.save(palabra);
//    }
    public ResponseEntity<Palabra> updatePalabra(@PathVariable (value = "id_palabra") Long id_palabra,
            @Valid @RequestBody Palabra palabraDetails)throws ResourceNotFoundException{
        Palabra palabra= palabraRepository.findById(id_palabra)
                .orElseThrow(()-> new ResourceNotFoundException("Palabra no encontrada por el id :: "+id_palabra));
        palabra.setDescripcion(palabraDetails.getDescripcion());
        palabra.setLeccion(palabraDetails.getLeccion());
        palabra.setSig_esp(palabraDetails.getSig_esp());
        palabra.setSig_kich(palabraDetails.getSig_kich());
        palabra.setUrl_audio(palabraDetails.getUrl_audio());
        palabra.setUrl_imagen(palabraDetails.getUrl_imagen());
        
        return ResponseEntity.ok(this.palabraRepository.save(palabra));
    }
    
    
}
