DROP DATABASE IF EXISTS `android_im2073_db`;
CREATE DATABASE `android_im2073_db`;

USE `android_im2073_db`;

CREATE TABLE `Responses`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `question_id` BIGINT UNSIGNED NOT NULL,
    `player_id` BIGINT UNSIGNED NOT NULL,
    `choice` VARCHAR(1) NOT NULL
);
CREATE TABLE `Questions`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room_id` BIGINT UNSIGNED NOT NULL,
    `question_text` TEXT NOT NULL,
    `answer` VARCHAR(1) NULL,
    `choice_one` TEXT NOT NULL,
    `choice_two` TEXT NOT NULL,
    `choice_three` TEXT NOT NULL,
    `choice_four` TEXT NOT NULL
);
CREATE TABLE `Users`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);
CREATE TABLE `RoomSessions`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room_code` VARCHAR(6) NOT NULL,
    `max_capacity` BIGINT NOT NULL DEFAULT 10,
    `owner_id` BIGINT UNSIGNED NOT NULL,
    `isOpen` BOOLEAN NOT NULL DEFAULT 1,
    `questionInterval` BIGINT NOT NULL DEFAULT 30
);
CREATE TABLE `Players`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room_id` BIGINT UNSIGNED NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `score` BIGINT NOT NULL DEFAULT 0
);
ALTER TABLE
    `Questions` ADD CONSTRAINT `questions_room_id_foreign` FOREIGN KEY(`room_id`) REFERENCES `RoomSessions`(`id`);
ALTER TABLE
    `Responses` ADD CONSTRAINT `responses_question_id_foreign` FOREIGN KEY(`question_id`) REFERENCES `Questions`(`id`);
ALTER TABLE
    `RoomSessions` ADD CONSTRAINT `roomsessions_owner_id_foreign` FOREIGN KEY(`owner_id`) REFERENCES `Users`(`id`);
ALTER TABLE
    `Responses` ADD CONSTRAINT `responses_player_id_foreign` FOREIGN KEY(`player_id`) REFERENCES `Players`(`id`);
ALTER TABLE
    `Players` ADD CONSTRAINT `players_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `Users`(`id`);
ALTER TABLE
    `Players` ADD CONSTRAINT `players_room_id_foreign` FOREIGN KEY(`room_id`) REFERENCES `RoomSessions`(`id`);

INSERT INTO `Users` (`name`,`email`,`password`) VALUES
('Avi','gibraltar.av@gmail.com','xxxx'),
('Googlex','google@gmail.com','xxxx');

INSERT INTO `RoomSessions` (`room_code`, `owner_id`) VALUES
('123456', 1),
('654321', 2);

INSERT INTO `Questions` (`room_id`, `question_text`, `answer`, `choice_one`, `choice_two`, `choice_three`, `choice_four`) VALUES
(1, 'What is the capital of France?', 'A', 'Paris', 'London', 'Berlin', 'Madrid'),
(1, 'What is the capital of Germany?', 'C', 'Paris', 'London', 'Berlin', 'Madrid'),
(1, 'What is the capital of Spain?', 'D', 'Paris', 'London', 'Berlin', 'Madrid'),
(1, 'What is the capital of England?', 'B', 'Paris', 'London', 'Berlin', 'Madrid');

INSERT INTO `Players` (`room_id`, `user_id`) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2);

INSERT INTO `Responses` (`question_id`, `player_id`, `choice`) VALUES
(1, 1, 'A'),
(1, 2, 'B'),
(2, 1, 'C'),
(2, 2, 'D'),
(3, 1, 'D'),
(3, 2, 'C'),
(4, 1, 'B'),
(4, 2, 'A');

SELECT * FROM `Users`;
SELECT * FROM `Questions`;
SELECT * FROM `RoomSessions`;
SELECT * FROM `Players`;
SELECT * FROM `Responses`;