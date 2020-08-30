# CQRS Example with Kotlin

## 1 - Full CQRS without Kafka
![alt cqrs-full-without-kafka](https://github.com/yvesmendes/kotlin-cqrs-implementation/blob/master/media/full-cqrs-without-kafka.png)

### 1.1 RUN
```
Just enter the full-cqrs-without-kafka folder
mvn package dockerfile:build
docker-compose -f docker-compose.yml up
```
### 1.2 Test
```
Add customer:

curl -X POST \
  http://localhost:8080/v1/customers \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Test User",
	"mail": "teste@user.com",
	"password": "123456"
}'

Add product:

curl -X POST \
  http://localhost:8082/v1/catalog/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Xbox One",
	"value": 200,
	"stock": 100
}'

Add item to cart (modify productId):

curl -X POST \
  http://localhost:8083/v1/orders/cart \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "48e4c9ee-bebf-426c-aeff-c2169e70d095",
	"quantity": 2
}'

Add balance (modify accountNumber):

curl -X PUT \
  http://localhost:8081/v1/accounts/balance \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '  {
  "amount": 2000, 
  "accountNumber" : "2f5c32af-b3b9-43b8-8cfd-5dc78d18f69b", 
  "balanceRequestType": "CREDIT"
}'

Calculate order (modify productId):

curl -X POST \
  http://localhost:8083/v1/orders/calculate \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "8c9672d2-98a0-43e0-a633-5fefec7e1c49"
}'

Confirm Order :
curl -X POST \
  http://localhost:8083/v1/orders/confirm \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' 
  
Get Profile:

curl -X GET \
  http://localhost:8080/v1/customers/1 \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' 

Get Order (modfify orderId):

curl -X GET \
  http://localhost:8083/v1/orders/18ee920a-6f68-4abd-a8e1-77f0909d3f6f \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
```
## 2 CQRS With Legacy System
![alt without-kafka](https://github.com/yvesmendes/kotlin-cqrs-implementation/blob/master/media/without-kafka.png)
### 2.1 RUN
```
Just enter the legacy-system-without-kafka folder
mvn package dockerfile:build
docker-compose -f docker-compose.yml up
```
### 2.2 Test
```
Add customer:

curl -X POST \
  http://localhost:8080/v1/customers \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Test User",
	"mail": "teste@user.com",
	"password": "123456"
}'

Add product:

curl -X POST \
  http://localhost:8082/v1/catalog/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Xbox One",
	"value": 200,
	"stock": 100
}'

Add item to cart (modify productId):

curl -X POST \
  http://localhost:8083/v1/orders/cart \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "48e4c9ee-bebf-426c-aeff-c2169e70d095",
	"quantity": 2
}'

Add balance (modify accountNumber):

curl -X PUT \
  http://localhost:8081/v1/accounts/balance \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '  {
  "amount": 2000, 
  "accountNumber" : "2f5c32af-b3b9-43b8-8cfd-5dc78d18f69b", 
  "balanceRequestType": "CREDIT"
}'

Calculate order (modify productId):

curl -X POST \
  http://localhost:8083/v1/orders/calculate \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "8c9672d2-98a0-43e0-a633-5fefec7e1c49"
}'

Confirm Order :
curl -X POST \
  http://localhost:8083/v1/orders/confirm \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' 
  
Get Profile:

curl -X GET \
  http://localhost:8080/v1/customers/1 \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' 

Get Order (modfify orderId):

curl -X GET \
  http://localhost:8083/v1/orders/18ee920a-6f68-4abd-a8e1-77f0909d3f6f \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
```
## 3 CQRS With Legacy System and Kafka Connector with Debezium
![alt with-kafka](https://github.com/yvesmendes/kotlin-cqrs-implementation/blob/master/media/with-kafka.png)

### 3.1 RUN
```
Just enter the legacy-system-with-kafka folder
mvn package dockerfile:build
docker-compose -f docker-compose.yml up
```
### 3.2 Test
```
First of all run the folowwing command to add the Debezium connector:

curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @source.json --verbose

Add customer:

curl -X POST \
  http://localhost:8080/v1/customers \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Test User",
	"mail": "teste@user.com",
	"password": "123456"
}'

Add product:

curl -X POST \
  http://localhost:8082/v1/catalog/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Xbox One",
	"value": 200,
	"stock": 100
}'

Add item to cart (modify productId):

curl -X POST \
  http://localhost:8089/v1/orders/cart \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "48e4c9ee-bebf-426c-aeff-c2169e70d095",
	"quantity": 2
}'

Add balance (modify accountNumber):

please import the following WSDL:

http://localhost:8085/ws/account.wsdl

And execute the request updateBalance:

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:acc="http://localhost/accounts/">
   <soapenv:Header/>
   <soapenv:Body>
      <acc:updateBalanceRequest>
         <acc:requestAccount>
            <acc:amount>1000</acc:amount>
            <acc:accountNumber>20bea94b-a2c6-45f7-8dc6-6716d8f0acff</acc:accountNumber>
            <acc:customerId>1</acc:customerId>
            <acc:balanceRequestType>credit</acc:balanceRequestType>
         </acc:requestAccount>
      </acc:updateBalanceRequest>
   </soapenv:Body>
</soapenv:Envelope>

Calculate order (modify productId):

curl -X POST \
  http://localhost:8089/v1/orders/calculate \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' \
  -d '{
	"productId": "8c9672d2-98a0-43e0-a633-5fefec7e1c49"
}'

Confirm Order :
curl -X POST \
  http://localhost:8089/v1/orders/confirm \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -H 'customerId: 1' 
  
Get Profile:

curl -X GET \
  http://localhost:8080/v1/customers/1 \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' 

Get Order (modfify orderId):

curl -X GET \
  http://localhost:8089/v1/orders/18ee920a-6f68-4abd-a8e1-77f0909d3f6f \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
```

