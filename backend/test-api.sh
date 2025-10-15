#!/bin/bash

echo "🧪 Testando API do Unipark Backend..."

BASE_URL="http://localhost:8080/api"

# Função para fazer requisições
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local token=$4
    
    if [ -n "$token" ]; then
        if [ -n "$data" ]; then
            curl -X $method "$BASE_URL$endpoint" \
                -H "Content-Type: application/json" \
                -H "Authorization: Bearer $token" \
                -d "$data"
        else
            curl -X $method "$BASE_URL$endpoint" \
                -H "Authorization: Bearer $token"
        fi
    else
        if [ -n "$data" ]; then
            curl -X $method "$BASE_URL$endpoint" \
                -H "Content-Type: application/json" \
                -d "$data"
        else
            curl -X $method "$BASE_URL$endpoint"
        fi
    fi
    echo ""
}

echo "1. Testando login como administrador..."
LOGIN_RESPONSE=$(make_request "POST" "/auth/login" '{"login":"admin","senha":"123456","role":"ADMIN"}')
echo "Resposta: $LOGIN_RESPONSE"

# Extrair token da resposta (assumindo que retorna JSON com campo 'token')
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -n "$TOKEN" ]; then
    echo "✅ Login realizado com sucesso!"
    echo "Token: $TOKEN"
    echo ""
    
    echo "2. Testando listagem de usuários..."
    make_request "GET" "/usuarios" "" "$TOKEN"
    
    echo "3. Testando listagem de vagas..."
    make_request "GET" "/vagas" "" "$TOKEN"
    
    echo "4. Testando estatísticas das vagas..."
    make_request "GET" "/vagas/estatisticas" "" "$TOKEN"
    
    echo "5. Testando vagas livres..."
    make_request "GET" "/vagas/livres" "" "$TOKEN"
    
    echo "6. Testando logout..."
    make_request "POST" "/auth/logout" "" "$TOKEN"
    
else
    echo "❌ Falha no login. Verifique se o backend está rodando e se as credenciais estão corretas."
fi

echo ""
echo "🏁 Testes concluídos!"