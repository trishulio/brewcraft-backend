# brewery-backend
Backend for the brewery business management app

Run app using:
./mvnw spring-boot:run (MacOS/Linux:)
mvnw spring-boot:run (Windows)

Run app in docker using:
docker-compose build --no-cache && docker-compose up

Note: When creating postgres server in pgadmin, use the name of the postgres container(postgresdb) as the host

# How to setup a development server

## Prerequisite

1. Install docker and docker-compose.
```
sudo apt update -y
sudo apt install docker docker-compose -y
sudo usermod -aG docker $USER
reboot # For permissions to take effect

# Validate docker is running
docker run --rm hello-world
```

## Setting app

1. Configure the values in your .env files.

2. Build a docker image and upload it to the server
    ```
    make source
    source ./source.sh
    make setup_prod
    ```

3. Start the containers in the server
    ```
    # The username and hostname values should be configured in your env from previous step.
    ssh $USERNAME@$HOST
    ```

4. Inside the SSH'd session
    ``` 
    cd <project_root>
    make source
    source ./source.sh
    # nohup and & is needed if you want to close SSH session without stopping the containers
    nohup make start &
    ``` 