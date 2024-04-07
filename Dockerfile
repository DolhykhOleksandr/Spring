FROM openjdk:17
COPY target/Spring-0.0.1-SNAPSHOT.jar /project.jar
CMD ["java", "-jar", "project.jar"]