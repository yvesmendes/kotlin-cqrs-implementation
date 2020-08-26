FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/orderservice
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/orderservice/order.jar
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
