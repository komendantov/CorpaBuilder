FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/corpabuilder-0.0.1-SNAPSHOT.jar
ARG MYSTEM=/mystem
# cp spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} corpabuilder-0.0.1-SNAPSHOT.jar
COPY ${MYSTEM} mystem
ENTRYPOINT ["java","-jar","-DmystemPath=/mystem","corpabuilder-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080