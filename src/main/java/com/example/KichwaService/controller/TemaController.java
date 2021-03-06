/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.controller;
import com.example.KichwaService.exception.ResourceNotFoundException;
import com.example.KichwaService.model.Tema;
import com.example.KichwaService.repository.TemaRepository;
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
@RequestMapping("/tema")
public class TemaController {
    @Autowired
    TemaRepository temaRepository;
    
    @GetMapping("/listar")
    @CrossOrigin
    public List<Tema> listarTemas(){
        return this.temaRepository.findAll();
    }
    
    @GetMapping("/buscar/{id_tema}")
    @CrossOrigin
    public ResponseEntity<Tema> getTema(@PathVariable (value = "id_tema") Long id_tema)
            throws ResourceNotFoundException{
        Tema tema = temaRepository.findById(id_tema)
                .orElseThrow(()-> new ResourceNotFoundException("Tema no encontrado con el id :: "+id_tema));
        return ResponseEntity.ok().body(tema);
    }
    
    @PostMapping("/crear")
    @CrossOrigin
    public Tema crearTema(@RequestBody Tema t){
        return this.temaRepository.save(t);
    }
    
    @DeleteMapping("/eliminar/{id_tema}")
    @CrossOrigin
    public void eliminarTema(@PathVariable Long id_tema){
        this.temaRepository.deleteById(id_tema);
    }
    
    @PutMapping("/modificar/{id_tema")
    @CrossOrigin
//    public Tema modificarTema(@RequestBody Tema tema, @PathVariable Long id_tema){
//        this.temaRepository.deleteById(id_tema);
//        return this.temaRepository.save(tema);
//    }
    public ResponseEntity<Tema> updateTema(@PathVariable (value = "id_tema") Long id_tema,
            @Valid @RequestBody Tema temaDetails)throws ResourceNotFoundException{
        Tema tema = temaRepository.findById(id_tema)
                .orElseThrow(()-> new ResourceNotFoundException("Tema no encontrado con el id :: "+id_tema));
        tema.setActividad(temaDetails.getActividad());
        tema.setDescripcion(temaDetails.getDescripcion());
        tema.setNombre(temaDetails.getNombre());
        
        return ResponseEntity.ok(this.temaRepository.save(tema));
    }
    
}
