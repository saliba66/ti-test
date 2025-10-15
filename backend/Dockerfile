# Dockerfile para Unipark Backend
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY src ./src

# Compilar a aplicação
RUN mvn clean package -DskipTests

# Expor a porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/backend-1.0.0.jar"]