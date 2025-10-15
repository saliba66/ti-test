package com.unipark.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "Login é obrigatório")
    private String login;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotBlank(message = "Perfil é obrigatório")
    private String role;
    
    // Construtores
    public LoginRequest() {}
    
    public LoginRequest(String login, String senha, String role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }
    
    // Getters e Setters
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}