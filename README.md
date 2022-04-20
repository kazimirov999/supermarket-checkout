# **Supermarket-checkout**

## Description

Application provides checkout functionality.

## Build & Run

### Build application

`mvn clean install`

### Run application

`java -jar supermarket-checkout-1.0-SNAPSHOT.jar`

## Technical details

- [Repository](https://github.com/kazimirov999/supermarket-checkout)

## Technologies

`Java 17, Junit5, Mockito, Maven`

## Having got extended requirements. Possible improvements

- Use framework to get Dependency injection, integration and configuration OOTB. (Spring)
- Add extra validations for Price Rule to prevent its creation with illegal state (negative price or unit size etc.)
- Add persistence to keep price rules
- Add API to do checkout and manage price rules(REST API, JMS etc.)
- Implement CI/CD, add SonarQube static analyzing

## NOTE!

Checkout class is not thread safe in this release