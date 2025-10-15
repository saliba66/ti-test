-- Inserir usuários de exemplo
INSERT INTO usuarios (login, senha, nome, email, role, matricula, ativo, data_criacao) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Administrador', 'admin@unipark.com', 'ADMIN', 'ADM001', true, NOW()),
('atendente1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'João Silva', 'joao@unipark.com', 'ATENDENTE', 'ATD001', true, NOW()),
('atendente2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Maria Santos', 'maria@unipark.com', 'ATENDENTE', 'ATD002', true, NOW()),
('operador1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Pedro Costa', 'pedro@unipark.com', 'OPERADOR', 'OPR001', true, NOW());

-- Inserir vagas de exemplo
INSERT INTO vagas (codigo, status, setor, tipo, observacoes, data_criacao, ultima_atualizacao) VALUES
('A001', 'LIVRE', 'Setor A', 'NORMAL', 'Vaga próxima à entrada', NOW(), NOW()),
('A002', 'OCUPADA', 'Setor A', 'NORMAL', 'Vaga central', NOW(), NOW()),
('A003', 'LIVRE', 'Setor A', 'NORMAL', 'Vaga no final do corredor', NOW(), NOW()),
('B001', 'LIVRE', 'Setor B', 'PREFERENCIAL', 'Vaga para idosos e PCD', NOW(), NOW()),
('B002', 'RESERVADA', 'Setor B', 'NORMAL', 'Vaga reservada para evento', NOW(), NOW()),
('C001', 'LIVRE', 'Setor C', 'MOTOCICLETA', 'Vaga para motocicletas', NOW(), NOW()),
('C002', 'MANUTENCAO', 'Setor C', 'NORMAL', 'Em manutenção - piso danificado', NOW(), NOW()),
('D001', 'LIVRE', 'Setor D', 'CARGA_DESCARGA', 'Vaga para carga e descarga', NOW(), NOW()),
('D002', 'LIVRE', 'Setor D', 'NORMAL', 'Vaga ampla', NOW(), NOW()),
('E001', 'OCUPADA', 'Setor E', 'NORMAL', 'Vaga coberta', NOW(), NOW());

-- Inserir movimentações de exemplo
INSERT INTO movimentacoes (vaga_id, usuario_id, tipo, placa_veiculo, modelo_veiculo, cor_veiculo, data_entrada, data_saida, observacoes, valor_cobrado, data_criacao) VALUES
(2, 2, 'ENTRADA', 'ABC1234', 'Honda Civic', 'Prata', NOW() - INTERVAL 2 HOUR, NULL, 'Cliente regular', NULL, NOW() - INTERVAL 2 HOUR),
(5, 3, 'ENTRADA', 'DEF5678', 'Toyota Corolla', 'Branco', NOW() - INTERVAL 1 HOUR, NULL, 'Reserva confirmada', NULL, NOW() - INTERVAL 1 HOUR),
(10, 4, 'ENTRADA', 'GHI9012', 'Ford Focus', 'Azul', NOW() - INTERVAL 30 MINUTE, NULL, 'Vaga coberta', NULL, NOW() - INTERVAL 30 MINUTE);