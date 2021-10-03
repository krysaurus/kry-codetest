FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

WORKDIR /app

COPY gradlew .
COPY build.gradle .
COPY gradle ./gradle
COPY src ./src
COPY build ./build

COPY build/libs/*.jar kry-poller.jar

CMD ["java", "-jar", "kry-poller.jar"]]