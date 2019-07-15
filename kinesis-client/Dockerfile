FROM openjdk:11.0-slim

ARG EXPLODED_JAR_DIR=build/exploded

WORKDIR /kinesis_client

COPY ${EXPLODED_JAR_DIR}/BOOT-INF/lib ./lib
COPY ${EXPLODED_JAR_DIR}/META-INF ./META-INF
COPY ${EXPLODED_JAR_DIR}/BOOT-INF/classes .

USER root
RUN chmod -R 777 /tmp

EXPOSE 8080
ENTRYPOINT ["java", "-cp", ".:./lib/*", "com.yg0r2.kinesis.client.example.SampleApplication"]
