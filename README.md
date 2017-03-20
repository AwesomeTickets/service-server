# Tickets-Server [![Build Status](https://travis-ci.org/stevennl/Tickets-Server.svg?branch=master)](https://travis-ci.org/stevennl/Tickets-Server)

Server of a movie tickets system powered by Spring & Thymeleaf.

## Installation

1. Download [Maven](http://maven.apache.org/download.cgi).

2. Build and run the server using commands below:

    ```bash
    $ mvn clean
    $ mvn compile
    $ mvn tomcat7:run
    ```

    or

    ```bash
    $ python server.py
    ```

3. The application should be available at `http://localhost:8080/tickets`

4. Run unit tests:

    ```bash
    $ mvn test
    ```
