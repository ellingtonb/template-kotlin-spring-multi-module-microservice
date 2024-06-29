FROM azul/zulu-openjdk:17-latest

COPY ./modules/webservice/build/libs/*-bootJar.jar app.jar

LABEL maintainer="Ellington Brambila"
LABEL version="1.0"

EXPOSE 8080 8081

CMD ["java", "-jar", "app.jar"]
