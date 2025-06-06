# Build stage
FROM gradle:jdk21-jammy AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Create a non-root user
RUN groupadd -r spring && useradd -r -g spring spring

# Copy the built artifact from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Set ownership to non-root user
RUN chown spring:spring /app
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 