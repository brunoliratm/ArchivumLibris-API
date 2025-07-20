FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/archivumlibris-*.jar app.jar

RUN apk --no-cache add curl

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]