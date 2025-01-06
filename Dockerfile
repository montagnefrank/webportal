FROM gradle:8.0-jdk17 AS build

WORKDIR /app
COPY build.gradle . 
COPY src /app/src

RUN gradle build --no-daemon

FROM amazoncorretto:17.0.8-alpine3.18

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
