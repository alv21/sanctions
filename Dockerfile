FROM openjdk:8-jre-alpine

COPY ./target/sanctions-1.0-SNAPSHOT.jar sanctions.jar

EXPOSE 8080

CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/sanctions.jar"]
