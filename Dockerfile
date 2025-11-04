# Use an official OpenJDK image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY target/*.jar app.jar

# Expose the Spring Boot port
EXPOSE 8081

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
