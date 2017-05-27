# ServiceServer [![Build Status](https://travis-ci.org/AwesomeTickets/ServiceServer.svg?branch=master)](https://travis-ci.org/AwesomeTickets/ServiceServer)

Server that provides RESTful services of AwesomeTickets system. See the [APIs](https://github.com/AwesomeTickets/Dashboard/blob/master/doc/api.md) available.

## Installation

- Requirements

    - Maven 3.5.0

- Run locally

    ```bash
    $ mvn clean tomcat7:run
    ```

- Run unit tests

    ```bash
    $ mvn clean test
    ```

**Notes:** Use `-Djava.security.egd=file:/dev/./urandom` if server starts slowly. ([details](http://nobodyiam.com/2016/06/07/tomcat-startup-slow/))

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.