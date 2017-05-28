# ServiceServer [![Build Status](https://travis-ci.org/AwesomeTickets/ServiceServer.svg?branch=master)](https://travis-ci.org/AwesomeTickets/ServiceServer)

Server that provides RESTful services of AwesomeTickets system. See the [APIs](https://github.com/AwesomeTickets/Dashboard/blob/master/doc/api.md) available.

## Installation

- Requirements

    - Maven 3.5.0

- Run locally

    ```bash
    $ mvn clean tomcat7:run
    ```

    **Notes:** Use `-Djava.security.egd=file:/dev/./urandom` if server starts slowly. ([details](http://nobodyiam.com/2016/06/07/tomcat-startup-slow/))

- Run unit tests

    ```bash
    $ mvn clean test
    ```

- Build with docker

    ```bash
    $ docker build -t service-server .
    $ docker run --name service-server -p 8000:8000 -d service-server
    ```

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.
