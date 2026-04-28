# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests

# Run the jar
CMD ["java", "-jar", "target/*.jar"]