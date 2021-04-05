# our base build image
FROM maven:3.6.3-jdk-11 as maven
WORKDIR /app
RUN chmod +x /app
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.8.0/wait /wait
RUN chmod +x /wait
CMD /wait && mvn clean spring-boot:run