FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/accountLegacyservice
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/accountLegacyservice/accountLegacy.jar
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
