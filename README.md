# brewery-backend
Backend for the brewery business management app

We are using Makefiles to abstract the tasks to build, start, and test, etc. For all the dockerized processes the name starts with prefix `d_`.

Some commands have alternative native targets available, like, `make d_install` uses dockerized maven to build the project whereas `make install` uses the host machine's maven (if available on path).


Build the project using
```
make d_install
```

This command expects that there is a `PWD` variable available in your ENV that corresponds to the root directory of this project. Most UNIX based systems like Mac or Linux automatically set this variable to point to the current directory. However in case this variable is not available in your host os, then you can set this varilable in your `.env` file to point to the root directory of this project, like,

```
PWD=/c/Users/<username>/code/brewcraft/
```

_Note: The non-docker version of the build can be executed using `make install`_

Run app in docker using:
```
make d_start
```

Alternatively, you can run app using:
```
make run
```
_Note: mvnw should be available on the path_


To combine the build and start tasks, use:
```
make d_install d_start
```

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