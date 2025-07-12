# ---------- Stage 1: Build the application ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and skip tests for faster build (optional)
RUN mvn clean package -DskipTests

# ---------- Stage 2: Create runtime image ----------
FROM eclipse-temurin:17-jdk-alpine

# Create app directory in runtime container
WORKDIR /app

# Copy the generated jar from the builder container
COPY --from=build /app/target/*.jar app.jar

# Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
