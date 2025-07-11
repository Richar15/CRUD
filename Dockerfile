# 🔹 IMAGEN BASE CON JAVA 17
FROM eclipse-temurin:17.0.11_9-jdk

# INFORMAR EL PUERTO DONDE CORRE EL BACK (INFORMATIVO)
EXPOSE 8080

# 🔹 DIRECTORIO DE TRABAJO
WORKDIR /app

# 🔹 COPIAR ARCHIVOS DE MAVEN Y POM
COPY ./pom.xml ./mvnw ./
COPY .mvn ./.mvn

# 🔹 DAR PERMISOS AL WRAPPER
RUN chmod +x ./mvnw

# 🔹 DESCARGAR DEPENDENCIAS SIN COMPILAR AÚN
RUN ./mvnw dependency:go-offline

# 🔹 COPIAR EL CÓDIGO FUENTE
COPY ./src ./src

# 🔹 COMPILAR EL PROYECTO
RUN ./mvnw clean install -DskipTests

# 🔹 EJECUTAR EL .JAR AL LEVANTAR EL CONTENEDOR
ENTRYPOINT ["java", "-jar", "target/CRUD-0.0.1-SNAPSHOT.jar"]