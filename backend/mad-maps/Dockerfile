FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /tmp/

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline
COPY ./src ./src

RUN ./mvnw clean install -Dskiptests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app/

COPY --from=builder /tmp/target/*.jar /app/myapp.jar

CMD ["java", "-jar", "/app/myapp.jar"]