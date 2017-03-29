-- Initialize tickets data to MySQL database

-- Database
CREATE DATABASE IF NOT EXISTS tickets;
use tickets;


-- Tables
CREATE TABLE IF NOT EXISTS country (

    countryID INT         NOT NULL AUTO_INCREMENT,
    name      VARCHAR(32) NOT NULL UNIQUE,

    PRIMARY KEY (countryID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie_status (

    movieStatusID INT         NOT NULL AUTO_INCREMENT,
    status        VARCHAR(16) NOT NULL UNIQUE,

    PRIMARY KEY (movieStatusID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie_style (

    movieStyleID INT         NOT NULL AUTO_INCREMENT,
    style        VARCHAR(16) NOT NULL UNIQUE,

    PRIMARY KEY (movieStyleID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie_type (

    movieTypeID INT        NOT NULL AUTO_INCREMENT,
    type        VARCHAR(8) NOT NULL UNIQUE,

    PRIMARY KEY (movieTypeID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie (

    movieID       INT NOT NULL AUTO_INCREMENT,
    countryID     INT NOT NULL,
    movieStatusID INT NOT NULL,
    movieTypeID   INT NOT NULL,
    pubdate       DATE NOT NULL,
    title         VARCHAR(64) NOT NULL,
    rating        FLOAT(2,1) NOT NULL DEFAULT 0.0,
    length        INT NOT NULL,
    posterSmall   VARCHAR(128),
    posterLarge   VARCHAR(128),

    PRIMARY KEY (movieID),
    FOREIGN KEY (countryID)     REFERENCES country(countryID),
    FOREIGN KEY (movieStatusID) REFERENCES movie_status(movieStatusID),
    FOREIGN KEY (movieTypeID)   REFERENCES movie_type(movieTypeID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie_has_style (

    movieID       INT NOT NULL,
    movieStyleID  INT NOT NULL,

    PRIMARY KEY (movieID, movieStyleID),
    FOREIGN KEY (movieID)     REFERENCES movie(movieID),
    FOREIGN KEY (movieStyleID) REFERENCES movie_style(movieStyleID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


-- Data
INSERT INTO country (name)        VALUES ('美国'), ('中国');
INSERT INTO movie_status (status) VALUES ('on'), ('soon');
INSERT INTO movie_type (type)     VALUES ('2D'), ('3D'), ('3D IMAX');
INSERT INTO movie_style (style)   VALUES ('剧情'), ('动作'), ('冒险'), ('爱情'), ('奇幻'), ('歌舞'), ('悬疑'), ('恐怖'), ('犯罪'), ('科幻');

INSERT INTO movie
(countryID, movieStatusID, movieTypeID, pubdate, title, rating, length, posterSmall, posterLarge)
VALUES
(1, 1, 2, '2017-03-17', "美女与野兽", 8.5, 130, "http://120.25.76.106:8080/images/s_beauty_and_the_beast.jpg", "http://120.25.76.106:8080/images/l_beauty_and_the_beast.png"),
(1, 2, 3, '2017-04-14', "速度与激情8", DEFAULT, 136, "http://120.25.76.106:8080/images/s_furious8.jpg", "http://120.25.76.106:8080/images/l_furious8.png"),
(1, 2, 3, '2017-04-07', "攻壳机动队", DEFAULT, 107, "http://120.25.76.106:8080/images/s_ghost_in_the_shell.jpg", "http://120.25.76.106:8080/images/l_ghost_in_the_shell.png");

INSERT INTO movie_has_style (movieID, movieStyleID)
VALUES (1, 4), (1, 5), (1, 6), (2, 2), (2, 9), (3, 2), (3, 9), (3, 10);
