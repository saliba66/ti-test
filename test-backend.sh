#!/bin/bash

echo "🧪 Testando Backend Unipark..."

# Teste 1: Verificar se a aplicação está rodando
echo "1. Verificando se a aplicação está rodando..."
if curl -s http://localhost:8080/api/vagas > /dev/null 2>&1; then
    echo "✅ Aplicação está rodando!"
else
    echo "❌ Aplicação não está respondendo"
    exit 1
fi

# Teste 2: Testar endpoint de vagas (deve retornar 401 - não autenticado)
echo "2. Testando endpoint de vagas (sem autenticação)..."
response=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/vagas)
if [ "$response" = "401" ]; then
    echo "✅ Endpoint protegido corretamente (401 Unauthorized)"
else
    echo "❌ Resposta inesperada: $response"
fi

# Teste 3: Testar endpoint de login
echo "3. Testando endpoint de login..."
login_response=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","senha":"123456","role":"ADMIN"}')

if echo "$login_response" | grep -q "token"; then
    echo "✅ Login funcionando!"
    echo "Resposta: $login_response"
else
    echo "❌ Login falhou"
    echo "Resposta: $login_response"
fi

echo "🏁 Testes concluídos!"