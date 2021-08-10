# brewery-backend

![example workflow](https://github.com/github/docs/actions/workflows/main.yml/badge.svg)

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
The first time this command is run, the maven container fetches all the JAR dependencies and stores them in the volume. Unless, the volumes is removed explicitly, the subsequent builds will be faster.


Run app (development mode) in docker using:
```
make run
```
Development mode:
    1. Set JVM option to profile the application using VisualVM
    2. Builds docker-image at run time
    3. Doesn't persist data in localstack's Secret Manager

Alternatively, you can run app using:

The build and running steps can be combined with
```
make install run
```

Run app (production mode) in docker using:
```
export VERSION=<PUT-IMAGE-VERSION-HERE>
# Unpack will load the docker image file into the docker system.
# Start will spin up a container from the image.
make unpack start
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
    # A single command to build, pack, upload the files to AppServer and then unpack, and start it up.
    make setup
    ```
    Note: This step has prerequisites:
        a. The value set for `APP_DIR` in your .env should refer to an "existing" directory, on the remote server, to which the remote user (used in `USERNAME`) should have read-write permissions to.
            ```
            ssh $USERNAME@HOST
            sudo mkdir $APP_DIR
            sudo chown $USERNAME:$USERNAME $APP_DIR
            ```
        b. The authentication (private) key should already be configured in your local system. It will be used by `rsync` to authenticate with the remote server for uploading the files
            ```
            ssh-add /path/to/private/key
            ```

3. Start the containers in the server
    ```
    make deploy
    ```