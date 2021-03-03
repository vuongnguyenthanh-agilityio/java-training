# our base build image
FROM maven:3.6.3-jdk-11 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package -DskipTests

# our final base image
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

# set deployment directory
WORKDIR /market-service

# copy over the built artifact from the maven image
COPY --from=maven target/market-service-1.0.jar ./

# set the startup command to run your binary
CMD ["java", "-jar", "./market-service-1.0.jar"]