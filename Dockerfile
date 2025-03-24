# Use an OpenJDK 17 base image
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy application JAR from the target folder
COPY target/quarkus-app/ /app/

# Expose the application port
EXPOSE 9990

# Run the application
CMD ["java", "-jar", "/app/quarkus-run.jar"]
