FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /tmp/

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline
COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app/

COPY --from=builder /tmp/target/*.jar /app/myapp.jar

CMD ["java", "-jar", "/app/myapp.jar"]