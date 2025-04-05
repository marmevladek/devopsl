FROM openjdk:21

WORKDIR /

RUN mkdir -p $HOME/maven3 && cd $HOME/maven3
FROM maven:3.9.8 AS maven
ENV M2_HOME=$HOME/maven3

COPY  . /
RUN mvn clean formatter:format install
RUN mv ./target/backend-service-1.0-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 1000