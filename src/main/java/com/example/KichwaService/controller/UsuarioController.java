/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.KichwaService.controller;

import com.example.KichwaService.exception.ResourceNotFoundException;
import com.example.KichwaService.model.Usuario;
import com.example.KichwaService.repository.UsuarioRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario", produces = "application/json")
//@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    
//    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    @GetMapping("/listar")
    @CrossOrigin
    public List<Usuario> listarUsuarios(){
        return this.usuarioRepository.findAll();
    }    
    
    @GetMapping("/buscar/{id_usuario}")
    @CrossOrigin
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable (value = "id_usuario") Long id_usuario)
            throws ResourceNotFoundException{
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado con el id :: "+id_usuario));
        return ResponseEntity.ok().body(usuario);
    }
    
    @GetMapping("/buscarByCorreo/{correo}")
    @CrossOrigin
    public List<Usuario> getUsuarioByCorreo(@PathVariable (value = "correo")String correo){
        
        System.out.println("Correo: "+correo);        
        
        return usuarioRepository.getCorreosUsuariosLike(correo);
    }
    
    @GetMapping("/verificarUsuarioByCorreo/{correo}")
    @CrossOrigin
    public boolean verificaUsuario(@PathVariable (value="correo") String correo){
        boolean bandUsu = false; 
        if (usuarioRepository.getCorreosUsuariosLike(correo).isEmpty()){
            bandUsu = false;
        }else{
            bandUsu = true;
        }
        return bandUsu;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/crear")
//    @RequestMapping(value = "/crear", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        Usuario newUsuario = usuarioRepository.save(usuario);
                
        return ResponseEntity.ok(newUsuario);
    }
    
    
    
//    @RequestMapping(value = "/eliminar/{id_usuario}", method = RequestMethod.DELETE)
    @DeleteMapping("/eliminar/{id_usuario}")
    @CrossOrigin
    public void eliminarUsuario(@PathVariable Long id_usuario){
        this.usuarioRepository.deleteById(id_usuario);
    }
    
//    @RequestMapping(value = "/modificar/{id_usuario}", method = RequestMethod.PUT)
    @PutMapping("/modificar/{id_usuario}")
    @CrossOrigin
    public ResponseEntity<Usuario> updateUsuario(@PathVariable (value = "id_usuario") Long id_usuario,
            @Valid @RequestBody Usuario usuarioDetails)throws ResourceNotFoundException{
        Usuario usuario= usuarioRepository.findById(id_usuario)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado por el id :: "+id_usuario));
        usuario.setCorreo(usuarioDetails.getCorreo());
        usuario.setMotivo(usuarioDetails.getMotivo());
        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setPassword(usuarioDetails.getPassword());
        usuario.setSexo(usuarioDetails.getSexo());
        usuario.setUrl_foto(usuarioDetails.getUrl_foto());
        
        return ResponseEntity.ok(this.usuarioRepository.save(usuario));
        
    }
//    public Usuario modificarUsuario(@RequestBody Usuario usuario, @PathVariable Long id_usuario){
//        this.usuarioRepository.deleteById(id_usuario);
//        return this.usuarioRepository.save(usuario);
//    }
            
}
