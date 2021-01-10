.PHONY: source install dist run export upload start setup_prod

HOST_APP_DIR := /var/brewcraft
APP_NAME := brewcraft
TARGET := ./dist

source:
	sed -E 's/^(\w)/export \1/' .env | sed 's/\r//' > source.sh

install:
	docker-compose -f docker-compose-install.yml run --rm install

dist:
	docker rmi ${APP_NAME}:${VERSION}; true
	docker build . -t ${APP_NAME}:${VERSION}

run:
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml rm &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml up

export:
	rm ${TARGET}/${APP_NAME}:${VERSION}; true
	mkdir -p dist
	docker save -o ${TARGET}/${APP_NAME}_${VERSION}.image ${APP_NAME}:${VERSION}

upload:
	cp -r ./db-init-scripts ${TARGET}
	cp ./docker-compose.yml ${TARGET}
	cp ./docker-compose-prod-test.yml ${TARGET}
	cp ./Makefile ${TARGET}
	cp .env ${TARGET}
	ssh ${USERNAME}@${HOST} "mkdir -p ${HOST_APP_DIR}"
	rsync -a ${TARGET}/.??* ${USERNAME}@${HOST}:${HOST_APP_DIR}

clean:
	rm -rf {TARGET}

start:
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml up -d

stop:
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml rm &&\

reload:
	docker rmi ${APP_NAME}:${VERSION}; true &&\
	docker load -i ${APP_NAME}_${VERSION}.image &&\

deploy:
	ssh ${USERNAME}@${HOST} "cd ${HOST_APP_DIR} && make restart"

restart: stop reload start

setup_prod: install dist export upload deploy
