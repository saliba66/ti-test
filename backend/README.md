# Unipark Backend

Sistema de Estacionamento Universitário - Backend em Java Spring Boot

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados
- **JWT** - Tokens de autenticação
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/unipark/backend/
│   │   │   ├── config/          # Configurações (Security, CORS, etc.)
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   ├── service/        # Serviços de negócio
│   │   │   └── UniparkBackendApplication.java
│   │   └── resources/
│   │       ├── application.yml # Configurações da aplicação
│   │       └── data.sql        # Dados iniciais
│   └── test/
└── pom.xml
```

## Configuração do Banco de Dados

### Pré-requisitos
- MySQL 8.0 ou superior
- Java 17
- Maven 3.6+

### Configuração do MySQL

1. Instale o MySQL
2. Crie um banco de dados chamado `unipark`:
```sql
CREATE DATABASE unipark;
```

3. Configure as credenciais no arquivo `application.yml`:
```yaml
spring:
  datasource:
    username: root
    password: sua_senha_aqui
```

## Executando a Aplicação

### 1. Clone o repositório e navegue até a pasta backend:
```bash
cd backend
```

### 2. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080/api`

## Endpoints da API

### Autenticação
- `POST /api/auth/login` - Login de usuário
- `POST /api/auth/logout` - Logout de usuário

### Usuários
- `GET /api/usuarios` - Listar todos os usuários (ADMIN/ATENDENTE)
- `GET /api/usuarios/{id}` - Buscar usuário por ID (ADMIN/ATENDENTE)
- `GET /api/usuarios/por-role/{role}` - Buscar usuários por role (ADMIN)
- `GET /api/usuarios/ativos` - Listar usuários ativos (ADMIN/ATENDENTE)

### Vagas
- `GET /api/vagas` - Listar todas as vagas (ADMIN/ATENDENTE)
- `GET /api/vagas/{id}` - Buscar vaga por ID (ADMIN/ATENDENTE)
- `GET /api/vagas/por-status/{status}` - Buscar vagas por status (ADMIN/ATENDENTE)
- `GET /api/vagas/por-setor/{setor}` - Buscar vagas por setor (ADMIN/ATENDENTE)
- `GET /api/vagas/livres` - Listar vagas livres (ADMIN/ATENDENTE)
- `GET /api/vagas/ocupadas` - Listar vagas ocupadas (ADMIN/ATENDENTE)
- `GET /api/vagas/estatisticas` - Obter estatísticas das vagas (ADMIN/ATENDENTE)

## Usuários Padrão

A aplicação vem com usuários de exemplo pré-cadastrados:

| Login | Senha | Role | Descrição |
|-------|-------|------|-----------|
| admin | 123456 | ADMIN | Administrador do sistema |
| atendente1 | 123456 | ATENDENTE | Atendente 1 |
| atendente2 | 123456 | ATENDENTE | Atendente 2 |
| operador1 | 123456 | OPERADOR | Operador 1 |

## Autenticação

A API utiliza JWT (JSON Web Tokens) para autenticação. Após o login, inclua o token no header `Authorization`:

```
Authorization: Bearer <seu_token_jwt>
```

## CORS

A aplicação está configurada para aceitar requisições de qualquer origem (`*`) para facilitar o desenvolvimento. Em produção, configure as origens específicas.

## Próximos Passos

1. Implementar controllers para Movimentação
2. Adicionar validações mais robustas
3. Implementar logs de auditoria
4. Adicionar testes unitários e de integração
5. Configurar Docker para containerização
6. Implementar cache com Redis
7. Adicionar documentação com Swagger/OpenAPI