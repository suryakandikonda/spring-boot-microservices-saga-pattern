FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=spring-boot-mircroservices-parent-builder /artifacts/saga-orchestrator-service.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]