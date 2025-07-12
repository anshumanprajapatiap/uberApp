# ---------- Stage 1: Build the application ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ---------- Stage 2: Runtime Image ----------
# Use Debian-based image to avoid alpine-related issues
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from builder stage
COPY --from=build /app/target/*.jar app.jar

# Copy resources (for application-prod.properties and data.sql)
COPY src/main/resources/application-prod.properties ./application-prod.properties
#COPY src/main/resources/data.sql ./data.sql

# Expose the app port
EXPOSE 8080

# Run the Spring Boot app with prod profile
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
