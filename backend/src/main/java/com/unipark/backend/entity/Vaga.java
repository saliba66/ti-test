package com.unipark.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "vagas")
public class Vaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Código da vaga é obrigatório")
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;
    
    @Column(name = "setor")
    private String setor;
    
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoVaga tipo;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    
    // Construtores
    public Vaga() {
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
    }
    
    public Vaga(String codigo, StatusVaga status, String setor, TipoVaga tipo) {
        this();
        this.codigo = codigo;
        this.status = status;
        this.setor = setor;
        this.tipo = tipo;
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
    
    public StatusVaga getStatus() {
        return status;
    }
    
    public void setStatus(StatusVaga status) {
        this.status = status;
        this.ultimaAtualizacao = LocalDateTime.now();
    }
    
    public String getSetor() {
        return setor;
    }
    
    public void setSetor(String setor) {
        this.setor = setor;
    }
    
    public TipoVaga getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoVaga tipo) {
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
    
    // Enums
    public enum StatusVaga {
        LIVRE, OCUPADA, RESERVADA, MANUTENCAO
    }
    
    public enum TipoVaga {
        NORMAL, PREFERENCIAL, MOTOCICLETA, CARGA_DESCARGA
    }
}