package com.unipark.backend.repository;

import com.unipark.backend.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    
    Optional<Vaga> findByCodigo(String codigo);
    
    List<Vaga> findByStatus(Vaga.StatusVaga status);
    
    List<Vaga> findBySetor(String setor);
    
    List<Vaga> findByTipo(Vaga.TipoVaga tipo);
    
    @Query("SELECT v FROM Vaga v WHERE v.status = :status AND v.setor = :setor")
    List<Vaga> findByStatusAndSetor(@Param("status") Vaga.StatusVaga status, @Param("setor") String setor);
    
    @Query("SELECT COUNT(v) FROM Vaga v WHERE v.status = :status")
    long countByStatus(@Param("status") Vaga.StatusVaga status);
    
    @Query("SELECT COUNT(v) FROM Vaga v WHERE v.status = :status AND v.setor = :setor")
    long countByStatusAndSetor(@Param("status") Vaga.StatusVaga status, @Param("setor") String setor);
    
    boolean existsByCodigo(String codigo);
}