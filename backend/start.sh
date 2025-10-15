#!/bin/bash

echo "🚀 Iniciando Unipark Backend..."

# Verificar se o Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale o Java 17 ou superior."
    exit 1
fi

# Verificar se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven 3.6 ou superior."
    exit 1
fi

# Verificar se o MySQL está rodando
if ! pgrep -x "mysqld" > /dev/null; then
    echo "⚠️  MySQL não está rodando. Por favor, inicie o MySQL antes de continuar."
    echo "   No Ubuntu/Debian: sudo systemctl start mysql"
    echo "   No macOS: brew services start mysql"
    echo "   No Windows: net start mysql"
    exit 1
fi

echo "✅ Dependências verificadas"

# Compilar e executar a aplicação
echo "🔨 Compilando a aplicação..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilação concluída"
    echo "🚀 Iniciando a aplicação..."
    mvn spring-boot:run
else
    echo "❌ Erro na compilação"
    exit 1
fi