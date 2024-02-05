DROP TABLE IF EXISTS USER;

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
        `is_active` tinyint(1) NOT NULL DEFAULT '1',
        `following` varchar(100) DEFAULT NULL
);