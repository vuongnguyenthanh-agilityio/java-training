# our base build image
FROM maven:3.6.3-jdk-11 as maven
WORKDIR /app
RUN chmod +x /app
CMD mvn clean spring-boot:run