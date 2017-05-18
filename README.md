# Tickets-Server [![Build Status](https://travis-ci.org/AwesomeTickets/Tickets-Server.svg?branch=master)](https://travis-ci.org/AwesomeTickets/Tickets-Server)

Server of AwesomeTickets system.

## Installation

1. Download [Maven](http://maven.apache.org/download.cgi).

2. Build and run:

    - foreground

        ```sh
        $ python server.py
        ```

    - background

        ```sh
        $ python deploy.py
        ```

3. The application should be available at http://localhost:8080

4. Run unit tests:

    ```sh
    $ mvn clean test
    ```

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.