FROM openjdk:11
EXPOSE 8089
WORKDIR /app
ADD target/kaddem-2.jar  kaddem-2.jar
ENTRYPOINT ["java", "-jar", "kaddem-2.jar"]