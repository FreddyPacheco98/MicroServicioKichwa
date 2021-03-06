/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.controller;

import com.example.KichwaService.exception.ResourceNotFoundException;
import com.example.KichwaService.model.ActividadUsuario;
import com.example.KichwaService.repository.ActividadUsuarioRepository;
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
@RequestMapping("/actividad_usuario")
public class ActividadUsuarioController {
    @Autowired
    ActividadUsuarioRepository actividadUsuarioRepository;
    
    @GetMapping("/listar")
    @CrossOrigin
    public List<ActividadUsuario> listaActividadUsuario(){
        return this.actividadUsuarioRepository.findAll();
    }
    
    @GetMapping("/buscar/{id_act_usu}")
    @CrossOrigin
    public ResponseEntity<ActividadUsuario> getActividadUsuarioById(@PathVariable (value = "id_act_usu") Long id_act_usu)
            throws ResourceNotFoundException{
        ActividadUsuario actividadUsuario= actividadUsuarioRepository.findById(id_act_usu)
                .orElseThrow(()-> new ResourceNotFoundException("ActividadUsuario no encontrado con el id :: "+id_act_usu));
        return ResponseEntity.ok().body(actividadUsuario);
    }
    
    @PostMapping("/crear")
    @CrossOrigin
    public ActividadUsuario crearActividadUsuario(@RequestBody ActividadUsuario actividadUsuario){
        return this.actividadUsuarioRepository.save(actividadUsuario);
    }
    
    @DeleteMapping("/eliminar/{id_act_usu}")
    @CrossOrigin
    public void eliminarActividadUsuario(@PathVariable Long id_act_usu){
        this.actividadUsuarioRepository.deleteById(id_act_usu);
        
    }
    
    @PutMapping("/modificar/{id_act_usu}")
    @CrossOrigin
//    public ActividadUsuario modificarActividadUsuario(@RequestBody ActividadUsuario actividadUsuario,@PathVariable Long id_act_usu){
//        this.actividadUsuarioRepository.deleteById(id_act_usu);
//        return this.actividadUsuarioRepository.save(actividadUsuario);
//    }
    public ResponseEntity<ActividadUsuario> updateActividadUsuario(@PathVariable (value = "id_act_usu") Long id_act_usu,
            @Valid @RequestBody ActividadUsuario actividadUsuarioDetails)throws ResourceNotFoundException{
        ActividadUsuario actividadUsuario=actividadUsuarioRepository.findById(id_act_usu)
                .orElseThrow(()-> new ResourceNotFoundException("ActividadUsuario no encontrado por el id :: "+id_act_usu));
        actividadUsuario.setActividad(actividadUsuarioDetails.getActividad());
        actividadUsuario.setEstado(actividadUsuarioDetails.isEstado());
        actividadUsuario.setFecha(actividadUsuarioDetails.getFecha());
        actividadUsuario.setUsuario(actividadUsuarioDetails.getUsuario());
        
        return ResponseEntity.ok(this.actividadUsuarioRepository.save(actividadUsuario));
    }
}
