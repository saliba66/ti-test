package com.unipark.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @NotNull(message = "Tipo de movimentação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;
    
    @Column(name = "placa_veiculo")
    private String placaVeiculo;
    
    @Column(name = "modelo_veiculo")
    private String modeloVeiculo;
    
    @Column(name = "cor_veiculo")
    private String corVeiculo;
    
    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;
    
    @Column(name = "data_saida")
    private LocalDateTime dataSaida;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    @Column(name = "valor_cobrado")
    private Double valorCobrado;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    // Construtores
    public Movimentacao() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Movimentacao(Vaga vaga, Usuario usuario, TipoMovimentacao tipo, String placaVeiculo) {
        this();
        this.vaga = vaga;
        this.usuario = usuario;
        this.tipo = tipo;
        this.placaVeiculo = placaVeiculo;
        this.dataEntrada = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Vaga getVaga() {
        return vaga;
    }
    
    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public TipoMovimentacao getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
    
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }
    
    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }
    
    public String getModeloVeiculo() {
        return modeloVeiculo;
    }
    
    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }
    
    public String getCorVeiculo() {
        return corVeiculo;
    }
    
    public void setCorVeiculo(String corVeiculo) {
        this.corVeiculo = corVeiculo;
    }
    
    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
    
    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    
    public LocalDateTime getDataSaida() {
        return dataSaida;
    }
    
    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public Double getValorCobrado() {
        return valorCobrado;
    }
    
    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    // Enum
    public enum TipoMovimentacao {
        ENTRADA, SAIDA, RESERVA, CANCELAMENTO
    }
}