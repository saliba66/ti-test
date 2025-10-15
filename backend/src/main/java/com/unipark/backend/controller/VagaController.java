package com.unipark.backend.controller;

import com.unipark.backend.dto.VagaDTO;
import com.unipark.backend.entity.Vaga;
import com.unipark.backend.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vagas")
@CrossOrigin(origins = "*")
public class VagaController {
    
    @Autowired
    private VagaRepository vagaRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<VagaDTO>> listarVagas() {
        List<Vaga> vagas = vagaRepository.findAll();
        List<VagaDTO> vagasDTO = vagas.stream()
            .map(VagaDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vagasDTO);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<VagaDTO> buscarVaga(@PathVariable Long id) {
        return vagaRepository.findById(id)
            .map(vaga -> ResponseEntity.ok(new VagaDTO(vaga)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/por-status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<VagaDTO>> buscarVagasPorStatus(@PathVariable String status) {
        try {
            Vaga.StatusVaga statusEnum = Vaga.StatusVaga.valueOf(status.toUpperCase());
            List<Vaga> vagas = vagaRepository.findByStatus(statusEnum);
            List<VagaDTO> vagasDTO = vagas.stream()
                .map(VagaDTO::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(vagasDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/por-setor/{setor}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<VagaDTO>> buscarVagasPorSetor(@PathVariable String setor) {
        List<Vaga> vagas = vagaRepository.findBySetor(setor);
        List<VagaDTO> vagasDTO = vagas.stream()
            .map(VagaDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vagasDTO);
    }
    
    @GetMapping("/livres")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<VagaDTO>> buscarVagasLivres() {
        List<Vaga> vagas = vagaRepository.findByStatus(Vaga.StatusVaga.LIVRE);
        List<VagaDTO> vagasDTO = vagas.stream()
            .map(VagaDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vagasDTO);
    }
    
    @GetMapping("/ocupadas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<List<VagaDTO>> buscarVagasOcupadas() {
        List<Vaga> vagas = vagaRepository.findByStatus(Vaga.StatusVaga.OCUPADA);
        List<VagaDTO> vagasDTO = vagas.stream()
            .map(VagaDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vagasDTO);
    }
    
    @GetMapping("/estatisticas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ATENDENTE')")
    public ResponseEntity<Object> obterEstatisticas() {
        long totalVagas = vagaRepository.count();
        long vagasLivres = vagaRepository.countByStatus(Vaga.StatusVaga.LIVRE);
        long vagasOcupadas = vagaRepository.countByStatus(Vaga.StatusVaga.OCUPADA);
        long vagasReservadas = vagaRepository.countByStatus(Vaga.StatusVaga.RESERVADA);
        long vagasManutencao = vagaRepository.countByStatus(Vaga.StatusVaga.MANUTENCAO);
        
        return ResponseEntity.ok(new Object() {
            public final long total = totalVagas;
            public final long livres = vagasLivres;
            public final long ocupadas = vagasOcupadas;
            public final long reservadas = vagasReservadas;
            public final long manutencao = vagasManutencao;
        });
    }
}