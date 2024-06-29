ARG JAVA_OPTS

FROM azul/zulu-openjdk:17-latest

COPY ./webservice/build/libs/*.jar app.jar
COPY ./cron-job/build/libs/*.jar cron.jar

LABEL maintainer="Ellington Brambila"
LABEL version="1.0"

EXPOSE 8080 8081

CMD ["java $JAVA_OPTS -jar app.jar"]
