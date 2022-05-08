FROM openjdk:8-alpine

COPY target/uberjar/polls.jar /polls/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/polls/app.jar"]
