# 🚀 Unipark Backend - Instruções de Execução

## 📋 Pré-requisitos

Antes de executar o backend, certifique-se de ter instalado:

### 1. Java 17 ou superior
```bash
# Verificar versão do Java
java -version

# Se não tiver instalado, instale:
# Ubuntu/Debian:
sudo apt update
sudo apt install openjdk-17-jdk

# Windows: Baixe do site oficial da Oracle ou OpenJDK
# macOS: brew install openjdk@17
```

### 2. Maven 3.6 ou superior
```bash
# Verificar versão do Maven
mvn -version

# Se não tiver instalado:
# Ubuntu/Debian:
sudo apt install maven

# Windows: Baixe do site oficial do Maven
# macOS: brew install maven
```

### 3. MySQL 8.0 ou superior
```bash
# Ubuntu/Debian:
sudo apt install mysql-server

# Windows: Baixe do site oficial do MySQL
# macOS: brew install mysql

# Iniciar MySQL:
sudo systemctl start mysql
# ou no Windows: net start mysql
```

## 🗄️ Configuração do Banco de Dados

### 1. Criar o banco de dados
```sql
-- Conectar ao MySQL como root
mysql -u root -p

-- Criar o banco de dados
CREATE DATABASE unipark;

-- Criar usuário (opcional, mas recomendado)
CREATE USER 'unipark'@'localhost' IDENTIFIED BY 'unipark123';
GRANT ALL PRIVILEGES ON unipark.* TO 'unipark'@'localhost';
FLUSH PRIVILEGES;

-- Sair do MySQL
EXIT;
```

### 2. Configurar credenciais (se necessário)
Edite o arquivo `src/main/resources/application.yml` e ajuste as credenciais:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/unipark?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: root  # ou 'unipark' se criou o usuário
    password: sua_senha_aqui  # sua senha do MySQL
```

## 🚀 Executando a Aplicação

### Método 1: Usando o script (Linux/macOS)
```bash
# Dar permissão de execução
chmod +x start.sh

# Executar
./start.sh
```

### Método 2: Usando Maven diretamente
```bash
# Compilar e executar
mvn clean spring-boot:run

# Ou apenas executar (se já compilou)
mvn spring-boot:run
```

### Método 3: Compilar e executar o JAR
```bash
# Compilar
mvn clean package

# Executar o JAR
java -jar target/backend-1.0.0.jar
```

## 🧪 Testando a Aplicação

### 1. Verificar se está rodando
A aplicação estará disponível em: `http://localhost:8080/api`

### 2. Testar com curl
```bash
# Teste de login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "admin",
    "senha": "123456",
    "role": "ADMIN"
  }'
```

### 3. Usar o script de teste
```bash
# Dar permissão de execução
chmod +x test-api.sh

# Executar testes
./test-api.sh
```

## 📊 Usuários Padrão

A aplicação vem com usuários pré-cadastrados:

| Login | Senha | Role | Descrição |
|-------|-------|------|-----------|
| admin | 123456 | ADMIN | Administrador do sistema |
| atendente1 | 123456 | ATENDENTE | Atendente 1 |
| atendente2 | 123456 | ATENDENTE | Atendente 2 |
| operador1 | 123456 | OPERADOR | Operador 1 |

## 🔧 Configurações Avançadas

### Perfis de Execução
```bash
# Desenvolvimento (padrão)
mvn spring-boot:run

# Teste
mvn spring-boot:run -Dspring-boot.run.profiles=test

# Produção
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Configurações de Ambiente
- **Desenvolvimento**: `application-dev.yml`
- **Teste**: `application-test.yml`
- **Produção**: `application-prod.yml`

## 🐳 Usando Docker (Opcional)

### 1. Usar Docker Compose
```bash
# Executar com MySQL incluído
docker-compose up -d

# Parar
docker-compose down
```

### 2. Build manual
```bash
# Construir imagem
docker build -t unipark-backend .

# Executar container
docker run -p 8080:8080 unipark-backend
```

## 📡 Endpoints da API

### Autenticação
- `POST /api/auth/login` - Login de usuário
- `POST /api/auth/logout` - Logout de usuário

### Usuários
- `GET /api/usuarios` - Listar todos os usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `GET /api/usuarios/por-role/{role}` - Buscar por role
- `GET /api/usuarios/ativos` - Listar usuários ativos

### Vagas
- `GET /api/vagas` - Listar todas as vagas
- `GET /api/vagas/{id}` - Buscar vaga por ID
- `GET /api/vagas/por-status/{status}` - Buscar por status
- `GET /api/vagas/por-setor/{setor}` - Buscar por setor
- `GET /api/vagas/livres` - Listar vagas livres
- `GET /api/vagas/ocupadas` - Listar vagas ocupadas
- `GET /api/vagas/estatisticas` - Estatísticas das vagas

## 🔍 Solução de Problemas

### Erro de Conexão com MySQL
```bash
# Verificar se MySQL está rodando
sudo systemctl status mysql

# Iniciar MySQL
sudo systemctl start mysql

# Verificar porta
netstat -tlnp | grep 3306
```

### Erro de Porta em Uso
```bash
# Verificar processo na porta 8080
lsof -i :8080

# Matar processo
kill -9 PID_DO_PROCESSO
```

### Erro de Compilação
```bash
# Limpar cache do Maven
mvn clean

# Recompilar
mvn compile
```

### Erro de Dependências
```bash
# Atualizar dependências
mvn dependency:resolve

# Forçar atualização
mvn clean install -U
```

## 📝 Logs

Os logs são exibidos no console durante a execução. Para arquivo de log:

```yaml
# Em application.yml
logging:
  file:
    name: logs/unipark-backend.log
```

## 🔐 Segurança

- **JWT**: Tokens com expiração de 24 horas
- **CORS**: Configurado para aceitar requisições do frontend
- **Validação**: Campos obrigatórios e formatos validados
- **Senhas**: Criptografadas com BCrypt

## 📞 Suporte

Se encontrar problemas:

1. Verifique os logs no console
2. Confirme se todas as dependências estão instaladas
3. Verifique se o MySQL está rodando
4. Teste a conectividade: `telnet localhost 3306`
5. Verifique se a porta 8080 está livre

## 🎯 Próximos Passos

1. Conectar com o frontend
2. Implementar controllers restantes (Movimentação, Relatórios)
3. Adicionar testes unitários
4. Configurar CI/CD
5. Implementar monitoramento