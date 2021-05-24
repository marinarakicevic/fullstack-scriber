FROM maven:3.8.1-jdk-11-slim AS build
COPY . ./app
WORKDIR /app/music-application
RUN mvn clean install
EXPOSE 80

FROM openjdk:11
COPY --from=build /app/music-application/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
