package com.unipark.vaga;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String numero;

    @NotBlank
    @Column(nullable = false)
    private String tipo; // COMUM, PCD, FUNCIONARIO, VISITANTE_ESPECIAL

    @NotBlank
    @Column(nullable = false)
    private String status; // LIVRE, OCUPADA

    private String placaAtual;

    private Long desde; // epoch millis

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlacaAtual() {
        return placaAtual;
    }

    public void setPlacaAtual(String placaAtual) {
        this.placaAtual = placaAtual;
    }

    public Long getDesde() {
        return desde;
    }

    public void setDesde(Long desde) {
        this.desde = desde;
    }
}
