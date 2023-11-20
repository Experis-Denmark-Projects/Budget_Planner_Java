# Use an official Java runtime as a parent image
FROM gradle:jdk17-alpine AS build

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Run the Java application
RUN gradle bootJar

FROM openjdk:17 as runtime
WORKDIR /app
ARG JAR_FILE=/app/build/libs/budget-planner-0.0.1.jar
COPY --from=build ${JAR_FILE} /app/app.jar

ENV HIBERNATE_DDL "none"
ENV DB_SEED "never"
ENV SPRING_PROFILE "prod"

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]