FROM openjdk:17
COPY build/libs/homemate-0.0.1-SNAPSHOT.jar homemate.jar
ENTRYPOINT ["java","-jar","homemate.jar"]