package com.unipark.backend.repository;

import com.unipark.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByLogin(String login);
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByMatricula(String matricula);
    
    List<Usuario> findByRole(Usuario.Role role);
    
    List<Usuario> findByAtivoTrue();
    
    @Query("SELECT u FROM Usuario u WHERE u.role = :role AND u.ativo = true")
    List<Usuario> findAtivosByRole(@Param("role") Usuario.Role role);
    
    boolean existsByLogin(String login);
    
    boolean existsByEmail(String email);
    
    boolean existsByMatricula(String matricula);
}