# Use the official OpenJDK image as the base image.
FROM eclipse-temurin:21-jdk-alpine

# Work directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Applications port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]