/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.17 : Database - vlocity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vlocity` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `vlocity`;

/*Table structure for table `projectplan` */

DROP TABLE IF EXISTS `projectplan`;

CREATE TABLE `projectplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` tinyint(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `projectplan` */

insert  into `projectplan`(`id`,`deleted`,`description`,`name`) values 
(1,0,'My Project 1 description','My Project 1');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` tinyint(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`deleted`,`description`,`name`) values 
(1,0,'Has permission to all functionality','ADMIN');

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` tinyint(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `taskStatus` varchar(255) DEFAULT NULL,
  `projectPlan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj0d08g1brg12s7vpnsqnuvuqw` (`projectPlan_id`),
  CONSTRAINT `FKj0d08g1brg12s7vpnsqnuvuqw` FOREIGN KEY (`projectPlan_id`) REFERENCES `projectplan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `task` */

insert  into `task`(`id`,`deleted`,`description`,`endDate`,`name`,`startDate`,`taskStatus`,`projectPlan_id`) values 
(1,0,'My Task 1 description','2019-11-15','Task 1','2019-11-11','COMPLETED',1),
(5,0,'My Task 2 description','2019-11-20','Task 2','2019-11-16','IN_PROGRESS',1);

/*Table structure for table `task_dependencies` */

DROP TABLE IF EXISTS `task_dependencies`;

CREATE TABLE `task_dependencies` (
  `task_id` bigint(20) NOT NULL,
  `dependency_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`dependency_id`),
  KEY `FK5yo414uju5w44dmhvrn9b90e1` (`dependency_id`),
  CONSTRAINT `FK5yo414uju5w44dmhvrn9b90e1` FOREIGN KEY (`dependency_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKiyre6lavgpk0lxejfvd5lto7o` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `task_dependencies` */

insert  into `task_dependencies`(`task_id`,`dependency_id`) values 
(5,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` tinyint(1) DEFAULT '0',
  `password` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`deleted`,`password`,`userType`,`username`) values 
(1,0,'$2a$10$AsvWMct96KOXT6t/9FvJQujNKclMGsjaOv/Pwvxv0u5uEkkExs9.m','ADMIN','admin');

/*Table structure for table `user_roles` */

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKeagjlbd56vsgs969265h6dsla` (`role_id`),
  CONSTRAINT `FKeagjlbd56vsgs969265h6dsla` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKhpppd2jtxojlqq7wabk8633nw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_roles` */

insert  into `user_roles`(`user_id`,`role_id`) values 
(1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
