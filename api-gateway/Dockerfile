FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=spring-boot-mircroservices-parent-builder /artifacts/api-gateway-service.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]