# build stage
FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle .. /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build -x test

#package stage
FROM openjdk:22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/room-service.jar

ENV  POSTGRES_USER=admin \
     POSTGRES_PASSWORD=password

EXPOSE 8090

ENTRYPOINT ["java","-jar", "/app/room-service.jar"]