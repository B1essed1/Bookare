FROM amazoncorretto:17
ADD target/*.jar Bookare-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" , "-jar", "/Bookare-0.0.1-SNAPSHOT.jar"]
EXPOSE 8899
