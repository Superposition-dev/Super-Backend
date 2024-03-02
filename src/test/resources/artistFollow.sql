DROP TABLE IF EXISTS artist_follow;

CREATE TABLE artist_follow
(
    email        varchar(50)       DEFAULT NULL,
    instagram_id varchar(50)       DEFAULT NULL,
    created_at   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (email, instagram_id)
);