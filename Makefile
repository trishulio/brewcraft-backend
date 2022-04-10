.PHONY: install containerize pack upload unpack deploy run start stop remove restart

TARGET_UPLOAD_DIR:=/var/server/brewcraft
SOURCE_UPLOAD_DIR:=./dist
APP_NAME:=brewcraft
VERSION:=1.0.0-SNAPSHOT

install:
    docker-compose -f docker-compose-install.yml run --rm install

containerize:
    docker build . -t ${APP_NAME}:${VERSION}

pack:
    mkdir -p ${SOURCE_UPLOAD_DIR}
    rm -rf ${SOURCE_UPLOAD_DIR}/* ; true
    docker save -o ${SOURCE_UPLOAD_DIR}/${APP_NAME}_${VERSION}.image ${APP_NAME}:${VERSION}
    cp -r ./db-init-scripts ${SOURCE_UPLOAD_DIR}
    cp -r ./wait-for-it.sh ${SOURCE_UPLOAD_DIR}
    cp ./docker-compose.yml ${SOURCE_UPLOAD_DIR}
    cp ./docker-compose-prod-test.yml ${SOURCE_UPLOAD_DIR}
    cp ./Makefile ${SOURCE_UPLOAD_DIR}

upload:
    ssh -i '${ID_KEY}' ${USERNAME}@${HOST} "mkdir -p ${TARGET_UPLOAD_DIR} && rm -r ${TARGET_UPLOAD_DIR}/* ; true"
    rsync -e "ssh -i '${ID_KEY}'" --progress -avz ${SOURCE_UPLOAD_DIR}/ ${USERNAME}@${HOST}:${TARGET_UPLOAD_DIR}

unpack:
    # Asserting that .env file is present.
    ls .env
    docker load -i ${APP_NAME}_${VERSION}.image

deploy:
    ssh -i '${ID_KEY}' ${USERNAME}@${HOST} "cd ${TARGET_UPLOAD_DIR} && make unpack restart VERSION=${VERSION}"

run:
    docker-compose -f docker-compose.yml -f docker-compose-dev.yml down &&\
    docker-compose -f docker-compose.yml -f docker-compose-dev.yml rm &&\
    docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
    docker-compose -f docker-compose.yml -f docker-compose-dev.yml up

start:
    docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml up -d

stop:
    docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml down

remove:
    docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml rm

restart: stop remove start

setup: containerize pack upload deploy