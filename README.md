# Currency Converter Application

This application provides currency conversion functionalities using an external forex service.

## Usage

To use this application, follow these steps:

1. **Build the Application:** Navigate to the project directory and build the application using Maven:
mvn clean install

2. **Run the Application:** After building the application, you can run it using the following Maven command:
mvn spring-boot:run

3. **Accessing Endpoints:**
- To retrieve currency data from the external service, make a GET request to the following endpoint:
  ```
  GET http://localhost:8080/forex/currencies
  ```
- To convert currency, make a PUT request to the following endpoint with the conversion details in the request body:
  ```
  PUT http://localhost:8080/forex/convert
  ```


## Technologies Used

1. Java
2. Spring Boot
3. Maven
4. Mockito (for testing)

## Endpoints

### Retrieve Currency Data
- **URL:** `/forex/currencies`
- **Method:** GET
- **Description:** Retrieves currency data from an external forex service.

### Convert Currency
- **URL:** `/forex/convert`
- **Method:** PUT
- **Description:** Converts currency based on the provided conversion request.

## Example

### Retrieve Currency Data
GET http://localhost:8080/forex/currencies

PUT http://localhost:8080/forex/convert

**Request Body:**
```json
{
  "amount": 100,
  "base": "EUR",
  "target": "INR"
}

Response: 
8361.8328

