# --- Fase 1: Construcción (Build Stage) ---
# Usamos una imagen de Java (Eclipse Temurin) con Gradle para construir el proyecto.
FROM gradle:8.4-jdk17-alpine AS build

# Establecemos el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copiamos solo los archivos de build de Gradle para aprovechar la caché de Docker.
# Esto hace que si no cambias las dependencias, las builds futuras sean más rápidas.
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Copiamos el resto del código fuente de la aplicación.
COPY src ./src

# Ejecutamos el comando de Gradle para construir la aplicación y generar el .jar.
# --no-daemon es importante para entornos de CI/CD como Render.
RUN gradle build --no-daemon


# --- Fase 2: Ejecución (Runtime Stage) ---
# Usamos una imagen de Java mucho más ligera, solo para ejecutar la aplicación.
# Esto hace que la imagen final sea pequeña y segura.
FROM eclipse-temurin:17-jre-alpine

# Establecemos el directorio de trabajo.
WORKDIR /app

# Copiamos el archivo .jar generado en la fase de construcción.
COPY --from=build /app/build/libs/camisas-api-0.0.1-SNAPSHOT.jar ./app.jar

# Exponemos el puerto 8080 (el puerto por defecto de Spring Boot)
# Render usará este puerto para dirigir el tráfico a tu aplicación.
EXPOSE 8080

# El comando que se ejecutará cuando el contenedor arranque.
ENTRYPOINT ["java", "-jar", "app.jar"]
