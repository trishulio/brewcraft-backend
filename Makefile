.PHONY: install containerize_local containerize pack upload unpack deploy run start stop remove restart

TARGET_UPLOAD_DIR:=/var/server/brewcraft
SOURCE_UPLOAD_DIR:=./dist
APP_NAME:=brewcraft-backend
VERSION:=1.0.0-SNAPSHOT
AWS_PROFILE:=jadc-development
AWS_ACCOUNT_ID:=346608161962
AWS_REGION:=ca-central-1
REGISTRY=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
VALUES_FILE:=values-development.yml
NAMESPACE:=local

install:
	docker-compose -f docker-compose-install.yml run --rm install

containerize_local:
	minikube image build -t ${APP_NAME}:${VERSION} .

deploy:
	(cd brewcraft-chart && helm install -f values.yaml -f ${VALUES_FILE} -n ${NAMESPACE} brewcraft-backend .)

undeploy:
	(cd brewcraft-chart && helm uninstall -n ${NAMESPACE} brewcraft-backend)

upgrade:
	(cd brewcraft-chart && helm upgrade -f values.yaml -f ${VALUES_FILE} -n ${NAMESPACE} brewcraft-backend .)

pack:
	mkdir -p ${SOURCE_UPLOAD_DIR}
	rm -rf ${SOURCE_UPLOAD_DIR}/* ; true
	docker save -o ${SOURCE_UPLOAD_DIR}/${REGISTRY}_${APP_NAME}_${VERSION}.image ${REGISTRY}/${APP_NAME}:${VERSION}
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
	docker load -i ${REGISTRY}_${APP_NAME}_${VERSION}.image

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

## Release

containerize:
	docker build . -t ${REGISTRY}/${APP_NAME}:${VERSION}

publish: set_repo_credentials
	docker push ${REGISTRY}/${APP_NAME}:${VERSION}

set_credentials:
	CREDS_JSON=$(aws sts assume-role --role-arn arn:aws:iam::${AWS_ACCOUNT_ID}:role/SystemAdministrator --role-session-name AWSCliSession --profile ${AWS_ACCOUNT_ID});\
	export AWS_ACCESS_KEY_ID=$(echo $CREDS_JSON | jq -r '.Credentials''.AccessKeyId');\
	export AWS_SECRET_ACCESS_KEY=$(echo $CREDS_JSON | jq -r '.Credentials''.SecretAccessKey');\
	export AWS_SESSION_TOKEN=$(echo $CREDS_JSON | jq -r '.Credentials''.SessionToken');

login_repo: set_credentials
	aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/brewcraft-backend
