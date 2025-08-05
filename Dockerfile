# Use official Java runtime base image
FROM eclipse-temurin:11-jre

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file (produced by Gradle build)
COPY build/libs/*.jar app.jar

# Expose port 8080 (or change if your app uses a different one)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
