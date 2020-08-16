#!/bin/sh
echo "********************************************************"
echo "Waiting for the RABBITMQ server to start on port $RABBITMQ_PORT"
echo "********************************************************"
while ! `nc -z rabbitmq $RABBITMQ_PORT`; do sleep 3; done
echo "*******  RABBITMQ Server has started"

echo "********************************************************"
echo "Waiting for the mongo server to start on port $MONGO_PORT"
echo "********************************************************"
while ! `nc -z mongo $MONGO_PORT`; do sleep 3; done
echo "******* mongo has started"

echo "********************************************************"
echo "Waiting for the redis server to start on port $REDIS_PORT"
echo "********************************************************"
while ! `nc -z redis $REDIS_PORT`; do sleep 3; done
echo "*******  Redis Server has started"


echo "********************************************************"
echo "Waiting for the Customer server to start on port $CUSTOMER_PORT"
echo "********************************************************"
while ! `nc -z customerservice $CUSTOMER_PORT`; do sleep 3; done
echo "*******  Customer Server has started"


echo "********************************************************"
echo "Starting Account Service                           "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Dspring.profiles.active=$PROFILE                                   \
     -jar /usr/local/accountservice/account.jar
