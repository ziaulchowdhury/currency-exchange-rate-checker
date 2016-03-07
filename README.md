# Currency Exchange Rate Checker
Currency Exchange Rate Checker is a simple system which allows new users to make registration. After successful registration, user can log in to the system and check currency exchange rates.

It offers two types of exchange rate query:
  1. Latest exchange rates
  2. Historical exchange rates
    1. Historical exchange rate is based on the date

## Source of Data 
Currency exchange rates are retrieved from [openexchangerates](https://openexchangerates.org).

# Technology Stack used in the Project
1. Spring Boot
2. Spring Data
3. JPA
4. H2 database
5. Spring Security
6. Hibernate validator
7. Themeleaf Template
8. Spring MVC
9. Gradle
10. Git
11. Java 8

# Checking out and Building
Currency Exchange Rate Checker is a Gradle based Spring Boot application. It uses Git version control system.

Development system must have Jdk 8 & Git installed.

In order to check out the project and build it locally, please follow the steps below:

    git clone https://github.com/ziaulchowdhury/currency-exchange-rate-checker.git
    cd currency-exchange-rate-checker
    ./gradlew build
  
The following command will run the Spring Boot application:

    ./gradlew run

# Configuration
In order to use API of [openexchangerates](https://openexchangerates.org), API key is required. 

There are two types of licenses for using the API:
  1. Developer account (limited API use, 1000 API calls per month)
  2. Commercial account

Once API key is available, it can be configured in [config.properties file](https://github.com/ziaulchowdhury/currency-exchange-rate-checker/blob/master/src/main/resources/config.properties). 

Please set API key in property "openexchange.api.key".
