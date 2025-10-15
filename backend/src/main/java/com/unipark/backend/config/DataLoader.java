package com.unipark.backend.config;

import com.unipark.backend.entity.Usuario;
import com.unipark.backend.entity.Vaga;
import com.unipark.backend.entity.Movimentacao;
import com.unipark.backend.repository.UsuarioRepository;
import com.unipark.backend.repository.VagaRepository;
import com.unipark.backend.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VagaRepository vagaRepository;
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existem dados
        if (usuarioRepository.count() > 0) {
            return;
        }
        
        // Criar usuários
        Usuario admin = new Usuario();
        admin.setLogin("admin");
        admin.setSenha(passwordEncoder.encode("123456"));
        admin.setNome("Administrador");
        admin.setEmail("admin@unipark.com");
        admin.setRole(Usuario.Role.ADMIN);
        admin.setMatricula("ADM001");
        admin.setAtivo(true);
        admin.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(admin);
        
        Usuario atendente1 = new Usuario();
        atendente1.setLogin("atendente1");
        atendente1.setSenha(passwordEncoder.encode("123456"));
        atendente1.setNome("João Silva");
        atendente1.setEmail("joao@unipark.com");
        atendente1.setRole(Usuario.Role.ATENDENTE);
        atendente1.setMatricula("ATD001");
        atendente1.setAtivo(true);
        atendente1.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(atendente1);
        
        Usuario atendente2 = new Usuario();
        atendente2.setLogin("atendente2");
        atendente2.setSenha(passwordEncoder.encode("123456"));
        atendente2.setNome("Maria Santos");
        atendente2.setEmail("maria@unipark.com");
        atendente2.setRole(Usuario.Role.ATENDENTE);
        atendente2.setMatricula("ATD002");
        atendente2.setAtivo(true);
        atendente2.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(atendente2);
        
        Usuario operador1 = new Usuario();
        operador1.setLogin("operador1");
        operador1.setSenha(passwordEncoder.encode("123456"));
        operador1.setNome("Pedro Costa");
        operador1.setEmail("pedro@unipark.com");
        operador1.setRole(Usuario.Role.OPERADOR);
        operador1.setMatricula("OPR001");
        operador1.setAtivo(true);
        operador1.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(operador1);
        
        // Criar vagas
        Vaga vaga1 = new Vaga();
        vaga1.setCodigo("A001");
        vaga1.setStatus(Vaga.StatusVaga.LIVRE);
        vaga1.setSetor("Setor A");
        vaga1.setTipo(Vaga.TipoVaga.NORMAL);
        vaga1.setObservacoes("Vaga próxima à entrada");
        vaga1.setDataCriacao(LocalDateTime.now());
        vaga1.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga1);
        
        Vaga vaga2 = new Vaga();
        vaga2.setCodigo("A002");
        vaga2.setStatus(Vaga.StatusVaga.OCUPADA);
        vaga2.setSetor("Setor A");
        vaga2.setTipo(Vaga.TipoVaga.NORMAL);
        vaga2.setObservacoes("Vaga central");
        vaga2.setDataCriacao(LocalDateTime.now());
        vaga2.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga2);
        
        Vaga vaga3 = new Vaga();
        vaga3.setCodigo("A003");
        vaga3.setStatus(Vaga.StatusVaga.LIVRE);
        vaga3.setSetor("Setor A");
        vaga3.setTipo(Vaga.TipoVaga.NORMAL);
        vaga3.setObservacoes("Vaga no final do corredor");
        vaga3.setDataCriacao(LocalDateTime.now());
        vaga3.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga3);
        
        Vaga vaga4 = new Vaga();
        vaga4.setCodigo("B001");
        vaga4.setStatus(Vaga.StatusVaga.LIVRE);
        vaga4.setSetor("Setor B");
        vaga4.setTipo(Vaga.TipoVaga.PREFERENCIAL);
        vaga4.setObservacoes("Vaga para idosos e PCD");
        vaga4.setDataCriacao(LocalDateTime.now());
        vaga4.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga4);
        
        Vaga vaga5 = new Vaga();
        vaga5.setCodigo("B002");
        vaga5.setStatus(Vaga.StatusVaga.RESERVADA);
        vaga5.setSetor("Setor B");
        vaga5.setTipo(Vaga.TipoVaga.NORMAL);
        vaga5.setObservacoes("Vaga reservada para evento");
        vaga5.setDataCriacao(LocalDateTime.now());
        vaga5.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga5);
        
        Vaga vaga6 = new Vaga();
        vaga6.setCodigo("C001");
        vaga6.setStatus(Vaga.StatusVaga.LIVRE);
        vaga6.setSetor("Setor C");
        vaga6.setTipo(Vaga.TipoVaga.MOTOCICLETA);
        vaga6.setObservacoes("Vaga para motocicletas");
        vaga6.setDataCriacao(LocalDateTime.now());
        vaga6.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga6);
        
        Vaga vaga7 = new Vaga();
        vaga7.setCodigo("C002");
        vaga7.setStatus(Vaga.StatusVaga.MANUTENCAO);
        vaga7.setSetor("Setor C");
        vaga7.setTipo(Vaga.TipoVaga.NORMAL);
        vaga7.setObservacoes("Em manutenção - piso danificado");
        vaga7.setDataCriacao(LocalDateTime.now());
        vaga7.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga7);
        
        Vaga vaga8 = new Vaga();
        vaga8.setCodigo("D001");
        vaga8.setStatus(Vaga.StatusVaga.LIVRE);
        vaga8.setSetor("Setor D");
        vaga8.setTipo(Vaga.TipoVaga.CARGA_DESCARGA);
        vaga8.setObservacoes("Vaga para carga e descarga");
        vaga8.setDataCriacao(LocalDateTime.now());
        vaga8.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga8);
        
        Vaga vaga9 = new Vaga();
        vaga9.setCodigo("D002");
        vaga9.setStatus(Vaga.StatusVaga.LIVRE);
        vaga9.setSetor("Setor D");
        vaga9.setTipo(Vaga.TipoVaga.NORMAL);
        vaga9.setObservacoes("Vaga ampla");
        vaga9.setDataCriacao(LocalDateTime.now());
        vaga9.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga9);
        
        Vaga vaga10 = new Vaga();
        vaga10.setCodigo("E001");
        vaga10.setStatus(Vaga.StatusVaga.OCUPADA);
        vaga10.setSetor("Setor E");
        vaga10.setTipo(Vaga.TipoVaga.NORMAL);
        vaga10.setObservacoes("Vaga coberta");
        vaga10.setDataCriacao(LocalDateTime.now());
        vaga10.setUltimaAtualizacao(LocalDateTime.now());
        vagaRepository.save(vaga10);
        
        // Criar movimentações
        Movimentacao mov1 = new Movimentacao();
        mov1.setVaga(vaga2);
        mov1.setUsuario(atendente1);
        mov1.setTipo(Movimentacao.TipoMovimentacao.ENTRADA);
        mov1.setPlacaVeiculo("ABC1234");
        mov1.setModeloVeiculo("Honda Civic");
        mov1.setCorVeiculo("Prata");
        mov1.setDataEntrada(LocalDateTime.now().minusHours(2));
        mov1.setObservacoes("Cliente regular");
        mov1.setDataCriacao(LocalDateTime.now().minusHours(2));
        movimentacaoRepository.save(mov1);
        
        Movimentacao mov2 = new Movimentacao();
        mov2.setVaga(vaga5);
        mov2.setUsuario(atendente2);
        mov2.setTipo(Movimentacao.TipoMovimentacao.ENTRADA);
        mov2.setPlacaVeiculo("DEF5678");
        mov2.setModeloVeiculo("Toyota Corolla");
        mov2.setCorVeiculo("Branco");
        mov2.setDataEntrada(LocalDateTime.now().minusHours(1));
        mov2.setObservacoes("Reserva confirmada");
        mov2.setDataCriacao(LocalDateTime.now().minusHours(1));
        movimentacaoRepository.save(mov2);
        
        Movimentacao mov3 = new Movimentacao();
        mov3.setVaga(vaga10);
        mov3.setUsuario(operador1);
        mov3.setTipo(Movimentacao.TipoMovimentacao.ENTRADA);
        mov3.setPlacaVeiculo("GHI9012");
        mov3.setModeloVeiculo("Ford Focus");
        mov3.setCorVeiculo("Azul");
        mov3.setDataEntrada(LocalDateTime.now().minusMinutes(30));
        mov3.setObservacoes("Vaga coberta");
        mov3.setDataCriacao(LocalDateTime.now().minusMinutes(30));
        movimentacaoRepository.save(mov3);
        
        System.out.println("✅ Dados iniciais carregados com sucesso!");
    }
}