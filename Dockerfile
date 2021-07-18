FROM openjdk:11 AS prod

ARG JAR_FILE=./target/brewcraft-1.0.0.jar
ARG WAIT_FOR_IT_FILE=./wait-for-it.sh

# cd /opt/app
WORKDIR /opt/app

# cp target/brewcraft-1.0.0.jar /opt/app/brewcraftapp.jar
COPY ${JAR_FILE} brewcraftapp.jar

# cp wait-for-it.sh /opt/app/wait-for-it.sh
COPY ${WAIT_FOR_IT_FILE} wait-for-it.sh

RUN useradd -D app && \
    chmod 0050 . && \
    chgrp -R app .

USER app
ENTRYPOINT [ "/bin/sh", "-c" ]
CMD ["java -jar brewcraftapp.jar"]
