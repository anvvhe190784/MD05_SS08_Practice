# Stage 1: Builder sử dụng Gradle 8.5 + JDK 17
FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle bootJar --no-daemon

# Stage 2: Production runtime siêu nhẹ với JRE 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
LABEL org.opencontainers.image.source="https://github.com/anvvhe190784/MD05_SS08_Practice"
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
