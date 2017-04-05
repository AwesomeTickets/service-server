# Tickets-Server [![Build Status](https://travis-ci.org/AwesomeTickets/Tickets-Server.svg?branch=master)](https://travis-ci.org/AwesomeTickets/Tickets-Server)

Server of a movie tickets system powered by Spring.

[Dashboard](https://github.com/AwesomeTickets/Dashboard)

## Installation

### Database

1. Download [MySQL](https://dev.mysql.com/downloads/mysql/).

2. User settings:

    ```
    username: root
    password: 123456
    ```

3. Initialize database:

    ```sh
    $ python initDB.py
    ```

### Server

1. Download [Maven](http://maven.apache.org/download.cgi).

2. Build and run:

    - Foreground

        ```sh
        $ python server.py
        ```

    - Background

        ```sh
        $ python deploy.py
        ```

3. The application should be available at http://localhost:8080

4. Run unit tests:

    ```sh
    $ mvn test
    ```

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.