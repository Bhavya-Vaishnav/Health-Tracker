FROM openjdk:22-jdk
ADD target/health-tracker.jar health-tracker.jar
ENTRYPOINT ["java", "-jar","/health-tracker.jar"]