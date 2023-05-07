FROM openjdk:8-jre-alpine
WORKDIR /app
COPY ./build/libs/Masterwork-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]