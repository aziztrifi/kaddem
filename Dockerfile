FROM openjdk:11
EXPOSE 8089
WORKDIR /app
ADD target/kaddem-10.jar  kaddem-10.jar
ENTRYPOINT ["java", "-jar", "kaddem-10.jar"]