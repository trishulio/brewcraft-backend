# brewery-backend
Backend for the brewery business management app

We are using Makefiles to abstract the tasks to build, start, and test, etc.


Build the project using
```
make install
```

This command expects that there is a `PWD` variable available in your ENV that corresponds to the root directory of this project. Most UNIX based systems like Mac or Linux automatically set this variable to point to the current directory. However in case this variable is not available in your host os, then you can set this varilable in your `.env` file to point to the root directory of this project, like,

```
PWD=/c/Users/<username>/code/brewcraft/
```



Run app (development mode) in docker using:
```
make run
```
Development mode:
    1. Set JVM option to profile the application using VisualVM
    2. Builds docker-image at run time
    3. Doesn't persist data in localstack's Secret Manager

Alternatively, you can run app using:


Run app (production mode) in docker using:
```
make start
```
Prod-test mode loads an existing application image and creates a container from it.


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