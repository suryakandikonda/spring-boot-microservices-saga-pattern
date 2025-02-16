
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests


FROM alpine:latest
WORKDIR /artifacts

COPY --from=builder /app/api-gateway/target/*.jar /artifacts/api-gateway-service.jar
COPY --from=builder /app/saga-orchestrator/target/*.jar /artifacts/saga-orchestrator-service.jar

COPY --from=builder /app/order-service/target/*.war /artifacts/order-service-app.war
COPY --from=builder /app/inventory-service/target/*.war /artifacts/inventory-service-app.war
COPY --from=builder /app/payment-service/target/*.war /artifacts/payment-service-app.war
