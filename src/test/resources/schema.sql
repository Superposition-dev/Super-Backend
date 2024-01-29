DROP TABLE IF EXISTS exhibition;

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