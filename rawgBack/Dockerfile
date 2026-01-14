# Cambiamos openjdk por eclipse-temurin que es la oficial y estable hoy día
FROM eclipse-temurin:21-jdk-alpine

# Carpeta interna
WORKDIR /app

# Copiamos el JAR (Asegúrate de que el nombre coincide con lo que tienes en target)
COPY target/rawgback-0.0.1-SNAPSHOT.jar app.jar

# Puerto
EXPOSE 8084

# Arrancar
ENTRYPOINT ["java", "-jar", "app.jar"]