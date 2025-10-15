package com.unipark.vaga;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vagas")
public class VagaController {

    private final VagaRepository vagaRepository;

    public VagaController(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    @GetMapping
    public List<Vaga> list() {
        return vagaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga create(@Valid @RequestBody Vaga vaga) {
        if (vaga.getStatus() == null || vaga.getStatus().isBlank()) {
            vaga.setStatus("LIVRE");
        }
        return vagaRepository.save(vaga);
    }

    @GetMapping("/{id}")
    public Vaga get(@PathVariable Long id) {
        return vagaRepository.findById(id).orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
    }

    @PutMapping("/{id}")
    public Vaga update(@PathVariable Long id, @Valid @RequestBody Vaga input) {
        Vaga existing = vagaRepository.findById(id).orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
        existing.setNumero(input.getNumero());
        existing.setTipo(input.getTipo());
        existing.setStatus(input.getStatus());
        existing.setPlacaAtual(input.getPlacaAtual());
        existing.setDesde(input.getDesde());
        return vagaRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vagaRepository.deleteById(id);
    }
}
