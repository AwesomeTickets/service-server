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
INSERT INTO movie_style (style)   VALUES ('剧情'), ('动作'), ('冒险'), ('爱情'), ('奇幻'), ('歌舞'), ('悬疑'), ('恐怖');

INSERT INTO movie
(countryID, movieStatusID, movieTypeID, pubdate, title, rating, length, posterSmall, posterLarge)
VALUES
(1, 1, 3, '2017-03-17', "美女与野兽", 8.5, 130, "http://img5.mtime.cn/mg/2017/01/25/112238.41908889_270X405X4.jpg", "http://img.inmywordz.com/uploads/20170323152259_9.jpg"),
(2, 2, 1, '2017-03-31', "绑架者", DEFAULT, 95, "http://img5.mtime.cn/mg/2017/03/13/101155.93555664_270X405X4.jpg", "http://img.tvmao.com/focuspic/movie/191435/cate_focus_pic.jpg");

INSERT INTO movie_has_style (movieID, movieStyleID)
VALUES (1, 4), (1, 5), (1, 6), (2, 1), (2, 2), (2, 3);
