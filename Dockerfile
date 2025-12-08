# 1. Imagen base con Java 21 (liviana)
FROM eclipse-temurin:21-jre

# 2. Directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiar el JAR generado por Spring Boot
COPY target/biblioteca-virtual-0.0.1-SNAPSHOT.jar app.jar

# 4. Exponer el puerto del backend
EXPOSE 8080

# 5. Variables de entorno (se pueden sobreescribir)
ENV SPRING_PROFILES_ACTIVE=docker

# 6. Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]