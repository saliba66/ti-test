package com.unipark.backend.dto;

import com.unipark.backend.entity.Vaga;

import java.time.LocalDateTime;

public class VagaDTO {
    
    private Long id;
    private String codigo;
    private String status;
    private String setor;
    private String tipo;
    private String observacoes;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaAtualizacao;
    
    // Construtores
    public VagaDTO() {}
    
    public VagaDTO(Vaga vaga) {
        this.id = vaga.getId();
        this.codigo = vaga.getCodigo();
        this.status = vaga.getStatus().name();
        this.setor = vaga.getSetor();
        this.tipo = vaga.getTipo() != null ? vaga.getTipo().name() : null;
        this.observacoes = vaga.getObservacoes();
        this.dataCriacao = vaga.getDataCriacao();
        this.ultimaAtualizacao = vaga.getUltimaAtualizacao();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSetor() {
        return setor;
    }
    
    public void setSetor(String setor) {
        this.setor = setor;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
    
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}