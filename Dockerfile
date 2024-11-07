FROM openjdk:11
EXPOSE 8089
WORKDIR /app
ADD target/kaddem-11.jar kaddem-11.jar
ENTRYPOINT ["java", "-jar", "kaddem-11.jar"]
