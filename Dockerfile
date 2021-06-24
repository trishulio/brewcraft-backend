FROM openjdk:11 AS prod

ARG JAR_FILE=./target/brewcraft-1.0.0.jar
ARG WAIT_FOR_IT_FILE=./wait-for-it.sh

# cd /opt/app
WORKDIR /opt/app

# cp target/brewcraft-1.0.0.jar /opt/app/brewcraftapp.jar
COPY ${JAR_FILE} brewcraftapp.jar

# cp wait-for-it.sh /opt/app/wait-for-it.sh
COPY ${WAIT_FOR_IT_FILE} wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT [ "/bin/sh", "-c" ]
CMD ["java -jar brewcraftapp.jar"]

# Stage 2 - Dev - Adds dev tools compatibility
FROM prod AS dev
CMD ["./wait-for-it.sh --strict --timeout=20 localstack:4566 && java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar brewcraftapp.jar"]
EXPOSE 9010