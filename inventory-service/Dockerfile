FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=spring-boot-mircroservices-parent-builder /artifacts/inventory-service-app.war app.war
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.war"]