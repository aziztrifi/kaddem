# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file (built from your Spring Boot project) into the container
COPY target/kaddem-0.0.1-SNAPSHOT.jar /app/kaddem.jar

# Optional: Set environment variables
# ENV SPRING_PROFILES_ACTIVE=prod
# ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Step 4: Expose the port your app runs on (default is 8080)
EXPOSE 8080

# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/kaddem.jar"]

# Optional: Add health check (for Kubernetes or Docker Swarm)
# HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD curl -f http://localhost:8080/actuator/health || exit 1
