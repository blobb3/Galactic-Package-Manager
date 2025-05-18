# Backend Build
FROM openjdk:21-slim as build
WORKDIR /app
COPY backend .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Runtime image
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]