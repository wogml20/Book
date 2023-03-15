#FROM openjdk:8-jdk-alpine
#FROM openjdk:10.0.1-10-jdk-slim


FROM openjdk:11-jdk-slim
ADD build/libs/book-0.0.1-SNAPSHOT.jar /app.jar
ENV SPTRING_PROFIELS_ACTIVE=$SPRING_PROFILES_ACTIVE
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-jar","-Dspring-boot.run.profiles=kimjaehee","/app.jar"]

