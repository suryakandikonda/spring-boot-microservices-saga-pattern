FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=spring-boot-mircroservices-parent-builder /artifacts/order-service-app.war app.war
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.war"]