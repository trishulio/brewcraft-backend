FROM openjdk:11

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
CMD ["./wait-for-it.sh --strict --timeout=20 localstack:4566 && java -jar brewcraftapp.jar"]
