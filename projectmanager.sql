DROP DATABASE IF EXISTS projectmanager;
CREATE DATABASE projectmanager;
USE projectmanager;

CREATE TABLE `managers` (
	`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(25) NOT NULL,
    `last_name` VARCHAR(25) NOT NULL,
    `email` VARCHAR(64) NOT NULL
);

CREATE TABLE `projects` (
	`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `description` VARCHAR(500) NOT NULL,
    `status` ENUM('active', 'completed', 'planned') NOT NULL,
    `manager_id` INT NOT NULL,
    FOREIGN KEY (`manager_id`) REFERENCES `managers`(`id`)
);


INSERT INTO `managers` (`first_name`, `last_name`, `email`)
VALUES('Tony', 'Stark', 'tonystark@gmail.com');

INSERT INTO `managers` (`first_name`, `last_name`, `email`)
VALUES('Bruce', 'Wayne', 'brucewayne@gmail.com');