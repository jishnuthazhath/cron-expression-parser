# Cron Expression Parser Application

### The application is build and tested in the following environment
```dtd
------------------------------------------------------------
Gradle 8.6
------------------------------------------------------------

Build time:   2024-02-02 16:47:16 UTC
Revision:     d55c486870a0dc6f6278f53d21381396d0741c6e

Kotlin:       1.9.20
Groovy:       3.0.17
Ant:          Apache Ant(TM) version 1.10.13 compiled on January 4 2023
JVM:          21.0.2 (Homebrew 21.0.2)
OS:           Mac OS X 14.3 aarch64
```

## Build
You can build the code using the following command
```dtd
./gradlew clean build
```

## Test
Run the tests using the following command
```dtd
./gradlew clean test
```

## Create a runnable jar
```dtd
./gradlew jar
```

## Run the application
Create a runnable jar using the below command
```dtd
java -jar app/build/libs/app.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

## Use Docker
In case if there are errors due to the java environment, you could use docker.
```dtd
cd cron-expression-parser
```

```dtd
docker build -t cron-app .
```

```dtd
docker run -it --rm cron-app java -jar cron-expression-parser.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```
