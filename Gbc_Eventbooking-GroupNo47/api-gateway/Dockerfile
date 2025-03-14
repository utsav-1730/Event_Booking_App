# build stage
FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle .. /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build -x test

#package stage
FROM openjdk:22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gateway.jar

EXPOSE 8011

ENTRYPOINT ["java","-jar", "/app/api-gateway.jar"]