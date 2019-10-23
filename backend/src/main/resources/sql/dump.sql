-- MySQL dump 10.13  Distrib 8.0.17, for osx10.14 (x86_64)
--
-- Host: localhost    Database: meetuply
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `achievement`
--

DROP TABLE IF EXISTS `achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(400) NOT NULL,
  `icon` varchar(500) NOT NULL,
  `followers_number` int(11) DEFAULT NULL,
  `posts_number` int(11) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievement`
--

LOCK TABLES `achievement` WRITE;
/*!40000 ALTER TABLE `achievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `achievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `achievement_topic`
--

DROP TABLE IF EXISTS `achievement_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement_topic` (
  `achievement_id` int(10) unsigned NOT NULL,
  `topic_id` int(10) unsigned NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`achievement_id`,`topic_id`),
  KEY `topic_id` (`topic_id`),
  KEY `achievement_id` (`achievement_id`),
  CONSTRAINT `achievement_topic_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`) ON UPDATE CASCADE,
  CONSTRAINT `achievement_topic_ibfk_2` FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievement_topic`
--

LOCK TABLES `achievement_topic` WRITE;
/*!40000 ALTER TABLE `achievement_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `achievement_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ban`
--

DROP TABLE IF EXISTS `ban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ban` (
  `reason_id` int(10) unsigned NOT NULL,
  `by_user_id` int(10) unsigned NOT NULL,
  `reported_user_id` int(10) unsigned NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`reason_id`,`by_user_id`,`reported_user_id`),
  KEY `by_user_id` (`by_user_id`),
  KEY `reported_user_id` (`reported_user_id`),
  CONSTRAINT `ban_ibfk_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `ban_ibfk_2` FOREIGN KEY (`reported_user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ban_ibfk_3` FOREIGN KEY (`reason_id`) REFERENCES `ban_reason` (`uid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
/*!40000 ALTER TABLE `ban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ban_reason`
--

DROP TABLE IF EXISTS `ban_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ban_reason` (
  `name` varchar(100) NOT NULL,
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban_reason`
--

LOCK TABLES `ban_reason` WRITE;
/*!40000 ALTER TABLE `ban_reason` DISABLE KEYS */;
/*!40000 ALTER TABLE `ban_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `content` varchar(400) NOT NULL,
  `post_id` int(10) unsigned NOT NULL,
  `author` int(10) unsigned NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `post_id` (`post_id`),
  KEY `author` (`author`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`author`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `confirmation_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (44,'09ef4354-d4b7-44d9-a55a-ac4cc80ccf28','2019-10-23 20:18:00',72);
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `content` varchar(500) NOT NULL,
  `author_id` int(10) unsigned NOT NULL,
  `forwarded_to_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `author_id` (`author_id`),
  KEY `forwarded_to_id` (`forwarded_to_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`forwarded_to_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filter_topic`
--

DROP TABLE IF EXISTS `filter_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filter_topic` (
  `topic_id` int(10) unsigned NOT NULL,
  `filter_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`topic_id`,`filter_id`),
  KEY `filter_id` (`filter_id`),
  CONSTRAINT `filter_topic_ibfk_1` FOREIGN KEY (`filter_id`) REFERENCES `saved_filter` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `filter_topic_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filter_topic`
--

LOCK TABLES `filter_topic` WRITE;
/*!40000 ALTER TABLE `filter_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `filter_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followers`
--

DROP TABLE IF EXISTS `followers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `followers` (
  `followed_user_id` int(10) unsigned NOT NULL,
  `follower_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`followed_user_id`,`follower_id`),
  KEY `follower_id` (`follower_id`),
  KEY `followed_user_id` (`followed_user_id`),
  CONSTRAINT `followers_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `followers_ibfk_2` FOREIGN KEY (`followed_user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followers`
--

LOCK TABLES `followers` WRITE;
/*!40000 ALTER TABLE `followers` DISABLE KEYS */;
/*!40000 ALTER TABLE `followers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `name` varchar(100) NOT NULL,
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES ('English',1),('German',2);
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meetup`
--

DROP TABLE IF EXISTS `meetup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetup` (
  `title` varchar(300) NOT NULL,
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_date_time` datetime NOT NULL,
  `finish_date_time` datetime NOT NULL,
  `place` varchar(500) NOT NULL,
  `registered_attendees` int(11) NOT NULL,
  `min_attendees` int(11) NOT NULL,
  `max_attendees` int(11) NOT NULL,
  `state_id` smallint(5) unsigned NOT NULL,
  `speaker_id` int(10) unsigned NOT NULL,
  `description` varchar(400) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `state_id` (`state_id`),
  KEY `speaker_id` (`speaker_id`),
  CONSTRAINT `meetup_ibfk_1` FOREIGN KEY (`state_id`) REFERENCES `state` (`uid`) ON UPDATE CASCADE,
  CONSTRAINT `meetup_ibfk_2` FOREIGN KEY (`speaker_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup`
--

LOCK TABLES `meetup` WRITE;
/*!40000 ALTER TABLE `meetup` DISABLE KEYS */;
INSERT INTO `meetup` VALUES ('Test #1',2,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',1,1,2,1,56,'Very exciting'),('Test #3',3,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',0,1,2,1,71,'Very exciting'),('Test #4',4,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',0,1,2,1,71,'Very exciting'),('Test #5',5,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',0,1,2,1,56,'Very exciting'),('Test #6',6,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',0,1,2,1,56,'Very exciting'),('Test #2',7,'2019-10-21 11:20:09','2019-10-21 11:20:09','Office #1',0,1,2,1,56,'Very exciting');
/*!40000 ALTER TABLE `meetup` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bfr_change_meetup` BEFORE UPDATE ON `meetup` FOR EACH ROW BEGIN
    IF NEW.max_attendees < NEW.registered_attendees THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max number of attendees cannot be less then current number of registered attendees';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `meetup_attendees`
--

DROP TABLE IF EXISTS `meetup_attendees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetup_attendees` (
  `meetup_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`meetup_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `meetup_attendees_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `meetup_attendees_ibfk_2` FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup_attendees`
--

LOCK TABLES `meetup_attendees` WRITE;
/*!40000 ALTER TABLE `meetup_attendees` DISABLE KEYS */;
INSERT INTO `meetup_attendees` VALUES (2,56);
/*!40000 ALTER TABLE `meetup_attendees` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bfr_join_meetup` BEFORE INSERT ON `meetup_attendees` FOR EACH ROW BEGIN
    SET @FREE_PLACES = (SELECT max_attendees - registered_attendees FROM meetup WHERE uid = NEW.meetup_id);
    IF @FREE_PLACES > 0 THEN
        UPDATE meetup
        SET registered_attendees = registered_attendees + 1
        WHERE uid = NEW.meetup_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Meetup is fully booked';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bfr_change_attendees` BEFORE UPDATE ON `meetup_attendees` FOR EACH ROW BEGIN
    IF OLD.meetup_id <> NEW.meetup_id THEN
        SET @FREE_PLACES = (SELECT max_attendees - registered_attendees FROM meetup WHERE uid = NEW.meetup_id);
        IF @FREE_PLACES > 0 THEN
            UPDATE meetup
            SET registered_attendees = registered_attendees + 1
            WHERE uid = NEW.meetup_id;
            UPDATE meetup
            SET registered_attendees = registered_attendees - 1
            WHERE uid = OLD.meetup_id;
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Meetup is fully booked';
        END IF;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bfr_leave_meetup` BEFORE DELETE ON `meetup_attendees` FOR EACH ROW BEGIN
    UPDATE meetup
    SET registered_attendees = registered_attendees - 1
    WHERE uid = OLD.meetup_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `meetup_language`
--

DROP TABLE IF EXISTS `meetup_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetup_language` (
  `language_id` int(10) unsigned NOT NULL,
  `meetup_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`language_id`,`meetup_id`),
  KEY `meetup_id` (`meetup_id`),
  CONSTRAINT `meetup_language_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `meetup_language_ibfk_2` FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup_language`
--

LOCK TABLES `meetup_language` WRITE;
/*!40000 ALTER TABLE `meetup_language` DISABLE KEYS */;
/*!40000 ALTER TABLE `meetup_language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meetup_topic`
--

DROP TABLE IF EXISTS `meetup_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetup_topic` (
  `topic_id` int(10) unsigned NOT NULL,
  `meetup_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`topic_id`,`meetup_id`),
  KEY `meetup_id` (`meetup_id`),
  CONSTRAINT `meetup_topic_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `meetup_topic_ibfk_2` FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup_topic`
--

LOCK TABLES `meetup_topic` WRITE;
/*!40000 ALTER TABLE `meetup_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `meetup_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `content` varchar(256) NOT NULL,
  `from` int(10) unsigned NOT NULL,
  `to_user_id` int(10) unsigned DEFAULT NULL,
  `to_room_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `from` (`from`),
  KEY `to_user_id` (`to_user_id`),
  KEY `to_room_id` (`to_room_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`from`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`to_room_id`) REFERENCES `chat_room` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `is_read` tinyint(1) NOT NULL,
  `receiver_id` int(10) unsigned NOT NULL,
  `template_id` int(11) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `receiver_id` (`receiver_id`),
  KEY `template_id` (`template_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`template_id`) REFERENCES `notification_template` (`uid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_template`
--

DROP TABLE IF EXISTS `notification_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_template` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `html_text` varchar(700) NOT NULL,
  `plain_text` varchar(400) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_template`
--

LOCK TABLES `notification_template` WRITE;
/*!40000 ALTER TABLE `notification_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `date_time` datetime NOT NULL,
  `content` varchar(2000) NOT NULL,
  `author_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `rated_user_id` int(10) unsigned NOT NULL,
  `rated_by` int(10) unsigned NOT NULL,
  `value` smallint(6) NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`rated_user_id`,`rated_by`),
  KEY `rated_by` (`rated_by`),
  KEY `rated_user_id` (`rated_user_id`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`rated_by`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`rated_user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `name` varchar(45) NOT NULL,
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5091999 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('admin',5091998),('user',3061979);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved_filter`
--

DROP TABLE IF EXISTS `saved_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_filter` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `rating` float DEFAULT NULL,
  `date_time_from` datetime DEFAULT NULL,
  `date_time_to` datetime DEFAULT NULL,
  `owner_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `saved_filter_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_filter`
--

LOCK TABLES `saved_filter` WRITE;
/*!40000 ALTER TABLE `saved_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `saved_filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state` (
  `name` varchar(20) NOT NULL,
  `uid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES ('Booked',11),('Canceled',21),('In progress',31),('Passed',51),('Scheduled',1),('Terminated',41);
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `name` varchar(100) NOT NULL,
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES ('sport',1),('politics',2),('health',3);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(200) NOT NULL,
  `password` varchar(61) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `registration_confirmed` tinyint(1) NOT NULL,
  `is_deactivated` tinyint(1) NOT NULL,
  `allow_notifications` tinyint(1) NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  `photo` varchar(300) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `location` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`uid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (56,'111@gmail.com','$2a$10$F3vsfcPxDenhA5.rb1CGf.882Nln1SV62wdniRzem8iUr0e/Rs2BG','111','111',1,0,1,3061979,NULL,'Very cool guy','Kyiv'),(57,'222@gmail.com','$2a$10$F60GkxL/u95n0tSlwFfmeeuYJ4m2sQvny2QVRpyt/e5faJRW7nI0O','222','222',1,1,1,3061979,NULL,'Very cool guy','London'),(58,'333@gmail.com','$2a$10$3gOV4.Za8Q4X8qdXlS7O6.wb2qP9GPliw.rjup5d72TPxRc2iFzH2','333','333',1,0,1,3061979,NULL,'Very cool guy','Barcelona'),(59,'444@gmail.com','$2a$10$yaJ80oSP5PquPh2uLODLkuH8tX07SjqMog6BGp2phlQvEYRbIZEZC','444','444',0,0,1,3061979,NULL,'Very cool guy','New York'),(60,'555@gmail.com','$2a$10$F4ljdh8CEyVgT1ZE6ndo3e6qDg7chOipUaNaV7MBPnZRBa5mndVCC','555','555',1,0,1,3061979,NULL,'Very cool guy','Deli'),(70,'admin@gmail.com','$2a$10$nvJa3JKPnKJTYvIcl1Q8hea/HeqDtH3itBA3SkdxkS3Itimmvs9Le','Admin','Admin',1,0,1,5091998,NULL,'Very cool guy','Tokyo'),(71,'mr.sn5.kma@gmail.com','$2a$10$H4fuqIKyS7GnLRqC9NYhTeUgmxDXMubSRVAsbvXC9XAcjP25Lm8wW','San','Nguyen',1,0,1,3061979,NULL,'Very cool guy','Berlin'),(72,'999@gmail.com','$2a$10$r9iL9fmw6m8kVbxqMPi3Tenmvl2Ql4W5FHj01bc3eB8NT8rJfFBOO','999','999',0,0,1,3061979,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `afr_deactivated_user` AFTER UPDATE ON `user` FOR EACH ROW BEGIN
    IF NEW.is_deactivated = 1 THEN

        SET @FUTURE_MEETUPS = (SELECT GROUP_CONCAT(uid) FROM meetup
                               WHERE state_id IN (SELECT uid FROM state
                                                  WHERE state.name = 'Booked'
                                                     OR state.name = 'Scheduled'));
        DELETE FROM meetup_attendees
        WHERE user_id = OLD.uid
          AND FIND_IN_SET(meetup_id, @FUTURE_MEETUPS);

    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `afr_deactivated_user_cancel_meetups` AFTER UPDATE ON `user` FOR EACH ROW BEGIN
    IF NEW.is_deactivated = 1 THEN

        SET @FUTURE_MEETUPS = (SELECT GROUP_CONCAT(uid) FROM meetup
                               WHERE speaker_id = OLD.uid
                                 AND state_id IN (SELECT uid FROM state
                                                  WHERE state.name = 'Booked'
                                                     OR state.name = 'Scheduled'));

        SET @CANCELED_ID = (SELECT uid FROM state WHERE name = 'Canceled');

        UPDATE meetup
        SET state_id = @CANCELED_ID
        WHERE FIND_IN_SET(uid, @FUTURE_MEETUPS);

    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user_achievement`
--

DROP TABLE IF EXISTS `user_achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_achievement` (
  `achievement_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`achievement_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_achievement_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_achievement_ibfk_2` FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_achievement`
--

LOCK TABLES `user_achievement` WRITE;
/*!40000 ALTER TABLE `user_achievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_achievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_chat_room`
--

DROP TABLE IF EXISTS `user_chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_chat_room` (
  `room_id` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`room_id`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `user_chat_room_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `chat_room` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_chat_room_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_chat_room`
--

LOCK TABLES `user_chat_room` WRITE;
/*!40000 ALTER TABLE `user_chat_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_language`
--

DROP TABLE IF EXISTS `user_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_language` (
  `user_id` int(10) unsigned NOT NULL,
  `language_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`language_id`),
  KEY `language_id` (`language_id`),
  CONSTRAINT `user_language_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_language_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_language`
--

LOCK TABLES `user_language` WRITE;
/*!40000 ALTER TABLE `user_language` DISABLE KEYS */;
INSERT INTO `user_language` VALUES (60,1),(60,2);
/*!40000 ALTER TABLE `user_language` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-23 23:18:44
