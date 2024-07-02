# Stage 1: Build
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/jw-park-github/backend-version3-main.git .
RUN gradle bootJar

# Stage 2: Run
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/backend-0.0.1.jar backend.jar
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
EXPOSE 8080
