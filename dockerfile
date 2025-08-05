FROM eclipse-temurin:11-jre

# Copy the built Spring Boot jar
COPY build/libs/*.jar app.jar

# Expose port (change if needed)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "/app.jar"]
