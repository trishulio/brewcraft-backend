FROM openjdk:17 AS prod

ARG JAR_FILE=./target/brewcraft-1.0.0.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/brewcraft-1.0.0.jar /opt/app/brewcraftapp.jar
COPY ${JAR_FILE} brewcraftapp.jar

RUN useradd -ms /bin/bash app && \
    chmod 0050 brewcraftapp.jar && \
    chgrp -R app brewcraftapp.jar

USER app
ENTRYPOINT ["java", "-jar", "brewcraftapp.jar"]