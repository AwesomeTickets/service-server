# ServiceServer [![Build Status](https://travis-ci.org/AwesomeTickets/ServiceServer.svg?branch=master)](https://travis-ci.org/AwesomeTickets/ServiceServer)

Server that provides RESTful services of AwesomeTickets system. See the [APIs](https://github.com/AwesomeTickets/Dashboard/blob/master/doc/api.md) available.

## Installation

1. Download [Maven](http://maven.apache.org/download.cgi).

2. Run server:

    ```python
    # Foreground
    python run.py

    # Background
    python run.py -b
    ```

3. The application should be available at http://localhost:8080

4. Run unit tests:

    ```python
    python run.py -t
    ```

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.