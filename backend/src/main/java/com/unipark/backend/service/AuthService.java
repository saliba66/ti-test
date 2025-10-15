package com.unipark.backend.service;

import com.unipark.backend.dto.LoginRequest;
import com.unipark.backend.dto.LoginResponse;
import com.unipark.backend.entity.Usuario;
import com.unipark.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService jwtService;
    
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Autenticar usuário
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getLogin(),
                    loginRequest.getSenha()
                )
            );
            
            // Buscar usuário no banco
            Usuario usuario = usuarioRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            // Verificar se o role corresponde
            if (!usuario.getRole().name().equals(loginRequest.getRole())) {
                throw new RuntimeException("Perfil não corresponde ao usuário");
            }
            
            // Atualizar último acesso
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuarioRepository.save(usuario);
            
            // Gerar token JWT
            String token = jwtService.generateToken(usuario);
            
            return new LoginResponse(token, usuario);
            
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas", e);
        }
    }
    
    public void logout(String token) {
        // Implementar blacklist de tokens se necessário
        // Por enquanto, apenas log
        System.out.println("Logout realizado para token: " + token);
    }
}