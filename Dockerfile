FROM gradle:latest as build
COPY ./ /tmp
WORKDIR /tmp
RUN gradle bootJar

FROM eclipse-temurin:11-jdk-alpine
COPY --from=build /tmp/build/libs/*-SNAPSHOT.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar"]