# Conexão Frontend-Backend - Unipark

Este documento explica como conectar o frontend existente com o backend Java Spring Boot criado.

## Estrutura Atual

### Frontend (JavaScript Vanilla)
- Localização: `/` (raiz do projeto)
- Arquivos principais: `index.html`, `app.js`, `router.js`, `store.js`, `ui.js`
- Porta: Servido via servidor local (ex: Live Server na porta 5500)

### Backend (Java Spring Boot)
- Localização: `/backend`
- Porta: `8080`
- Context path: `/api`

## Configuração da Conexão

### 1. Atualizar o Frontend para Consumir a API

#### Modificar `app.js` - Função de Login

Substitua a função `handleLogin` no arquivo `app.js`:

```javascript
async handleLogin(e) {
    e.preventDefault();
    
    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;
    const role = document.getElementById('roleSelect').value;
    
    if (!role) {
        this.ui.showToast('Por favor, selecione um perfil', 'error');
        return;
    }

    try {
        // Fazer requisição para o backend
        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                login: login,
                senha: senha,
                role: role
            })
        });

        if (response.ok) {
            const data = await response.json();
            
            // Armazenar token no localStorage
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify({
                id: data.id,
                login: data.login,
                nome: data.nome,
                email: data.email,
                role: data.role,
                matricula: data.matricula
            }));

            // Atualizar estado da aplicação
            this.currentUser = {
                role: data.role,
                name: data.nome
            };

            this.store.setAuth({ currentRole: data.role });
            this.updateUserInfo();
            this.updateSidebarVisibility();
            this.hideLogin();
            
            window.location.hash = '#/dashboard';
            this.router.handleRoute();
            
            this.ui.showToast(`Bem-vindo, ${data.nome}!`, 'success');
        } else {
            const error = await response.json();
            this.ui.showToast(error.error || 'Erro no login', 'error');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        this.ui.showToast('Erro de conexão com o servidor', 'error');
    }
}
```

#### Adicionar Função para Requisições Autenticadas

Adicione esta função à classe `App`:

```javascript
async makeAuthenticatedRequest(url, options = {}) {
    const token = localStorage.getItem('token');
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    };
    
    const mergedOptions = { ...defaultOptions, ...options };
    if (mergedOptions.body && typeof mergedOptions.body === 'object') {
        mergedOptions.body = JSON.stringify(mergedOptions.body);
    }
    
    const response = await fetch(`http://localhost:8080/api${url}`, mergedOptions);
    
    if (response.status === 401) {
        // Token expirado ou inválido
        this.handleLogout();
        throw new Error('Sessão expirada');
    }
    
    return response;
}
```

#### Atualizar Função de Logout

```javascript
handleLogout() {
    // Fazer logout no backend
    const token = localStorage.getItem('token');
    if (token) {
        fetch('http://localhost:8080/api/auth/logout', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).catch(error => {
            console.error('Erro no logout:', error);
        });
    }
    
    // Limpar dados locais
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    
    this.currentUser = null;
    this.store.setAuth({ currentRole: null });
    this.showLogin();
    
    document.getElementById('roleSelect').value = '';
    this.ui.showToast('Logout realizado com sucesso', 'info');
}
```

### 2. Exemplo de Consumo de Dados

#### Buscar Vagas

```javascript
async carregarVagas() {
    try {
        const response = await this.makeAuthenticatedRequest('/vagas');
        if (response.ok) {
            const vagas = await response.json();
            // Atualizar interface com as vagas
            this.atualizarInterfaceVagas(vagas);
        }
    } catch (error) {
        console.error('Erro ao carregar vagas:', error);
        this.ui.showToast('Erro ao carregar vagas', 'error');
    }
}
```

#### Buscar Estatísticas

```javascript
async carregarEstatisticas() {
    try {
        const response = await this.makeAuthenticatedRequest('/vagas/estatisticas');
        if (response.ok) {
            const stats = await response.json();
            // Atualizar dashboard com estatísticas
            this.atualizarDashboard(stats);
        }
    } catch (error) {
        console.error('Erro ao carregar estatísticas:', error);
    }
}
```

### 3. Configuração do CORS

O backend já está configurado para aceitar requisições do frontend. As configurações estão em `SecurityConfig.java`:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

## Executando o Sistema

### 1. Iniciar o Backend

```bash
cd backend
./start.sh
```

Ou usando Maven diretamente:
```bash
cd backend
mvn spring-boot:run
```

### 2. Iniciar o Frontend

Use um servidor local (Live Server, Python HTTP server, etc.):

```bash
# Com Python
python -m http.server 5500

# Com Node.js (se tiver http-server instalado)
npx http-server -p 5500
```

### 3. Testar a Conexão

Acesse `http://localhost:5500` e tente fazer login com:
- **Login**: admin
- **Senha**: 123456
- **Perfil**: Administrador

## Endpoints Disponíveis

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/logout` - Logout

### Usuários
- `GET /api/usuarios` - Listar usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `GET /api/usuarios/por-role/{role}` - Buscar por role
- `GET /api/usuarios/ativos` - Listar ativos

### Vagas
- `GET /api/vagas` - Listar vagas
- `GET /api/vagas/{id}` - Buscar vaga por ID
- `GET /api/vagas/por-status/{status}` - Buscar por status
- `GET /api/vagas/por-setor/{setor}` - Buscar por setor
- `GET /api/vagas/livres` - Listar vagas livres
- `GET /api/vagas/ocupadas` - Listar vagas ocupadas
- `GET /api/vagas/estatisticas` - Estatísticas

## Próximos Passos

1. **Implementar Controllers Restantes**: Movimentação, Relatórios, etc.
2. **Adicionar Validações**: Frontend e backend
3. **Implementar Cache**: Para melhor performance
4. **Adicionar Testes**: Unitários e de integração
5. **Configurar HTTPS**: Para produção
6. **Implementar Logs**: Auditoria e monitoramento
7. **Adicionar Documentação**: Swagger/OpenAPI

## Troubleshooting

### Erro de CORS
- Verifique se o backend está rodando na porta 8080
- Confirme as configurações de CORS no `SecurityConfig.java`

### Erro de Conexão
- Verifique se o MySQL está rodando
- Confirme as credenciais do banco em `application.yml`

### Token Inválido
- Verifique se o token está sendo enviado corretamente
- Confirme se o JWT_SECRET está configurado

### Banco de Dados
- Execute o script `data.sql` para dados iniciais
- Verifique se o banco `unipark` existe