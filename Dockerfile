FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=./target/brewcraft-1.0.0.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/brewcraft-1.0.0.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]