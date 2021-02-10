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

run: stop
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml up

export:
	rm ${TARGET}/${APP_NAME}:${VERSION}; true
	mkdir -p dist
	docker save -o ${TARGET}/${APP_NAME}_${VERSION}.image ${APP_NAME}:${VERSION}

pack:
	cp -r ./db-init-scripts ${TARGET}
	cp ./docker-compose.yml ${TARGET}
	cp ./docker-compose-prod-test.yml ${TARGET}
	cp ./Makefile ${TARGET}
	cp .env ${TARGET}

clean:
	rm -rf {TARGET}

upload:
	ssh ${USERNAME}@${HOST} "mkdir -p ${HOST_APP_DIR}"
	rsync -a ${TARGET}/.??* ${USERNAME}@${HOST}:${HOST_APP_DIR}

start:
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml up -d

stop:
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml rm

reload:
	docker rmi ${APP_NAME}:${VERSION}; true &&\
	docker load -i ${APP_NAME}_${VERSION}.image &&\

restart: stop reload start

deploy:
	ssh ${USERNAME}@${HOST} "cd ${HOST_APP_DIR} && make restart"

setup: clean install dist export pack upload
