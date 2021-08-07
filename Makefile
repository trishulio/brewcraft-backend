.PHONY: install containerize pack upload unpack deploy run start stop remove restart

HOST_APP_DIR:=/var/server/brewcraft
APP_NAME:=brewcraft
TARGET:=./dist
VERSION:=1.0.0-SNAPSHOT

install:
	docker-compose -f docker-compose-install.yml run --rm install

containerize:
	docker build . -t ${APP_NAME}:${VERSION}

pack:
	mkdir -p ${TARGET}
	rm -rf ${TARGET}/* ; true
	docker save -o ${TARGET}/${APP_NAME}_${VERSION}.image ${APP_NAME}:${VERSION}
	cp -r ./db-init-scripts ${TARGET}
	cp -r ./wait-for-it.sh ${TARGET}
	cp ./docker-compose.yml ${TARGET}
	cp ./docker-compose-prod-test.yml ${TARGET}
	cp ./Makefile ${TARGET}
	docker rmi ${APP_NAME}:${VERSION}

upload:
	ssh -i ${ID_KEY} ${USERNAME}@${HOST} "mkdir -p ${HOST_APP_DIR} && rm -r ${HOST_APP_DIR}/* ; true"
	rsync -e "ssh -i ${ID_KEY}" --progress -avz ${TARGET}/ ${USERNAME}@${HOST}:${HOST_APP_DIR}

unpack:
	# Asserting that .env file is present.
	ls .env
	docker load -i ${APP_NAME}_${VERSION}.image

deploy:
	ssh -i ${ID_KEY} ${USERNAME}@${HOST} "cd ${HOST_APP_DIR} && export VERSION=${VERSION} && make unpack && make restart"

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