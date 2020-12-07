.PHONY: install run d_install

install:
	docker-compose -f docker-compose-install.yml run --rm install

dist:
	source .env
	docker rmi brewcraft:${VERSION}; true
	docker build . -t brewcraft:${VERSION}

run:
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml rm &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml up