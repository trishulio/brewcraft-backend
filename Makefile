.PHONY: install run d_install

install:
	mvn clean install

run:
	mvnw spring-boot:run

d_install:
	docker-compose -f docker-compose-install.yml run install

d_start:
	docker-compose down &&\
	docker-compose rm &&\
	docker-compose build --no-cache &&\
	docker-compose up
