# Build stage
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY src/ src/

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:24-jre-noble
WORKDIR /app

RUN groupadd -r acme && useradd -r -g acme acme

# Copy the built artifact from builder stage
COPY --from=builder /app/build/libs/acme-transactions.jar acme-transactions.jar

# Set ownership to non-root user
RUN chown acme:acme /app
USER acme

# Add health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "acme-transactions.jar"] 