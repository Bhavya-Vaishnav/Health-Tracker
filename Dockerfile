FROM maven:3.9.12-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-XX:TieredStopAtLevel=1","-jar","app.jar"]
# -XX:TieredStopAtLevel=1 is a Java Virtual Machine (JVM) option that controls the tiered compilation behavior of the JVM.
# It is used to optimize the startup time from 202 to 58.4 sec.