# ETAPA 1: CONSTRUCCIÓN (BUILD)
# Usamos una imagen de Maven con Java 21 para compilar el código
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos solo el archivo de configuración primero
COPY pom.xml .
# Descargamos dependencias
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src
# Compilamos el proyecto y creamos el .jar
RUN mvn clean package -DskipTests

# ETAPA 2: EJECUCIÓN (RUN)
# Usamos la imagen ligera de Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# COPIAMOS el .jar DESDE la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Variable por defecto
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]