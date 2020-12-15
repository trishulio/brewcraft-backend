.PHONY: source install dist run export upload start setup_prod

source:
	sed -E 's/^(\w)/export \1/' .env | sed 's/\r//' > source.sh

install:
	docker-compose -f docker-compose-install.yml run --rm install

dist:
	# source .env
	docker rmi brewcraft:${VERSION}; true
	docker build . -t brewcraft:${VERSION}

run:
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml rm &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml up

export:
	rm ./dist/brewcraft:${VERSION}; true
	mkdir -p dist
	docker save -o ./dist/brewcraft_${VERSION}.image brewcraft:${VERSION}

upload:
	cp -r ./db-init-scripts ./dist
	cp ./docker-compose.yml ./dist
	cp ./docker-compose-prod-test.yml ./dist
	cp ./Makefile ./dist
	cp .env ./dist
	rsync -a ./dist ${USERNAME}@${HOST}:${APP_DIR}

start:
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml rm &&\
	docker rmi brewcraft:${VERSION}; true &&\
	docker load -i brewcraft_${VERSION}.image &&\
	docker-compose -f docker-compose.yml -f docker-compose-prod-test.yml up -d

setup_prod: install dist export upload
