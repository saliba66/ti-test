package com.unipark.backend.controller;

import com.unipark.backend.dto.UsuarioDTO;
import com.unipark.backend.entity.Usuario;
import com.unipark.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(UsuarioDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .map(usuario -> ResponseEntity.ok(new UsuarioDTO(usuario)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/por-role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuariosPorRole(@PathVariable String role) {
        try {
            Usuario.Role roleEnum = Usuario.Role.valueOf(role.toUpperCase());
            List<Usuario> usuarios = usuarioRepository.findByRole(roleEnum);
            List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(usuariosDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/ativos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioRepository.findByAtivoTrue();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(UsuarioDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }
}