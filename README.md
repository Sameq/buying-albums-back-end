# BootCamp Sysmap 2024
## Albums vending system

## Description:
This project is a system for selling music albums, developed using Java with the Spring Boot framework. The application allows users to view and purchase albums available on the platform, as well as manage their virtual wallets.

## Prerequisites:
- Java Development Kit (JDK) 17 or higher
- Maven
- Postgres Server
- Spring Boot
- Spring Security
- Account at Sportify
- Docker
- Rabbitmq
## Features:
- View albums available for sale.
- Purchase albums using virtual wallet balance.
- Management of users' virtual wallet.
- Authentication and authorization of users using Spring Security.
# Installation & Configuration
Clone this repository to your local environment.
```bash
  git clone https://github.com/bc-fullstack-04/sameque-ananias-backend
```
- Make sure you have PostgreSQL Server installed and running on your machine.
- Import the project into your preferred IDE (such as IntelliJ, IDEA, or Eclipse).
- Make sure you have Docker installed on your machine.
- Open the application.yml file in the src/main/resources folder and configure the Postgres database access information.
## API Documentation:
- API documentation is available using Swagger. After launching the project locally, go to http://localhost:8080/swagger-ui.html (API-USER) end http://localhost:8082/swagger-ui.html (APP-INTEGRATION) to view the documentation and interact with the API endpoints.
## How to execute the project:
- Build the project using Maven: mvn clean install.
- Run the Spring Boot project: mvn spring-boot:run.
## License
- This project is licensed under the MIT License.
