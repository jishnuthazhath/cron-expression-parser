FROM openjdk:11
WORKDIR /app
COPY app/build/libs/app.jar ./cron-expression-parser.jar
CMD ["java", "-jar", "cron-expression-parser.jar", "args"]
