FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=spring-boot-mircroservices-parent-builder /artifacts/payment-service-app.war app.war
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.war"]