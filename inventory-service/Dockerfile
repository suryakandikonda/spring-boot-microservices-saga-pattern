FROM tomcat:11-jdk21

ENV CATALINA_HOME /usr/local/tomcat

WORKDIR /usr/local/tomcat/webapps/

COPY --from=spring-boot-mircroservices-parent-builder /artifacts/inventory-service-app.war ROOT.war

EXPOSE 8082

CMD ["catalina.sh", "run"]