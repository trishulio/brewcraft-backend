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
