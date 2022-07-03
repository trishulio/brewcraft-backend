FROM openjdk:17 AS prod

ARG JAR_FILE=./target/brewcraft-backend-1.0.0.jar
ARG USER_ID=johndoe
ARG UUID=8877

# cd /opt/app
WORKDIR /opt/app

# cp target/brewcraft-1.0.0.jar /opt/app/brewcraftapp.jar
COPY ${JAR_FILE} brewcraftapp.jar

RUN useradd -ms /bin/bash -u ${UUID} ${USER_ID} && \
    chmod 0500 brewcraftapp.jar && \
    chown ${USER_ID} brewcraftapp.jar

USER ${USER_ID}

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx1g", "-jar", "brewcraftapp.jar"]