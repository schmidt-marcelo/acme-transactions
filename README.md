# Acme transactions

Sample app to me to get back to java development, after more than 7 years away. This app provides an api to store simple transactions, like

````
POST http://host:port/transactions
{ 
    "date": "2025=06-05",
    "description": "This is a purchase transaction."
    "totalAmount": 100.0
}
````
Only purchase transactions are allowed, therefore no negative value can be provided. Once the transaction is sucessfuly stored, the folowing response is provided:
````
{
    "id": "cc7d937f-e8dc-4c81-aed6-0dcfa32b69d2",
    "description": "This is a very expensive purchase",
    "date": "2025-06-04",
    "totalAmount": 54933.3
}
````

There is the possibility to convert the transaction to any currency supported by USA Treasury Fiscal Data. To convert the transaction, just call:
````
GET http://host:port/transactions/{transactionId}/converted?currency={currencyName}&country={countryName}
ex:
GET http://localhost:8080/transactions/cc7d937f-e8dc-4c81-aed6-0dcfa32b69d2/converted?currency=Cfa Franc&country=Burkina Faso
````
And the response will be:
````
{
    "id": "cc7d937f-e8dc-4c81-aed6-0dcfa32b69d2",
    "description": "This is a another purchase very expensive",
    "date": "2025-06-04",
    "totalAmount": 54933.3,
    "currencyId": "Cfa Franc",
    "currencyCountry": "Burkina Faso",
    "currencyDescription": "Burkina Faso-Cfa Franc",
    "rate": 604.5,
    "convertedAmount": 3.320717985E7
}
`````

There is a lot of room for improvement, but it's a start. 


## Setting up

The app uses gradle as a build tool, and docker environments to run. 

* Build the app: ```./gradlew build```
* Build docker image: ```./gradlew buildImage```
* Start app with postgres: ```./gradlew start```
* Stop app and database: ```./gradlew stop```

