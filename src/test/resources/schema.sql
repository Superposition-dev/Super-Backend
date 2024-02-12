DROP TABLE IF EXISTS product_with_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS exhibition;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS `like`;

CREATE TABLE exhibition
(
    exhibition_id int          NOT NULL AUTO_INCREMENT,
    title         varchar(100) NOT NULL,
    subHeading    varchar(200) NOT NULL,
    location      varchar(100) NOT NULL,
    start_date    date         NOT NULL,
    end_date      date         NOT NULL,
    status        varchar(100) NOT NULL,
    poster        varchar(100) NOT NULL,
    PRIMARY KEY (exhibition_id)
);

CREATE TABLE artist
(
    name               varchar(50)   DEFAULT NULL,
    profile            varchar(50)   DEFAULT NULL,
    introduce          varchar(50)   DEFAULT NULL,
    description        varchar(1024) DEFAULT NULL,
    instagram_id       varchar(50)   DEFAULT NULL,
    is_display         int           DEFAULT NULL,
    view               int           DEFAULT NULL,
    is_about           int           DEFAULT NULL,
    collaboration_date varchar(50)   DEFAULT NULL,
    PRIMARY KEY (instagram_id)
);

CREATE TABLE product
(
    product_id    int NOT NULL,
    picture       varchar(50)   DEFAULT NULL,
    title         varchar(50)   DEFAULT NULL,
    artist_name   varchar(50)   DEFAULT NULL,
    description   varchar(1024) DEFAULT NULL,
    price         int           DEFAULT NULL,
    basic_view    int           DEFAULT NULL,
    qr_view       int           DEFAULT NULL,
    like_count    int           DEFAULT NULL,
    order_count   int           DEFAULT NULL,
    exhibition_id int           DEFAULT NULL,
    PRIMARY KEY (product_id),
    FOREIGN KEY (exhibition_id) REFERENCES exhibition (exhibition_id)
);

CREATE TABLE product_with_tag
(
    product_id int DEFAULT NULL,
    tag_id     int DEFAULT NULL
);

CREATE TABLE tag
(
    tag_id int         DEFAULT NULL,
    name   varchar(50) DEFAULT NULL
);

CREATE TABLE `USER` (
                        `email` varchar(50) NOT NULL,
                        `name` varchar(100) DEFAULT NULL,
                        `nickname` varchar(100) DEFAULT NULL,
                        `profile` varchar(100) DEFAULT NULL,
                        `gender` varchar(1) DEFAULT NULL,
                        `birth_date` date DEFAULT NULL,
                        `is_artist` tinyint(1) NOT NULL DEFAULT '0',
                        `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `nickname_update_at` date DEFAULT NULL,
                        `delete_at` timestamp NULL DEFAULT NULL,
                        `is_active` tinyint(1) NOT NULL DEFAULT '1'
);

CREATE TABLE `like` (
                        `product_id` int DEFAULT NULL,
                        `user_id` varchar(100) DEFAULT NULL
);