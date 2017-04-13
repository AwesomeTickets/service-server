-- Initialize tickets data to MySQL database

-- Drop database
DROP DATABASE IF EXISTS tickets;

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

    movieID      INT NOT NULL,
    movieStyleID INT NOT NULL,

    PRIMARY KEY (movieID, movieStyleID),
    FOREIGN KEY (movieID)      REFERENCES movie(movieID),
    FOREIGN KEY (movieStyleID) REFERENCES movie_style(movieStyleID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS cinema (

    cinemaID INT NOT NULL AUTO_INCREMENT,
    name     VARCHAR(128) NOT NULL UNIQUE,
    location VARCHAR(256) NOT NULL UNIQUE,

    PRIMARY KEY (cinemaID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS cinema_hall (

    cinemaHallID INT NOT NULL AUTO_INCREMENT,
    cinemaID     INT NOT NULL,
    name         VARCHAR(16) NOT NULL,
    seatLayout   VARCHAR(512) NOT NULL,

    PRIMARY KEY (cinemaHallID),
    FOREIGN KEY (cinemaID) REFERENCES cinema(cinemaID)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS movie_on_show (

    movieOnShowID INT NOT NULL AUTO_INCREMENT,
    movieID       INT NOT NULL,
    cinemaHallID  INT NOT NULL,
    lang          VARCHAR(16) NOT NULL,
    showDate      DATE NOT NULL,
    showTime      TIME NOT NULL,
    price         DECIMAL(5, 2) NOT NULL,

    PRIMARY KEY (movieOnShowID),
    FOREIGN KEY (movieID)      REFERENCES movie(movieID),
    FOREIGN KEY (cinemaHallID) REFERENCES cinema_hall(cinemaHallID),

    UNIQUE (movieID, cinemaHallID, showDate, showTime)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS seat (

    seatID        INT NOT NULL AUTO_INCREMENT,
    movieOnShowID INT NOT NULL,
    row           INT NOT NULL,
    col           INT NOT NULL,
    available     BOOLEAN NOT NULL DEFAULT TRUE,

    PRIMARY KEY (seatID),
    FOREIGN KEY (movieOnShowID) REFERENCES movie_on_show(movieOnShowID),

    UNIQUE (movieOnShowID, row, col)

) ENGINE = InnoDB, DEFAULT CHARSET = utf8;


-- Data
INSERT INTO country (name)        VALUES ('美国'), ('中国'), ('加拿大');
INSERT INTO movie_status (status) VALUES ('on'), ('soon');
INSERT INTO movie_type (type)     VALUES ('2D'), ('3D'), ('IMAX 3D');

INSERT INTO movie_style (style) VALUES
('剧情'), ('动作'), ('冒险'),
('爱情'), ('奇幻'), ('歌舞'),
('悬疑'), ('恐怖'), ('犯罪'),
('科幻'), ('惊悚'), ('喜剧'),
('动画'), ('音乐'), ('运动');

INSERT INTO movie
(countryID, movieStatusID, movieTypeID, pubdate, title, rating, length, posterSmall, posterLarge) VALUES
(1, 1, 2, '2017-03-17', "美女与野兽", 8.2, 130, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/1.jpg", "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/1.png"),
(1, 2, 3, '2017-04-14', "速度与激情8", DEFAULT, 136, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/2.jpg", "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/2.png"),
(1, 2, 3, '2017-04-07', "攻壳机动队", DEFAULT, 107, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/3.jpg", "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/3.png"),
(1, 1, 3, '2017-03-03', "金刚狼3：殊死一战", 8.7, 123, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/4.jpg", ""),
(1, 1, 1, '2017-02-17', "欢乐好声音", 9.2, 108, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/5.jpg", ""),
(2, 2, 1, '2017-04-02', "麦兜当当伴我心", DEFAULT, 80, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/6.jpg", ""),
(2, 2, 2, '2017-05-02', "八万里", DEFAULT, 91, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/7.jpg", ""),
(1, 1, 3, '2017-02-10', "极限特工：终极回归", 8.7, 107, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/8.jpg", ""),
(1, 1, 1, '2017-02-14', "爱乐之城", 8.6, 129, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/9.jpg", ""),
(1, 1, 1, '2017-03-03', "一条狗的使命", 9.0, 101, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/10.jpg", ""),
(1, 1, 3, '2017-03-24', "金刚：骷髅岛", 8.3, 119, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/11.jpg", ""),
(3, 2, 1, '2017-04-01', "冰雪大作战", DEFAULT, 83, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/12.jpg", ""),
(2, 2, 1, '2017-03-31', "绑架者", DEFAULT, 95, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/13.jpg", ""),
(2, 2, 1, '2017-03-31', "非凡任务", DEFAULT, 122, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/14.jpg", ""),
(1, 1, 2, '2017-02-24', "生化危机：终章", 8.5, 99, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/15.jpg", ""),
(2, 1, 1, '2017-03-24', "八月", 7.8, 106, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/16.jpg", ""),
(2, 2, 1, '2017-03-31', "嫌疑人x的献身", DEFAULT, 112, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/17.jpg", ""),
(2, 2, 1, '2017-04-01', "有完没完", DEFAULT, 99, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/small/18.jpg", "");

INSERT INTO movie_has_style (movieID, movieStyleID) VALUES
(1, 4), (1, 5), (1, 6),
(2, 2), (2, 9), (2, 11),
(3, 2), (3, 9), (3, 10),
(4, 2), (4, 3), (4, 10),
(5, 12), (5, 13), (5, 14),
(6, 13), (6, 14),
(7, 1), (7, 15),
(8, 2), (8, 11), (8, 3),
(9, 4), (9, 6),
(10, 1), (10, 12),
(11, 1), (11, 2), (11, 3),
(12, 12), (12, 13),
(13, 2), (13, 9),
(14, 2),
(15, 2), (15, 10), (15, 11),
(16, 1),
(17, 1), (17, 4), (17, 7),
(18, 1), (18, 12);

INSERT INTO cinema (name, location) VALUES
("金逸珠江国际影城（大学城店）", "番禺区小谷围街贝岗村中二横路1号高高新天地商业广场B2B001铺"),
("万达国际影城（番禺店）", "番禺区南村镇汉溪大道东389号番禺万达广场四楼"),
("UA花城汇影院", "天河区珠江新城花城广场花城汇B1楼1130铺（花城广场入口靠近黄埔大道）"),
("飞扬影城（天河城店）", "天河区天河路208号天河城广场4楼"),
("保利国际影城（中环店）", "越秀区建设大马路18号中环广场南楼5层");

INSERT INTO cinema_hall (cinemaID, name, seatLayout) VALUES
(1, "1号厅", "00111111111100,00111111111100,01111111111110,11111111111111,11111111111111,11111111111111"),
(1, "2号厅", "00111111111100,00111111111100,01111111111110,11111111111111,11111111111111,11111111111111"),
(1, "VIP厅", "001111111100,001111111100,011111111110,011111111110"),
(2, "1号厅", "1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111"),
(2, "2号厅", "1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111,1111111111111111"),
(2, "VIP厅", "111111,111111,111111,111111,111111,111111"),
(3, "1号厅", "000111111000,000111111000,001111111100,001111111100,011111111110,011111111110,111111111111,111111111111"),
(3, "2号厅", "000111111000,000111111000,001111111100,001111111100,011111111110,011111111110,111111111111,111111111111"),
(3, "VIP厅", "1111,1111,1111,1111,1111,1111"),
(4, "1号厅", "00011111111000,00111111111100,01111111111110,11111111111111"),
(4, "2号厅", "00011111111000,00111111111100,11111111111111,11111111111111"),
(4, "VIP厅", "011110,011110,011110,111111,111111,111111"),
(5, "1号厅", "011110,011110,011110,111111,111111,111111,111111,111111,111111"),
(5, "2号厅", "011110,011110,011110,011110,011110,011110,011110,111111,111111"),
(5, "VIP厅", "011110,011110,011110,111111,111111,111111");
