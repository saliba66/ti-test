package com.unipark.backend.repository;

import com.unipark.backend.entity.Movimentacao;
import com.unipark.backend.entity.Usuario;
import com.unipark.backend.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    
    List<Movimentacao> findByUsuario(Usuario usuario);
    
    List<Movimentacao> findByVaga(Vaga vaga);
    
    List<Movimentacao> findByTipo(Movimentacao.TipoMovimentacao tipo);
    
    List<Movimentacao> findByPlacaVeiculo(String placaVeiculo);
    
    @Query("SELECT m FROM Movimentacao m WHERE m.dataEntrada >= :dataInicio AND m.dataEntrada <= :dataFim")
    List<Movimentacao> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT m FROM Movimentacao m WHERE m.usuario = :usuario AND m.dataEntrada >= :dataInicio AND m.dataEntrada <= :dataFim")
    List<Movimentacao> findByUsuarioAndPeriodo(@Param("usuario") Usuario usuario, @Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT m FROM Movimentacao m WHERE m.dataSaida IS NULL")
    List<Movimentacao> findMovimentacoesAtivas();
    
    @Query("SELECT m FROM Movimentacao m WHERE m.vaga = :vaga AND m.dataSaida IS NULL")
    List<Movimentacao> findMovimentacaoAtivaByVaga(@Param("vaga") Vaga vaga);
    
    @Query("SELECT COUNT(m) FROM Movimentacao m WHERE m.dataEntrada >= :dataInicio AND m.dataEntrada <= :dataFim")
    long countByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
}