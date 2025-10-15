package com.unipark.backend.dto;

import com.unipark.backend.entity.Usuario;

public class LoginResponse {
    
    private String token;
    private String tipoToken = "Bearer";
    private Long id;
    private String login;
    private String nome;
    private String email;
    private String role;
    private String matricula;
    
    // Construtores
    public LoginResponse() {}
    
    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.role = usuario.getRole().name();
        this.matricula = usuario.getMatricula();
    }
    
    // Getters e Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTipoToken() {
        return tipoToken;
    }
    
    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}