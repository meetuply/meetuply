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
  `followers` int(11) DEFAULT NULL,
  `posts` int(11) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `meetups` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
INSERT INTO `ban` VALUES (1,94,85,'','2006-09-11 12:39:33'),(2,4,69,'Sit qui fuga dolores eveniet et incidunt veniam sed. Voluptatem repudiandae ad pariatur deserunt. Mollitia laborum qui voluptas repudiandae labore assumenda illum. Enim ratione rerum omnis excepturi. ','1997-03-15 22:41:22'),(3,73,100,'','2018-10-06 22:13:11'),(4,96,16,'Recusandae nam aut omnis dolores mollitia molestias. In ullam non sint ut et. Quos quae velit similique quia rerum quae. Excepturi culpa inventore nostrum.','1971-01-25 21:12:33'),(5,79,95,'Reprehenderit tempore aut illo omnis velit minus. Deleniti blanditiis esse beatae rerum aliquid ipsam et omnis. Quis atque voluptatibus non saepe nesciunt voluptas ea.','1983-01-14 16:59:23'),(6,66,67,'Et neque maiores temporibus consequatur animi eum. Rem quis dolores harum sed. Non est modi atque non corporis. Eos alias repudiandae sit doloremque reiciendis.','2007-04-29 06:42:28'),(7,40,51,'Ut est iusto velit molestiae quisquam. Possimus corporis facere id ea vel. Aut nihil qui voluptates provident quisquam.','1998-12-30 01:00:20'),(8,55,83,'','1996-10-07 12:40:13'),(9,91,5,'','1998-02-18 09:09:53'),(10,66,45,'Occaecati dolores sint ut assumenda autem. Et cumque aperiam provident error aut vero voluptas. Vel est illo delectus autem.','1990-07-18 21:32:39'),(11,66,80,'','2009-10-29 11:01:08'),(12,36,38,'Quasi occaecati atque maxime eum necessitatibus a. Non nihil tempora doloribus maiores molestiae ut ea laboriosam. Omnis assumenda iste rerum ut iusto eligendi. Aut labore atque quidem unde.','1991-09-23 06:12:03'),(13,39,29,'Rerum officiis adipisci minima rem et et. Ex qui dolor ex repellendus modi. Dolores voluptates voluptate possimus vel dolor hic. Eligendi tempora dolores repudiandae ut est suscipit. Explicabo illum e','1974-06-10 04:18:54'),(14,78,18,'Voluptas et est nostrum tenetur laboriosam beatae sit ea. Excepturi necessitatibus ut non sunt nemo. Illum id vel explicabo voluptatem hic.','1980-05-19 13:30:32'),(15,75,89,'','2018-11-30 16:47:15'),(16,46,68,'','1995-01-04 15:06:03'),(17,73,50,'','1974-08-25 06:01:09'),(18,36,46,'','2018-02-27 15:36:24'),(19,49,32,'Fugiat sapiente voluptatem eaque tempora qui et necessitatibus. Unde non id est animi quidem.','2011-02-20 19:20:16'),(20,61,27,'Id aspernatur maiores quia soluta reiciendis velit omnis. Repudiandae et in adipisci et.','1974-09-24 18:14:05');
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban_reason`
--

LOCK TABLES `ban_reason` WRITE;
/*!40000 ALTER TABLE `ban_reason` DISABLE KEYS */;
INSERT INTO `ban_reason` VALUES ('quia',1),('possimus',2),('nobis',3),('impedit',4),('enim',5),('molestias',6),('ex',7),('iste',8),('earum',9),('eius',10),('numquam',11),('hic',12),('occaecati',13),('est',14),('odio',15),('laboriosam',16),('et',17),('soluta',18),('non',19),('nihil',20);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (67,'a05ee134-271d-4af3-a0d8-73edb604cdf2','2019-11-05 16:20:56',103);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followers`
--

LOCK TABLES `followers` WRITE;
/*!40000 ALTER TABLE `followers` DISABLE KEYS */;
INSERT INTO `followers` VALUES (30,1),(41,1),(43,2),(9,3),(19,3),(36,3),(58,3),(40,4),(31,5),(88,5),(39,7),(83,7),(74,8),(34,9),(38,9),(89,9),(12,10),(63,10),(6,11),(22,11),(32,12),(38,12),(45,12),(50,12),(69,12),(84,12),(47,13),(57,13),(77,14),(31,15),(59,16),(63,16),(15,17),(49,17),(86,17),(34,18),(52,18),(2,19),(11,19),(88,19),(18,20),(77,21),(15,22),(22,22),(38,22),(42,22),(47,22),(99,22),(89,23),(39,24),(6,25),(93,25),(9,26),(87,26),(37,27),(88,27),(90,28),(20,29),(95,29),(14,30),(31,30),(52,31),(75,31),(51,32),(59,32),(83,32),(2,33),(26,35),(97,35),(19,36),(60,36),(79,36),(35,37),(54,37),(57,37),(80,37),(19,38),(80,38),(31,39),(67,39),(11,40),(33,41),(42,43),(76,43),(83,43),(23,44),(56,44),(87,44),(21,45),(29,45),(47,45),(49,45),(28,47),(46,47),(28,48),(57,48),(92,48),(94,48),(1,50),(45,50),(60,50),(44,51),(67,51),(36,52),(57,52),(71,53),(71,54),(81,54),(100,54),(72,55),(65,57),(79,57),(44,58),(70,58),(41,59),(84,59),(78,61),(64,62),(100,63),(28,65),(37,65),(19,66),(61,66),(53,68),(92,68),(57,70),(62,70),(72,70),(79,70),(91,70),(6,71),(45,71),(91,71),(39,72),(42,72),(1,73),(27,73),(53,74),(30,75),(42,75),(50,75),(63,75),(48,76),(25,77),(37,77),(43,78),(21,79),(81,79),(92,79),(7,80),(19,80),(24,80),(62,80),(28,81),(100,81),(53,82),(64,82),(20,83),(25,83),(29,83),(93,83),(44,84),(96,84),(20,86),(9,87),(14,87),(53,87),(92,87),(19,88),(59,88),(86,88),(57,89),(84,89),(90,89),(39,90),(63,90),(66,90),(4,91),(18,91),(48,91),(48,92),(95,92),(42,93),(5,94),(1,95),(29,95),(32,95),(44,96),(49,96),(80,96),(86,96),(87,96),(85,97),(66,98),(6,99),(41,99),(12,100),(13,100),(46,100);
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
  PRIMARY KEY (`uid`),
  UNIQUE KEY `language_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES ('amet',8),('amety',12),('aut',7),('ea',11),('et',6),('etty',10),('ex',3),('impedit',9),('voluptate',5),('voluptatem',4);
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
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup`
--

LOCK TABLES `meetup` WRITE;
/*!40000 ALTER TABLE `meetup` DISABLE KEYS */;
INSERT INTO `meetup` VALUES ('Et non qui sed reprehenderit consectetur.',1,'1975-10-11 15:00:00','1980-12-30 22:15:09','15771 Dicki Park Apt. 342\nLake Roosevelt, WI 43814-8705',6,4,6,6,102,'Harum aut rerum qui molestias doloremque. Sed esse quidem labore eaque error autem ut. Libero aspernatur aut et in vitae.'),('Architecto deleniti distinctio aut praesentium.',2,'1985-10-09 12:00:00','2015-04-07 21:06:12','1707 Grayce Crescent Suite 214\nLesterland, IN 61827',4,5,20,6,102,'Amet odio repudiandae aut maxime molestias quos explicabo. Cum dolorum et deserunt eius. Labore velit qui aliquid sit qui voluptatem assumenda. Iure temporibus quisquam minima earum occaecati quibusdam accusantium non.'),('Minima eius est tenetur ipsa voluptatum est.',3,'2009-03-09 14:00:00','2015-09-11 20:37:09','00079 Ewald Club Apt. 562\nEast Roxanechester, NE 90011-1877',7,3,20,6,102,'Aut error et dicta aliquid neque dolor asperiores. Pariatur vel consequuntur quisquam voluptatem ipsum laboriosam et. Nihil modi mollitia ducimus aut soluta facere sit enim. Tenetur deserunt explicabo quibusdam rerum dolor porro necessitatibus.'),('Minus officia fuga consequatur quia.',4,'2019-10-28 20:00:00','2019-11-01 20:00:00','3072 Ivah Dam\nWest Lillianahaven, OR 97188-0921',4,1,17,6,102,'Beatae cumque hic dignissimos quidem in. Natus modi excepturi nemo nobis et. Consequatur pariatur incidunt et.'),('Accusamus dolorem laborum corrupti ea nostrum odit reiciendis.',5,'2020-02-18 04:00:00','2020-04-05 01:55:22','2664 Bailey Flat Apt. 469\nLake Lyric, ND 75363',4,2,4,2,102,'Et omnis nemo odit. Ut animi nulla deleniti excepturi. Ullam repellendus id eum autem non et.'),('Blanditiis animi a facilis sit veniam sit et.',6,'1990-09-26 00:00:00','2013-07-21 18:40:40','885 Mckayla Ranch\nNickfort, WY 40152',5,3,20,5,102,'Esse nihil necessitatibus quam magni aut voluptatibus. Nisi consequatur tempora nihil perspiciatis illum. Ea dicta aut molestiae id cum.'),('Tempora aspernatur omnis enim distinctio.',7,'1976-05-27 15:00:00','1995-08-24 13:01:30','5024 Guido Avenue Suite 920\nNew Eveline, CA 30996-5316',4,2,13,6,102,'Dolorem est est saepe aliquam ad. Animi aut aut rerum recusandae ipsam distinctio. Adipisci velit et ab voluptatem sit ut tenetur.'),('Placeat nihil omnis architecto.',8,'1990-06-07 04:00:00','1999-04-08 15:27:42','6773 Joy Green Apt. 001\nSouth Bill, VA 94213-6414',5,3,5,6,102,'Eveniet dolores architecto suscipit omnis. Ratione voluptatem labore facilis ut voluptatem ex consectetur. Eaque ipsum quia sunt eaque sequi sapiente sit aliquid. Odit aut nesciunt non perferendis magnam illum similique.'),('Voluptatem iste porro placeat adipisci necessitatibus repudiandae itaque.',9,'2007-02-21 18:00:00','1976-07-03 03:09:54','819 Maritza Ranch Apt. 050\nPort Thomasbury, RI 89808',6,2,20,6,102,'Eius aut consequatur excepturi maiores suscipit voluptatem. Quidem dolorum accusantium voluptatibus commodi consequatur. Soluta et ratione tenetur ut ad doloremque. Voluptatum doloribus ab nulla culpa.'),('Dignissimos et nam sint minima voluptas et similique.',10,'1972-10-05 12:00:00','1987-08-27 18:40:09','6921 Katherine Freeway Suite 243\nDinaland, NC 19332-3510',3,3,10,6,102,'Ea quas dignissimos ipsam fuga ratione rem blanditiis. Est voluptas sequi quis possimus sunt est omnis.'),('Necessitatibus alias vitae suscipit eius et.',11,'1983-11-05 09:00:00','1970-08-26 02:55:04','779 Vanessa Road\nNorth Garth, IA 13015',4,4,11,6,21,'Quibusdam et quam consectetur aut aspernatur. Libero ut est ipsum ab. Quia dolor mollitia quaerat libero. Eos a nobis veniam magnam repellendus ab.'),('Suscipit nulla earum eos cum.',12,'1977-11-04 09:00:00','1986-07-08 02:42:42','3376 Ian Keys Suite 986\nGorczanyshire, DE 88759-0838',4,3,8,6,79,'Eveniet dolor qui placeat commodi porro. Commodi quo dolores consequatur quia ipsum. Aut harum aperiam recusandae cupiditate praesentium. Quo esse eos minus consequatur. Aperiam eius sint veritatis natus error ea.'),('Nisi est sed vitae numquam.',13,'1979-09-01 09:00:00','1988-03-24 19:07:35','86223 Ozella Shore Apt. 548\nSouth Agustinabury, KS 80131',2,5,15,6,32,'Vel quasi reiciendis adipisci ea sit dolores aut. Ut quae tenetur eligendi eos corrupti.'),('Eum non recusandae laboriosam eligendi est et sint id.',14,'1978-07-06 00:00:00','2003-09-25 14:16:26','2996 Friesen Inlet\nJaniyaton, DE 85008-6759',2,2,10,5,27,'Aliquam alias eos quam eius et laborum enim. Voluptatem omnis quis assumenda beatae quaerat nostrum. Debitis est ducimus quo occaecati.'),('Quis vitae rerum dolor consequuntur libero.',15,'2005-08-08 00:00:00','2001-07-07 04:52:44','872 Smith Branch\nCortneyland, AR 98540-5316',5,4,8,3,92,'Qui accusamus deserunt dolores a. Veniam dolorum magnam aut sint. Voluptate nihil ratione neque dolore. Maxime iste excepturi aspernatur quod.'),('Debitis tempore impedit iusto aut quidem.',16,'2018-10-09 15:00:00','1975-05-24 07:19:03','68556 Moore Ports Suite 514\nErnserview, ME 46602',5,5,10,6,35,'Nemo repellendus et totam beatae. Tempora nemo eaque neque in explicabo. Beatae omnis rerum qui eveniet. Nobis minima reprehenderit impedit error ratione cumque.'),('Atque nesciunt omnis officiis nobis aut nobis omnis.',17,'2013-01-30 00:00:00','2000-10-18 16:57:23','45771 Toy Junctions Suite 552\nGreenchester, SC 88506-5156',3,4,12,5,38,'Et odit perspiciatis aliquam et culpa similique mollitia. Accusamus animi maxime adipisci in. Sit quibusdam vitae unde sit eveniet.'),('Culpa ad qui beatae tenetur ut neque labore accusantium.',18,'1985-01-26 09:00:00','1988-07-23 11:53:05','2129 Laverne Knoll\nEast Deshawntown, CT 53071',3,2,9,6,94,'Aut dolorem molestiae consequuntur corrupti quis aut fugit. Officia molestiae vel pariatur modi dolorem. Ut qui harum molestiae consequuntur voluptate illo.'),('Id enim assumenda placeat est et quis.',19,'1988-01-20 00:00:00','2019-06-13 15:28:30','0125 Grimes Cliff\nHaneview, OR 37769',3,3,9,3,72,'Dolore ullam provident numquam nesciunt expedita quisquam. Laudantium commodi neque labore est necessitatibus sed ut. Nihil dolores sequi quas quo sit. Cum adipisci officiis amet.'),('Magnam nesciunt sunt sapiente.',20,'1997-08-08 00:00:00','2007-02-12 07:05:41','350 Mills Meadows\nPort Chaya, RI 18715-8456',2,1,7,3,99,'Ut accusamus ab similique. Voluptatum eaque qui neque enim aut aliquam cupiditate. Rem facere adipisci suscipit vel. Ducimus quidem sunt dolorem ad nostrum.'),('Sit molestiae magnam sequi cumque.',21,'2010-11-30 00:00:00','1999-05-23 12:55:20','263 Bernhard Parks\nPort Emmittchester, CO 03054',3,5,9,3,18,'Quis fuga perferendis aut. Vero dolorum ipsa atque possimus. Nemo distinctio magni earum qui esse. Fuga dignissimos numquam asperiores necessitatibus quos repudiandae. Rem vitae et officiis qui.'),('Error omnis dolor pariatur.',22,'1979-04-20 09:00:00','1972-02-04 22:43:59','6147 Wehner Mall\nLake Ceasarport, AK 07839-2838',0,1,5,6,28,'Quaerat tenetur possimus error qui. Eveniet quae hic hic rem id id. Doloremque hic nisi nesciunt est et perferendis. Numquam labore beatae debitis neque molestias.'),('Sint molestias sit repellat sunt iste.',23,'2007-03-26 09:00:00','2006-06-18 02:05:15','150 Eleanore View\nHankchester, VA 19726-6254',4,5,17,6,41,'Aspernatur dolore libero sint amet ducimus et. Neque eveniet quisquam fugiat assumenda sequi odio voluptatum eos. Suscipit nesciunt occaecati in atque quae.'),('Ipsum voluptatem necessitatibus autem illo.',24,'2006-01-01 14:00:00','2017-05-17 17:59:48','7451 D\'Amore Track\nNew Gabefurt, WI 04102-3639',2,5,17,6,100,'Voluptas maxime qui expedita nulla. Repudiandae ut aut assumenda quae vitae exercitationem quo. Odit repellendus minima voluptatem aut.'),('Omnis facere est quo omnis et placeat facilis occaecati.',25,'2002-10-25 09:00:00','2007-02-19 11:00:50','87607 Okuneva Highway Apt. 149\nPort Laneyport, NE 27047',7,2,20,6,60,'Asperiores aut aut facilis laudantium voluptas recusandae cum. Error quaerat consequatur delectus quod ut. Minus et nulla dolorum ducimus dolores.'),('Est culpa mollitia nihil eum et.',26,'2012-12-20 16:00:00','2017-03-11 11:54:08','78966 Lukas Branch\nSouth Tysontown, KY 22311-5789',2,2,19,6,22,'Necessitatibus ullam amet eos optio dolores doloremque. Ullam laudantium eos numquam labore. Sit harum consequatur non vitae accusamus.'),('Dicta porro inventore quo dolor rerum tempora.',27,'2011-07-12 00:00:00','2001-11-23 09:28:57','80435 Herta Well\nOceanemouth, IN 58739-9808',6,4,9,5,99,'Vero ducimus cupiditate et veniam dolorem qui qui. Quia recusandae voluptatem sunt rerum. Quia quam facilis modi velit dolor.'),('Vero aut perferendis velit corporis quo sed quo.',28,'2001-07-26 09:00:00','2010-09-24 18:49:16','395 Halvorson Course\nBabychester, WV 73121-4068',0,1,18,6,87,'Ullam aut asperiores saepe. Quos eum pariatur qui fuga. Ut est ratione repellat.'),('Magnam perferendis ad est maiores.',29,'2010-05-14 09:00:00','1994-11-13 01:30:48','57056 Oleta View Suite 204\nSouth Aaliyah, MI 49465-4158',5,5,11,6,75,'Dolor commodi quam aspernatur quia non et. Error aut animi quo non totam occaecati. Sit hic sit aliquam vel.'),('Sit beatae ut non.',30,'1983-02-14 09:00:00','2001-09-15 16:07:20','4148 Sawayn Street\nGlenfurt, UT 86777-6689',2,5,5,6,68,'Assumenda molestiae nihil non eaque doloribus sit officiis. Nihil et molestias sed voluptatem aut cumque.'),('Ratione aut voluptas vitae repellat dolor.',31,'1999-11-07 16:00:00','2013-09-04 11:20:13','019 Arlo Locks\nLake Stanfordshire, TX 04832-6239',5,4,20,6,97,'Id doloribus unde nesciunt corporis ut vitae. Aut corrupti ad iste eos impedit dicta molestias dolorum.'),('Repellendus nostrum possimus sunt aut veniam rem.',32,'1988-08-08 04:00:00','1975-12-02 23:11:14','8727 Gutmann Unions\nPort Josiannetown, WA 40614-8709',8,3,14,6,36,'Aut sunt quo eius ut voluptates qui molestiae velit. Autem vitae cumque qui voluptatem odio alias mollitia. Magnam laboriosam voluptates quae assumenda et. Rem natus libero temporibus sunt molestiae consequuntur nulla.'),('Veritatis ipsam accusamus rerum id mollitia temporibus.',33,'2017-07-17 00:00:00','2017-05-29 23:51:43','281 Hilll Route\nNorth Alayna, GA 43344-5574',1,1,7,3,3,'Mollitia ut dolorem quasi consectetur aut veniam rem. Consequuntur alias ratione laborum dolor aut. Minima praesentium sed eum quia.'),('Voluptates sed doloremque maxime accusamus vel itaque nihil.',34,'1974-10-19 09:00:00','1985-11-29 23:57:12','41212 Keshawn Lodge\nKianaland, MT 60492-9347',5,2,13,6,102,'Ut sit quidem earum soluta est. Molestias quis aut est et. Vel qui iste molestias est.'),('Dolore sapiente nulla enim voluptatibus iusto inventore.',35,'1970-10-12 00:00:00','1995-10-16 20:16:03','634 Quigley Mount\nEast Sagemouth, HI 02625',9,4,14,5,102,'Consequatur dolorum odio eaque quos voluptatem. Ab impedit occaecati molestiae commodi qui.'),('Temporibus delectus quam eos saepe soluta ut sed.',36,'1976-11-11 00:00:00','1970-07-03 20:39:06','0887 Torphy Brook\nEast Majorland, OH 75000',4,1,16,5,12,'Corporis ut voluptatem dolore laudantium adipisci. Et consectetur minima quia nemo hic vero. Eum ut voluptatem quia excepturi ex. Optio perferendis molestiae accusamus similique et.'),('Necessitatibus blanditiis officia recusandae officia rerum cupiditate.',37,'2015-12-16 18:00:00','1970-05-05 08:23:36','90914 McDermott Fords\nPort Chrisbury, MI 82189',3,3,6,6,2,'Est quis nobis eveniet ratione optio quis. Harum officiis impedit et nobis in. Quo voluptatem voluptatem tempore qui delectus dolores sit. Qui consequatur quis corporis voluptatem repellendus numquam maiores.'),('Dolorum perferendis vel quisquam.',38,'1993-04-19 00:00:00','2016-11-19 21:43:59','051 Meaghan Isle\nBlockshire, NJ 63376-7370',5,3,9,3,98,'Deleniti omnis cupiditate suscipit qui. Libero vero excepturi esse cupiditate. Sunt beatae necessitatibus molestiae maxime omnis voluptas quidem.'),('Et maiores reprehenderit fugiat aut nihil.',39,'2019-11-09 08:00:00','2019-11-09 08:00:00','33427 Bosco Islands\nJuliotown, NJ 74912',7,5,7,2,102,'Eius ad fugit dolor alias. Animi nihil dolores culpa porro amet qui. Sed sit optio minima quos. Id aut est laboriosam dolorum natus rerum eaque.'),('At autem omnis dolorem ut nesciunt omnis consectetur.',40,'1990-11-07 15:00:00','1981-07-15 19:01:12','608 Samara Pine Apt. 202\nTamaramouth, WY 65848-9690',3,4,11,6,63,'Sed neque totam fugit ea architecto ex. Illo corrupti maiores laboriosam quaerat distinctio. Temporibus quia vel deleniti voluptas non. Illum molestiae qui explicabo ea quaerat et natus.'),('Commodi at perspiciatis maxime sequi qui ad omnis et.',41,'2009-10-23 09:00:00','1981-01-08 08:50:54','38221 Feeney Shoals Apt. 034\nEast Stacey, GA 85901-5951',4,3,14,6,63,'Mollitia accusamus voluptas corrupti fugit voluptatum et. Ut iusto nihil eius excepturi porro in ullam. Molestiae quia atque sed velit minus nesciunt tenetur. Laudantium quod quo consequatur impedit.'),('Corrupti maxime earum non nobis qui id.',42,'1996-02-28 18:00:00','1974-12-15 01:58:15','5326 Salvatore Brooks Suite 061\nChanelshire, MT 48778-4938',0,4,10,6,82,'Pariatur natus natus laudantium aut explicabo odio officiis provident. Amet ipsam et et numquam consequuntur. Voluptate est accusamus et maxime.'),('Ducimus et provident vel sed et fugit doloremque.',43,'1990-04-29 08:00:00','2000-10-07 00:23:58','1368 Jordi Crest\nEnochmouth, LA 76969',9,2,14,6,85,'Alias eaque eveniet quaerat nihil laboriosam fugiat numquam. Placeat vitae eligendi saepe et voluptatem deleniti ut sit. Error eligendi omnis ut inventore aspernatur quis. Cum ut molestiae tempora adipisci.'),('Id voluptas in qui culpa placeat placeat.',44,'2005-06-23 00:00:00','1972-08-07 02:54:52','685 Koepp Shore\nPort Geoffrey, AZ 97580-7872',5,1,8,5,91,'Rem ut fuga eveniet id. Quam laudantium iusto neque fugiat fuga dolor possimus. In vel unde numquam maxime corporis dolor numquam. Ut ratione omnis quo et tempora.'),('Dolore in nobis modi assumenda ab.',45,'1987-10-22 12:00:00','1972-10-12 21:45:44','07057 Mathilde Plains Apt. 860\nNaderfurt, CA 71246-2857',2,1,16,6,81,'Eum maiores aut ut dolorem sed. Tempore nulla consequatur consequatur molestiae officiis. Suscipit et illo totam omnis in et a. Provident nihil quo quia. Vitae ad explicabo illo eum unde at excepturi error.'),('Voluptatem cumque corporis earum blanditiis.',46,'1985-04-17 08:00:00','2016-05-13 15:20:27','0317 Darian Valley\nChelsiemouth, MN 41256',3,3,9,6,24,'Molestiae et dolore fuga aut nesciunt aut iure. Velit quis omnis laudantium animi commodi sed amet.'),('Excepturi quidem minus exercitationem alias.',47,'2017-07-12 00:00:00','1982-07-20 19:39:10','95343 Miller Courts\nKutchburgh, KY 60105',3,1,6,5,60,'Qui et aperiam maiores doloremque tempora. Vel perspiciatis neque blanditiis et. Neque quasi molestiae aliquid officiis. Ullam optio delectus iure numquam animi ullam.'),('Dolore et id hic praesentium non qui illum.',48,'1993-09-26 16:00:00','2004-02-02 14:36:08','0040 Stracke Loop Suite 840\nStehrburgh, MS 65967-9529',6,5,8,6,12,'Id ut voluptas quia doloribus cupiditate laboriosam. Vitae totam incidunt aspernatur perferendis. Placeat cumque est excepturi enim nostrum sed neque. Ea quod ut exercitationem ex.'),('Ut vel maiores ipsa sunt eius distinctio.',49,'2006-05-24 12:00:00','2001-09-07 01:36:45','439 Tremblay Road\nColefort, WA 58419',3,1,16,6,90,'Distinctio eum aut odit saepe laudantium architecto quae. Accusamus sequi dolor quam qui. Tenetur rem aut et tempora et et.'),('Praesentium in consequatur voluptatibus suscipit laudantium explicabo.',50,'1979-08-21 15:00:00','1998-09-12 03:12:22','132 Botsford Mill\nWolffberg, TX 17687',3,2,20,6,36,'Quidem vel tenetur dolorem asperiores veritatis debitis nihil consequatur. Sed tempore rerum similique minima enim perspiciatis dolor. Aut est aut et voluptates est vel amet.'),('Test123',53,'2019-10-29 22:00:00','2019-11-02 22:00:00','location',0,0,10,6,102,'123123');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup_attendees`
--

LOCK TABLES `meetup_attendees` WRITE;
/*!40000 ALTER TABLE `meetup_attendees` DISABLE KEYS */;
INSERT INTO `meetup_attendees` VALUES (3,1),(6,1),(17,1),(32,1),(47,1),(15,2),(27,2),(43,2),(9,4),(21,6),(48,6),(29,7),(44,7),(4,8),(8,8),(12,8),(35,8),(36,8),(32,9),(35,9),(21,10),(32,11),(43,12),(9,13),(25,13),(29,13),(35,13),(9,14),(38,15),(43,15),(7,16),(8,16),(48,16),(17,18),(44,18),(38,19),(1,20),(3,22),(26,22),(27,22),(19,23),(27,23),(34,23),(35,23),(25,24),(39,24),(43,24),(8,25),(11,25),(32,25),(41,26),(49,27),(9,28),(15,28),(35,28),(39,28),(2,30),(10,30),(44,30),(16,32),(43,32),(50,32),(21,33),(31,33),(6,34),(7,34),(40,35),(25,36),(41,36),(13,37),(34,37),(37,37),(6,38),(30,38),(32,38),(7,39),(36,39),(39,39),(43,39),(35,40),(39,40),(16,41),(48,41),(5,42),(29,44),(1,45),(9,45),(36,45),(3,46),(25,46),(30,46),(45,46),(50,46),(23,48),(38,48),(48,48),(2,49),(26,49),(2,50),(4,51),(5,51),(8,51),(46,51),(12,52),(14,52),(15,52),(39,52),(47,52),(11,53),(39,53),(10,54),(20,54),(25,54),(24,55),(25,55),(34,55),(6,56),(16,56),(27,56),(38,56),(40,57),(31,58),(4,60),(1,61),(18,62),(37,62),(43,62),(23,63),(31,63),(10,64),(13,64),(20,64),(27,64),(5,65),(23,65),(11,67),(12,67),(32,69),(19,70),(43,70),(46,70),(16,71),(40,71),(43,71),(2,72),(15,72),(27,72),(18,74),(47,74),(16,76),(44,76),(49,76),(14,77),(32,77),(35,77),(41,78),(35,79),(39,79),(24,80),(34,81),(34,82),(45,82),(48,82),(11,83),(44,83),(3,84),(18,84),(29,84),(46,84),(17,85),(31,85),(37,85),(23,86),(38,86),(25,87),(49,88),(3,89),(48,89),(12,91),(19,91),(36,91),(35,92),(1,94),(9,94),(31,94),(32,94),(8,95),(29,95),(33,97),(15,99),(41,100),(50,100),(1,102),(3,102),(4,102),(5,102),(6,102),(7,102),(1,103),(3,103);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetup_topic`
--

LOCK TABLES `meetup_topic` WRITE;
/*!40000 ALTER TABLE `meetup_topic` DISABLE KEYS */;
INSERT INTO `meetup_topic` VALUES (15,1),(55,1),(98,1),(100,1),(13,2),(16,2),(46,2),(65,2),(68,2),(56,3),(72,3),(4,4),(14,4),(66,4),(95,4),(22,5),(54,5),(27,6),(39,6),(53,6),(40,7),(53,7),(62,7),(84,8),(65,9),(18,10),(27,10),(53,10),(67,10),(50,11),(59,11),(80,11),(96,11),(12,12),(27,12),(49,12),(83,12),(42,13),(3,14),(30,15),(40,15),(70,15),(69,16),(6,17),(66,17),(17,18),(27,18),(37,18),(65,18),(68,18),(83,18),(88,19),(23,20),(42,20),(64,20),(16,22),(32,22),(58,24),(9,25),(40,26),(81,26),(90,26),(52,27),(66,28),(71,28),(71,29),(89,29),(90,30),(47,31),(55,32),(5,34),(41,34),(52,34),(63,34),(21,35),(59,35),(82,35),(83,35),(87,36),(8,37),(38,37),(75,37),(60,38),(59,39),(18,40),(27,40),(73,40),(38,41),(100,41),(50,42),(15,43),(96,44),(26,45),(18,46),(41,46),(52,46),(42,47),(84,48),(8,49),(39,49);
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
  `content` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,23,4,'2016-06-05 13:05:36'),(2,2,0,'2018-01-21 02:41:25'),(2,52,2,'1986-10-05 15:46:14'),(3,10,5,'2006-12-15 22:36:36'),(3,77,2,'2007-08-28 21:02:38'),(5,53,3,'1989-05-19 09:03:45'),(5,65,0,'2011-03-19 00:08:48'),(5,95,3,'1980-05-20 20:53:46'),(6,30,0,'1995-08-28 19:35:42'),(6,32,4,'1986-02-25 16:15:58'),(7,10,2,'1998-02-17 16:44:50'),(7,27,2,'1989-07-10 05:21:09'),(8,51,1,'1978-06-20 05:38:21'),(8,77,0,'2004-08-07 15:51:14'),(8,93,3,'1977-04-25 04:25:49'),(9,35,4,'1992-09-24 06:08:29'),(9,38,1,'2006-05-06 14:18:04'),(9,46,1,'1985-09-22 01:03:54'),(10,70,1,'1986-05-18 03:08:44'),(11,27,4,'1971-12-06 11:26:44'),(11,100,4,'1995-07-21 21:10:52'),(13,19,3,'2010-04-16 19:19:01'),(14,10,3,'1989-06-21 10:11:47'),(14,51,3,'2006-05-14 19:40:57'),(14,56,3,'1981-10-03 15:15:16'),(15,8,5,'2002-10-13 01:27:44'),(15,11,1,'1974-11-25 10:59:50'),(15,43,5,'1976-08-17 21:24:41'),(16,21,2,'1982-09-11 11:40:33'),(16,33,0,'1975-03-15 18:27:07'),(16,54,4,'1974-07-13 06:38:36'),(16,96,4,'1980-03-11 08:47:39'),(17,31,1,'2008-02-10 00:12:37'),(17,62,2,'2018-04-23 09:43:26'),(18,16,2,'2003-08-29 14:23:09'),(18,36,5,'1991-09-23 07:05:45'),(19,91,4,'1989-05-21 17:10:15'),(20,62,4,'1978-04-07 11:32:25'),(20,69,3,'2007-02-21 05:39:40'),(20,85,1,'2013-09-10 17:06:08'),(21,53,2,'2010-09-14 07:30:27'),(21,78,0,'2015-04-19 09:16:16'),(22,43,1,'2011-11-27 04:31:09'),(23,39,5,'1991-11-17 21:10:22'),(23,75,5,'2015-01-26 05:51:14'),(23,99,0,'1979-09-15 11:48:45'),(24,17,0,'1976-02-02 20:31:28'),(24,27,3,'1978-04-06 10:06:49'),(24,91,4,'2014-12-12 08:29:39'),(24,95,4,'2011-01-01 05:11:49'),(24,100,5,'1989-07-23 15:08:04'),(25,11,2,'2009-08-21 01:23:00'),(25,34,5,'2003-09-01 08:55:53'),(25,59,2,'2004-02-06 11:00:36'),(25,75,1,'1990-04-14 10:37:15'),(25,77,5,'2010-08-04 22:05:34'),(26,16,2,'1982-02-04 19:08:19'),(26,53,2,'1981-08-14 10:13:20'),(26,74,2,'1999-05-07 17:32:20'),(27,26,3,'2016-11-17 11:25:35'),(27,39,0,'1990-07-21 01:38:50'),(27,62,3,'1971-07-05 11:14:39'),(28,18,2,'1980-08-16 14:55:27'),(28,40,2,'1984-07-31 03:33:54'),(28,74,0,'2011-01-30 03:50:24'),(28,87,1,'1993-07-09 15:23:10'),(29,40,2,'2010-03-16 06:56:21'),(29,90,4,'2004-12-11 12:19:21'),(29,91,0,'1970-12-11 03:40:56'),(30,29,1,'1983-11-14 09:49:38'),(30,32,2,'1982-06-15 00:34:33'),(31,100,5,'1973-04-17 11:33:05'),(32,31,1,'2012-04-05 02:10:01'),(32,97,5,'1972-04-15 23:18:27'),(33,61,2,'1993-11-18 05:56:20'),(34,44,4,'1991-08-30 13:35:30'),(34,64,0,'1989-11-20 12:07:33'),(35,57,3,'1999-09-24 05:32:46'),(35,69,3,'2014-05-22 09:21:25'),(36,25,5,'2010-01-08 19:37:58'),(37,58,1,'2014-11-16 08:33:20'),(38,53,3,'2005-02-11 19:21:53'),(38,83,0,'2013-09-30 02:25:27'),(39,64,3,'1999-01-05 11:12:29'),(40,35,0,'1977-06-01 12:51:23'),(40,65,3,'2015-10-04 18:43:57'),(40,81,4,'2009-01-18 22:17:33'),(41,53,5,'2002-08-08 07:40:18'),(41,79,2,'1970-11-21 21:28:19'),(42,47,2,'1970-10-06 00:58:39'),(42,51,1,'1995-09-06 19:39:38'),(42,67,5,'1970-08-24 10:39:44'),(43,94,5,'1995-05-06 10:40:17'),(44,74,0,'2014-09-22 22:36:08'),(45,50,5,'1974-07-11 09:51:13'),(45,66,4,'1977-06-01 15:45:30'),(45,79,4,'1997-04-27 00:41:45'),(46,6,4,'2006-10-29 01:36:47'),(46,26,3,'1974-07-10 11:51:16'),(47,46,5,'2006-04-27 21:08:51'),(47,50,0,'1994-01-21 20:35:09'),(47,64,4,'1991-01-10 12:17:49'),(47,78,2,'1988-02-11 01:59:24'),(48,59,5,'1970-09-06 16:22:56'),(48,86,3,'2013-07-11 17:16:22'),(49,50,5,'2000-06-04 06:33:22'),(50,30,2,'1983-07-06 10:00:45'),(50,57,2,'1983-08-30 13:03:11'),(51,15,4,'1987-10-02 03:15:41'),(52,48,3,'1970-05-15 16:30:38'),(52,62,2,'1999-09-22 06:11:35'),(53,11,1,'2011-07-24 03:00:21'),(53,100,5,'1989-08-23 17:01:49'),(54,27,2,'1976-04-08 13:56:58'),(55,33,3,'1973-04-11 05:02:25'),(55,42,0,'2002-04-01 16:29:04'),(55,51,3,'2011-12-16 03:32:16'),(56,32,2,'2015-02-16 22:05:42'),(56,84,4,'1995-11-20 05:49:38'),(57,16,4,'1981-02-16 15:12:26'),(57,82,1,'1979-11-05 06:37:02'),(57,90,0,'1971-09-08 13:41:27'),(58,28,2,'1978-02-13 19:23:57'),(58,67,4,'2009-09-15 03:07:39'),(59,52,0,'1978-03-22 18:53:31'),(60,66,5,'1997-04-20 16:59:03'),(61,31,1,'2016-08-13 16:14:23'),(61,89,3,'1994-12-22 03:05:31'),(62,14,4,'1989-02-10 13:41:18'),(62,83,4,'1978-08-23 06:36:13'),(63,13,2,'1975-04-12 15:24:50'),(63,15,3,'1987-08-15 02:38:18'),(63,23,3,'2003-04-13 02:49:53'),(63,46,4,'1991-09-07 05:27:44'),(64,15,5,'1999-06-08 23:51:34'),(64,51,5,'2001-02-21 08:47:45'),(65,70,2,'1973-07-31 08:41:22'),(65,90,2,'2005-02-01 16:11:59'),(66,65,2,'2006-04-04 14:59:11'),(66,71,2,'1974-01-29 23:05:25'),(67,48,0,'1970-10-21 03:13:01'),(69,51,5,'1992-08-17 05:34:48'),(70,59,3,'2011-07-22 18:02:44'),(71,4,5,'2018-03-11 00:57:53'),(72,68,0,'2017-11-17 02:12:48'),(72,74,5,'1973-06-11 12:08:37'),(73,18,0,'1993-03-01 13:17:55'),(73,94,5,'1982-06-13 04:41:15'),(74,43,2,'1996-04-20 14:12:06'),(75,5,5,'1975-05-18 20:18:15'),(75,89,3,'2000-11-29 11:42:07'),(76,38,3,'2005-01-23 17:35:55'),(77,66,0,'2017-02-23 01:09:23'),(77,83,5,'2011-01-20 00:01:35'),(79,38,0,'2003-12-21 17:25:28'),(79,62,0,'2018-10-31 11:32:58'),(79,81,2,'1978-09-12 05:41:24'),(80,8,4,'2012-12-22 20:55:33'),(80,58,4,'2016-07-17 21:46:37'),(80,91,1,'2012-07-31 02:20:45'),(81,4,3,'2005-10-02 23:36:19'),(81,12,1,'2018-09-23 10:55:45'),(81,89,5,'2011-03-31 07:01:15'),(83,57,3,'1982-05-10 14:04:45'),(84,34,4,'1994-06-09 21:28:57'),(86,42,2,'1988-05-31 21:29:40'),(86,99,0,'1978-02-16 22:58:57'),(88,45,3,'2000-06-08 08:14:29'),(88,49,2,'1977-11-02 01:06:02'),(88,64,1,'1976-12-12 01:54:20'),(88,70,3,'1979-06-27 05:39:07'),(89,19,5,'2002-05-15 14:02:52'),(89,90,1,'1994-10-20 13:02:49'),(90,2,2,'2019-05-17 15:29:54'),(90,30,5,'1974-04-27 05:45:07'),(90,57,4,'1985-01-25 22:04:43'),(91,71,5,'1983-08-11 22:09:08'),(91,84,4,'2018-09-23 17:17:11'),(91,88,3,'2017-03-11 17:37:59'),(92,41,0,'2012-03-03 07:12:43'),(92,76,2,'2014-10-27 04:23:41'),(92,79,1,'2007-03-31 01:04:14'),(92,88,1,'2018-04-22 23:02:25'),(92,97,3,'2013-06-11 02:22:16'),(94,75,1,'1983-10-21 00:47:30'),(95,42,2,'1996-02-24 10:29:21'),(95,48,3,'1994-05-14 04:02:55'),(95,84,2,'2005-01-16 05:37:00'),(97,24,1,'1980-07-26 03:38:50'),(97,93,4,'2014-02-27 02:02:39'),(98,81,5,'2008-03-03 21:53:54'),(99,13,1,'1972-09-16 21:48:12'),(99,28,2,'1979-11-05 21:04:10'),(99,40,0,'2004-12-19 16:36:31'),(99,77,0,'1996-08-03 10:23:33'),(100,8,1,'2016-01-07 01:24:09'),(100,31,4,'1998-02-01 11:29:18'),(100,43,1,'2010-04-30 02:17:25');
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
) ENGINE=InnoDB AUTO_INCREMENT=5091999 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('admin',2),('user',1);
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
  `date_time_from` datetime DEFAULT NULL,
  `date_time_to` datetime DEFAULT NULL,
  `owner_id` int(10) unsigned NOT NULL,
  `rating_from` float DEFAULT NULL,
  `rating_to` float DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `saved_filter_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES ('Booked',2),('Canceled',3),('In progress',4),('Passed',6),('Scheduled',1),('Terminated',5);
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
  PRIMARY KEY (`uid`),
  UNIQUE KEY `topic_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES ('alias',88),('aliquid',29),('aperiam',19),('aperiamm',26),('architecto',82),('assumenda',83),('at',85),('aut',60),('autem',87),('autt',93),('consequatur',2),('culpa',52),('cumque',72),('deleniti1',74),('deleniti2',11),('deleniti3',14),('distinctio',81),('dolor',96),('dolorem',39),('dolores',9),('doloribus',57),('ea1',47),('ea2',66),('ea3',84),('eaque',23),('enim',49),('enim1',94),('eos',8),('est',12),('et',71),('et1',42),('eum',16),('eum1',98),('eveniet',92),('ex',67),('exercitationem',100),('facere',18),('fuga',91),('fugit',95),('id',78),('illum',73),('impedit',5),('ipsam',6),('itaque',30),('iusto',37),('magni',50),('maiores',54),('minima',20),('minima1',3),('minus',43),('minus1',44),('nemo',56),('nemo1',63),('neque',46),('neque1',68),('nihil',22),('non',77),('non1',27),('non2',59),('non3',62),('nostrum',1),('occaecati',24),('occaecati1',28),('odit',45),('odit1',33),('omnis',48),('omnis1',13),('perspiciatis',36),('perspiciatis1',89),('placeat',17),('possimus',41),('quaerat',40),('quasi',4),('qui',65),('quidem',86),('quis',99),('quis1',35),('quo',21),('quos',38),('rerum',32),('saepe',15),('sed',80),('sequi',64),('similique',25),('sit',58),('sunt',61),('suscipit',55),('tempora',70),('tenetur',34),('unde',31),('ut',76),('veritatis',51),('voluptas',10),('voluptas1',69),('voluptas2',53),('voluptatem',90),('voluptatem1',79),('voluptatem2',75),('voluptatem3',97);
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
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'alec31@example.com','27f1f31e2ed49dd30ccb0700d9ea14958b78c215','Keith','Hermann',1,0,0,1,NULL,'Modi alias et accusantium. Repellat est recusandae aut est qui iure ea. Tempore accusamus et corrupti ipsam in temporibus culpa.','Mullerborough'),(2,'demario.medhurst@example.com','396d764addead2dfff90bc327739a7401c773246','Greta','Krajcik',1,1,1,1,NULL,'Perferendis voluptate quis non debitis temporibus. Quos cum voluptas nihil. Nesciunt libero non voluptas magnam sit.','Herminiaport'),(3,'roderick.ferry@example.net','41c156e54595de5263a80a62d44b61de512e8bd2','Curtis','Hane',1,0,0,1,NULL,'Iusto qui aliquid magni corporis ex non eveniet perferendis. Ipsa id ducimus ut est officia quibusdam architecto. Ipsam ex nihil consequatur sed magni at magnam.','Keeblerport'),(4,'gwalker@example.org','006c8d2f80574038189ccc1706e934c7c5e2c9d1','Angelica','Labadie',1,0,1,1,NULL,'Et rerum voluptas vel. Quas distinctio sunt doloremque cum ipsam non quas culpa. Ut harum rerum est nihil porro rerum. Quidem occaecati praesentium consequuntur perferendis.','Port Brainhaven'),(5,'russel.stanton@example.com','80e0a3574fc0c9757c1b2d5de8996047166e62f3','Nick','West',1,0,0,1,NULL,'Ut consequuntur nemo iusto perspiciatis provident aut quo. Est sapiente dolorem eum saepe. Unde voluptas ipsum ea suscipit quasi veritatis.','Mayaside'),(6,'murazik.johnathon@example.org','fcd7f55db6c5a0b32511972bcf9d8342da76c4f4','Muriel','Stehr',1,0,1,1,NULL,'Suscipit sunt vel praesentium dignissimos quia rerum nemo saepe. In occaecati voluptatem qui doloribus.','Erdmanside'),(7,'imogene.harvey@example.com','4961a619c1bab0a4df98179916fe4e0c4d532f76','Nikolas','Schuster',1,0,0,1,NULL,'Cumque velit aut quibusdam dolor reiciendis impedit similique. Dolorem doloremque aut dicta rerum id minus unde sed. Eius culpa nihil consectetur. Voluptatem sunt alias eligendi nam alias dolorem voluptatibus.','North Emeliabury'),(8,'josue.spencer@example.com','7e1877257f77838d3fc5bc928d762d3b4a5cd71b','Jordane','Towne',1,0,0,1,NULL,'Ea qui culpa iure dolores nobis. Provident itaque voluptatem et adipisci quaerat reprehenderit. Ratione ea officia vitae sapiente sint consequatur. Perspiciatis voluptatem voluptas distinctio culpa quisquam minus.','East Ali'),(9,'guido69@example.com','9bb2a053263b932dab57fbf347f04a26160ac972','Jose','Breitenberg',1,0,0,1,NULL,'Consequuntur esse assumenda excepturi libero ut. Perspiciatis autem qui omnis voluptate laborum voluptates quia. Iste culpa ipsum adipisci ea harum deleniti nemo.','Diannatown'),(10,'agustin.shanahan@example.org','512247f87655b78b58021d572b808a8dcf50ba5b','Allison','Trantow',1,0,1,1,NULL,'Rerum quam saepe aliquam. Eius ipsam laudantium cupiditate. Est nostrum quasi dolore architecto veritatis labore quisquam. Ut qui tempore deserunt fugiat occaecati autem.','Gislasonside'),(11,'west.kiel@example.org','7869ba88cd2215482f14b69ca389912f67bd6acc','Bella','Hand',1,1,0,1,NULL,'In quibusdam excepturi mollitia suscipit dolor voluptatibus. Ut saepe iure facere delectus excepturi. Facilis assumenda a dolore nobis architecto beatae. Doloremque eligendi assumenda voluptas tenetur aut consequatur.','Stokesshire'),(12,'mina.schinner@example.org','a8c9678fb2c057639882792a966b61239bfd315b','Percival','Gerlach',1,0,0,1,NULL,'Ipsa quae delectus voluptatem sunt eligendi. Est occaecati in sequi nobis. Animi reprehenderit vel suscipit sint. Dolorem aut neque asperiores commodi.','Kendallborough'),(13,'bobby49@example.com','0b3826aff642be591fe038665c4123fc44373900','Alford','Nitzsche',1,0,0,1,NULL,'Sit dolorem quod a eos omnis asperiores. Tenetur consequatur eaque quaerat. Eum quam optio in tempora itaque quas natus. Quibusdam unde repudiandae et a.','New Myron'),(14,'dustin18@example.net','257319d2eeeac68d2b9a433ce1d90a68fe8c6531','Karli','Jacobs',1,0,1,1,NULL,'Quo doloremque dolor tempore magnam nisi. Praesentium dolorem et reprehenderit aut distinctio quas. Ratione recusandae ipsum saepe explicabo nam.','South Wendell'),(15,'lbalistreri@example.net','4e0c72a492c01132ffdc34dfb40b283c64aa1e7f','Salvador','Blick',1,0,0,1,NULL,'Voluptatem non quasi ut iure eos qui. Enim quas ab iste. Esse doloribus deserunt sint alias sit dolorem quis.','Barbaraborough'),(16,'jacobson.demarcus@example.net','ea7b553734919a0501c12cf31cf24ca4faf812e2','Myrtie','Parisian',1,0,1,1,NULL,'Praesentium maxime incidunt commodi veritatis est. Rerum ullam maiores architecto officiis doloribus omnis quis nam. Enim quo minima eveniet non voluptas. Sed optio impedit iure sed voluptas quisquam et. Molestiae consequuntur eum dolores doloremque earum est facilis.','Danikaborough'),(17,'shanna.wilkinson@example.com','7233e841f34feb535e68991e640e8f400eded446','Darwin','Bartell',1,0,1,1,NULL,'Quod voluptas provident omnis ratione cum est eos. Tempora voluptatem perspiciatis cumque occaecati nulla voluptatem impedit. Voluptate porro numquam nisi nesciunt. Distinctio nostrum consectetur autem non corporis vitae.','North Alyssonside'),(18,'ajohns@example.org','c7654bb5009838e51de591d18302391512fd9f4e','Alicia','Gibson',1,0,0,1,NULL,'Delectus sed neque assumenda mollitia dolores nihil in quam. Minima ut eos mollitia. Ipsam consequuntur amet consequatur et expedita.','Hoppefurt'),(19,'karolann.grant@example.com','25582e71d8e68b6fa36c9a8ca2607962ec7046ad','Josue','Padberg',1,0,0,1,NULL,'Tempora dolores maiores enim voluptatibus quas numquam. Accusamus cum quia laboriosam beatae dolorem corrupti ut. Eos similique rerum numquam quod libero odit consequuntur explicabo. Odit et omnis et consequatur aut quam.','North Joannyland'),(20,'stehr.josie@example.net','0d47ac92c7bca08f814466e1d09d95024540c77c','Jolie','Green',1,0,1,1,NULL,'Esse rerum in veniam illum consequuntur beatae. Dolores sed et vel. Voluptas officia quia ut voluptatem repellat dolor. Odio quo facilis consectetur ipsum.','Port Flofurt'),(21,'jaskolski.brandt@example.net','a6b4be9f80c0cfbfd0c836d91061c7a7c7faf9b0','Tito','Cremin',1,0,1,1,NULL,'Est eos et voluptatibus itaque fugiat quia porro. Rem deleniti officiis alias at distinctio quae. Assumenda itaque autem eum sit. Reiciendis velit non deserunt ab.','East Dylan'),(22,'adeline.willms@example.org','bb4fc5203a2a848447601e63b9be2394c396edfe','Hallie','Pfeffer',1,0,1,1,NULL,'Sunt veritatis perferendis ut alias occaecati voluptate inventore nihil. Sunt minus porro dolorum omnis eum. Tenetur nihil aut doloribus ratione voluptas. Dolore voluptatum commodi vel consequatur maiores enim repellendus.','Yasmeenstad'),(23,'trever.steuber@example.net','719af13c6c38d660e0783c9da139d0631871d98a','Damon','Turner',1,0,0,1,NULL,'Ea dolor distinctio dolores tenetur doloribus. Molestiae et omnis quasi voluptatem mollitia nihil beatae. Nulla natus soluta voluptatem et delectus dolorem expedita.','East Loubury'),(24,'hattie38@example.net','c25dcfe036d8d319a58b7f5a013e4d5a5f1a51c0','Torrance','Jaskolski',1,0,0,1,NULL,'Voluptatem soluta nesciunt laudantium necessitatibus velit porro natus. Maxime ut necessitatibus modi earum omnis dolor. Illo magni tempora soluta qui dolores accusamus consectetur.','New Terrance'),(25,'vprohaska@example.org','df5e285620f00b2280d750b1bc88903071942fb4','Liliana','Ankunding',1,0,1,1,NULL,'Et animi repellendus consequatur porro et. Optio exercitationem quidem et dolore. Dolorum quia est sed illo neque porro debitis. Voluptas in nobis accusamus aut necessitatibus perferendis suscipit similique.','Port Margretstad'),(26,'billy30@example.com','3b6e4c22de4480b1c8e5666736f677653b961130','Therese','Bednar',1,0,0,1,NULL,'Quam aliquam ut minus dignissimos. Nisi consequatur fuga reiciendis. Magnam velit doloribus eos autem est eaque iure.','Port Felicia'),(27,'sfadel@example.com','e9eef4ed1deeeac1825e9d4ad980d79cb629fbbd','Raymond','Crist',1,0,1,1,NULL,'Dolorem enim nisi fugit ut dolor quae quam. Itaque qui officiis id velit velit dignissimos. Ea quasi quaerat ad numquam eligendi dolor dignissimos. Repellat praesentium dolorem sit.','Lake Gerdahaven'),(28,'uherzog@example.net','0ba99a83cee175b7d8ba33c1b307d20585b2c700','Zoe','Fay',1,0,0,1,NULL,'Est eligendi ea maxime aut. Sit dolores praesentium sunt officiis saepe id ratione. Eveniet praesentium in cupiditate nemo labore et ab. Quaerat pariatur consequatur similique natus nam aut id. Facere praesentium quia quibusdam dolores quisquam numquam quis et.','North Eulabury'),(29,'rath.cydney@example.com','16f008f0a0622695146d4b047c72369d64de82f0','Elyse','Schiller',1,0,0,1,NULL,'Accusantium beatae eaque et. Voluptas ex numquam consequatur doloremque vitae. Illum voluptatum expedita qui et soluta quod enim. Dolore ratione in ea omnis eum id.','Swaniawskibury'),(30,'sgusikowski@example.com','a3d83ccf92a4d6e3dfe592c1b8a6363c84f19e25','Rosella','Kuhic',1,0,0,1,NULL,'Accusamus ut fuga illo iusto sequi aut ex. Vitae nihil molestiae hic est. Ut qui dignissimos facilis tempora quas.','Wintheiserton'),(31,'mzboncak@example.net','dfd03b2d7ead28ca2e3bd81f0311fa04d75bb1f7','Camron','Olson',1,0,1,1,NULL,'Maiores sed veniam non repudiandae dolore. Sit est qui dolorem non. Similique ullam nesciunt debitis. Adipisci blanditiis cum aliquam praesentium nostrum ipsam nobis.','Lake Jarretmouth'),(32,'shanel04@example.net','97e3fe588a1246d82c106640f51112f906e5766e','Elaina','Miller',1,0,1,1,NULL,'Debitis veritatis quos sequi. Laboriosam voluptatibus sit veniam vel sed ex sed et. Cupiditate nobis est tenetur sapiente eveniet. Ut temporibus fugit beatae minima culpa eveniet eum.','East Ozella'),(33,'kcollier@example.org','5d4d0063d7b02187bdee621e1ed070f828cc78c7','Lane','Erdman',1,0,0,1,NULL,'Odit illum inventore ad error. Rerum omnis enim autem perferendis qui et nihil.','Port Isaiahmouth'),(34,'rigoberto60@example.net','1e6f0b5932d491efa1fa353e501fb10c413910ef','Earnestine','Wilderman',1,0,0,1,NULL,'Nulla et earum animi ullam numquam. Nobis nihil a sapiente. Aperiam dolorum facilis aut ut debitis qui.','Wizafurt'),(35,'vstroman@example.com','a0bfb98cfde6e6c51686658be748302442c45cba','Marcelo','Emmerich',1,0,1,1,NULL,'Corrupti autem quaerat optio omnis accusamus consequatur. Eaque facere impedit occaecati voluptatum tempora enim qui. Et magni eum aspernatur tempore. Commodi non a eum asperiores neque.','New Manley'),(36,'luettgen.jameson@example.net','47dd53fef3efe63dfe4d8601afae9072b6b4e3d4','Meaghan','Brakus',1,0,0,1,NULL,'Sapiente porro omnis aperiam id. Voluptatem molestiae veritatis fugit commodi.','Kesslerland'),(37,'raleigh38@example.org','022d8411a5a6058e32b1c09c8de6d124d21bc2d2','Christy','Grimes',1,0,0,1,NULL,'Et sed sapiente magnam asperiores dicta. Alias quos sint corrupti perspiciatis. In possimus impedit at eum et eos. Ipsa deserunt sed labore.','Rempelbury'),(38,'tierra44@example.org','58faad8fb8ea049fa6228f34978f9991ee7debc8','Glen','Goyette',1,0,0,1,NULL,'Cupiditate a saepe asperiores quia quis aut. Eius est voluptates dolorem. Fugiat iste unde ducimus. Minima vel quia adipisci sunt ut laborum.','Gretaborough'),(39,'lonie46@example.org','fcdc2cadd65c534ea842a996ad11902d9ab6510d','Miracle','Kautzer',1,1,0,1,NULL,'Voluptas et quia accusantium aliquid dolorum beatae. Earum nihil assumenda quis vero. Sed odio commodi temporibus libero dolorum. Vero fugiat voluptas et voluptatem omnis. Corporis eos in earum voluptas.','Port Tevin'),(40,'sbahringer@example.com','420e4e9dab1598c2a4a0a3fade7eacf28af92579','Declan','Walter',1,0,0,1,NULL,'Sit natus saepe molestiae voluptatem earum amet. Ea quaerat at voluptas id. Rerum ratione laborum eligendi dolores maxime. Rerum aspernatur aut enim et.','New Antwanborough'),(41,'okuneva.jazmyn@example.org','2fe2af0d4469929660f460fe337e4a6000d44a99','Yasmin','Brakus',1,0,0,1,NULL,'Velit saepe maiores aut ea cumque voluptas illum. Officia nobis est sed aut quasi error aut. Vel error quia cumque non similique ipsam culpa non.','Dangelobury'),(42,'jbailey@example.org','4bfae71d4b576352256c9cc7ea165ba2eda5d9d7','Santiago','Smitham',1,0,0,1,NULL,'Dolorem est autem omnis ipsum occaecati id optio. Tenetur ut cumque sit beatae voluptatem officiis quos. Aspernatur et temporibus similique tempora. Similique est aut non debitis voluptatem. Deserunt tenetur consequatur commodi qui aliquam ea iusto.','Deckowfurt'),(43,'klein.raul@example.net','d6a74d9b21a4090646b18c335cc4b9d8f3770c92','Bernice','Blanda',1,0,0,1,NULL,'Cumque magni vel optio ipsum dolorem exercitationem. Quae enim et optio unde. Consectetur reiciendis illo quae ut.','Hirtheberg'),(44,'dhowell@example.com','b8c461e3cc404ddb96dc003707907938b83eef0c','Desmond','Gleichner',1,0,0,1,NULL,'Iure ut est iure ab non. Et dicta qui aut eaque quibusdam deserunt eaque. Maiores pariatur recusandae ab soluta quisquam saepe unde. Voluptatem quas dolor nobis deleniti rerum est hic.','Marielashire'),(45,'helmer49@example.org','17611347e0ffe5071e55b6906ee5563c5a68544f','Antonetta','Rutherford',1,0,1,1,NULL,'A quis doloribus autem. Pariatur nisi in ut dolores ut. Beatae est distinctio reiciendis eius ipsam quos labore provident. Vero eos cupiditate eveniet sunt quas et. Eum nihil at dolorum ut.','East Clifford'),(46,'cummings.blake@example.org','e62220093c688849995c8c65143db7a4bca5631b','Cesar','Schimmel',1,0,0,1,NULL,'Vel qui fuga iure. Accusantium rerum provident quae velit voluptate qui dolore.','Romainestad'),(47,'lwisoky@example.org','9749bbd34224ace18dc0d8635bfafc1a6ab1afa9','Trisha','Gutmann',1,1,1,1,NULL,'Aliquam facilis eaque ut corrupti repudiandae. Harum ea ab omnis nemo quasi eius. Assumenda eaque expedita nostrum non molestiae tenetur.','North Laneville'),(48,'qdickens@example.net','d8d90283f7881347694cea57384028a8ebbe2edb','Orpha','Nikolaus',1,0,0,1,NULL,'Doloremque ducimus delectus nihil nisi. Enim distinctio quisquam sed velit placeat voluptatem qui. Quaerat excepturi iure illo deleniti ducimus eum.','Gunnartown'),(49,'xokuneva@example.com','9afbe568d2a7fe9d6709c0fcaecf208b6d273fd5','Lawrence','Kautzer',1,0,0,1,NULL,'Labore doloremque magnam dolorum illum amet labore. Vero dolorem voluptatibus consequatur fuga. Deserunt voluptatem sint sint.','East Cletusside'),(50,'satterfield.sydnie@example.com','08e337f53c374e1d5a1e9df0c4a4239cde402a48','Ricardo','Emmerich',1,0,0,1,NULL,'Dolores ut nobis error enim iste officiis. Nostrum aut incidunt dolores placeat. Reiciendis ut fuga harum sit deserunt ut numquam enim.','East Alfonso'),(51,'macejkovic.viviane@example.org','50a11b237161bdb18e1ee55ddd15968c41b44885','Marina','Paucek',1,0,1,1,NULL,'Ut dolores dolor eius optio minus. Voluptatem rerum adipisci dolorem quo. Numquam recusandae est reiciendis veritatis vel impedit dolores nihil.','Boganmouth'),(52,'yo\'keefe@example.org','a3052a591dacefb4d20a428ce099f66e543f2965','Elinor','Fadel',1,0,1,1,NULL,'Et in iste laudantium aut occaecati reiciendis. Voluptatem error quam blanditiis itaque voluptatibus. Et magnam temporibus et dolorem alias. Sed magni voluptatem nobis labore.','Lake Maximoberg'),(53,'msteuber@example.net','1bf0a55a44aeae7ee404233c2b6199a36844bed3','Jodie','O\'Keefe',1,0,0,1,NULL,'Facilis esse repudiandae voluptatem est voluptatum qui vero. Sit aut illo dolor quia. Et maiores voluptates dolores rerum laboriosam. Quae laboriosam qui est nam dolores.','Medhurstborough'),(54,'jacinto.kuhic@example.net','4d0d3e15558002b319e10926917200fedc3a65aa','Kaylah','Wyman',1,0,0,1,NULL,'Cumque tempora sapiente nisi sint eum. Incidunt quam repellendus aut provident doloribus ut ut. Facere aut amet eum neque rerum doloribus quibusdam. Ipsam libero veritatis iure ducimus aut voluptas vero.','Karenview'),(55,'chance.gutmann@example.org','29d8893e070b1a5c77ad442eb88547e4d0a422c0','Taryn','Rohan',1,0,1,1,NULL,'Ad pariatur at mollitia et. Nemo omnis accusantium aperiam expedita officiis rem. Iure rerum aut molestias neque officiis eaque. Dicta dolorem non tenetur a.','Schaeferton'),(56,'hayes.timmothy@example.net','b9e0fe26c66d53a010a95eebaa1dab05ddba7f68','Winfield','Renner',1,1,0,1,NULL,'Ducimus amet est illum et sed in temporibus. Alias minima voluptatem magni recusandae. Ut in vel consequuntur atque quaerat voluptatem.','Orntown'),(57,'rutherford.silas@example.net','b42848a3571ad526a4e76e87bbd2cd633ef602fa','Leilani','Aufderhar',1,1,0,1,NULL,'Suscipit mollitia quasi et illum. Repellat minus libero et. Libero enim nihil ex enim beatae quidem. Id autem at repellendus odit voluptate.','Mullerchester'),(58,'spinka.hermann@example.net','ab417de4866279996b457a7b49152a61a4d9c008','Leonardo','Hegmann',1,0,0,1,NULL,'Consequatur iure similique cupiditate et sunt maxime adipisci. Et dolor corrupti architecto possimus voluptas. Iusto consequatur beatae sunt autem quae est. Dolor eos autem odio ut.','East Hollystad'),(59,'mueller.jacquelyn@example.com','7279911b00dea1d9edeff8c63a41dc431b3b49d0','Bo','Bogan',1,0,1,1,NULL,'Dolore non et rem. Quibusdam voluptatem dolor expedita iste quia repudiandae. Quisquam non provident nulla totam ipsam quia.','Reingerhaven'),(60,'ndickens@example.org','c3487cd1765959514824d4f1284a660d3797f798','Aliya','Sawayn',1,0,0,1,NULL,'Ut vel voluptate autem error sit est. Quis dolorem sapiente et odio occaecati aut. Sapiente consectetur ducimus nihil est et similique tempore.','Ewaldborough'),(61,'myah26@example.com','c568610b3596d082d94345d714542935887ff311','Ursula','Gusikowski',1,0,1,1,NULL,'Veniam voluptatem aut animi sint possimus. Iure facilis quasi suscipit omnis ipsa expedita illo. Velit quasi vero eum autem.','Vitoland'),(62,'laila.ward@example.com','23cfad8534e4aca7b2062e4da7f65183f7c15cbe','Rudy','White',1,0,0,1,NULL,'Enim eaque quaerat dolor illum tempora non. Illum et velit iusto quis. Ut voluptatem dolores reiciendis architecto aliquam quia. Debitis voluptatum facilis voluptas est quia dolore labore. Nihil aut culpa beatae.','South Benton'),(63,'murray.vincenza@example.net','f86c2d3a87c16247d431e8d79f6f26e86bc2069d','Cassidy','Bartoletti',1,0,0,1,NULL,'Molestiae expedita velit labore pariatur velit error iure. Eos nesciunt molestiae at repellat cupiditate atque. Cupiditate et vitae et blanditiis. Quae dolores maiores est vero quia ipsam. Reiciendis culpa sint omnis ea odio non.','North Garthtown'),(64,'hortense79@example.com','fa0f2c3689e62094e6670f97394b36e657c0d788','Meaghan','Bailey',1,0,0,1,NULL,'Voluptatem voluptatem nihil nesciunt autem modi natus. Id reprehenderit et sapiente dolores deserunt debitis porro. Velit eligendi laborum qui reprehenderit rerum quasi qui.','Londonside'),(65,'lillie.senger@example.org','b29ed9f19cc0dcfed8ccc64c82712e13ea52a9e7','Mellie','West',1,0,0,1,NULL,'Et dicta molestias et qui esse sint minima. Sint labore dolores possimus adipisci architecto occaecati ipsum velit. Ut sed aliquid accusamus laborum voluptatem aut exercitationem.','Catalinahaven'),(66,'schinner.mikel@example.org','0bef0aacc5496a550d670a8e38f5bb5ca746ac3a','Gideon','Jerde',1,0,1,1,NULL,'Quo architecto dolore quibusdam aut soluta facere fugit omnis. Aut rerum qui aliquam omnis nihil dignissimos. Illo porro provident odit placeat.','Domenicoview'),(67,'qrice@example.org','b6e756f27d499b847958db26f9de89414c501ace','Kory','Rosenbaum',1,0,0,1,NULL,'Ea rem dolores sapiente esse ab. Quas alias asperiores soluta ducimus et quas. A voluptatum consequuntur architecto at et deleniti. Quo voluptates molestias inventore unde et.','Lake Brycenborough'),(68,'susanna.toy@example.org','c1b310ebcc254494119ca69f6119b1db46e0e16b','Diego','Ruecker',1,1,0,1,NULL,'Quis unde distinctio velit consectetur molestiae nesciunt. Rerum dolores modi doloribus explicabo officiis fuga. Omnis possimus officia placeat nobis quam. Molestias ut dolores eos.','Huelsview'),(69,'ramon.conroy@example.org','abdbdeed1bd7162d4aff53b836d917d9e9e012d1','Jacky','O\'Keefe',1,0,1,1,NULL,'Reprehenderit harum deleniti necessitatibus maxime. Et est eos sint qui quam. Aut qui non accusantium aut.','East Margarettland'),(70,'larkin.brett@example.com','e272ea8e5eaa08951cd460eac5dc0a30121453ad','Willa','Kovacek',1,0,0,1,NULL,'Placeat voluptatem consequatur voluptatem excepturi. Voluptas asperiores consequatur accusantium quis. Et placeat eos cum quaerat facilis quia. Sapiente ut omnis itaque sequi itaque.','Federicomouth'),(71,'qlehner@example.org','f1d753e537840da8a7dc956a17c5f93f3f09822b','Marc','Ratke',1,0,0,1,NULL,'Fugiat et architecto distinctio numquam excepturi molestiae. Ipsam tenetur qui ratione est.','South Terrancefort'),(72,'kiehn.royce@example.com','caf2d7d26daa9163c3061562cdac855681a5dce8','Vickie','Towne',1,0,0,1,NULL,'Ut corrupti et dolor perspiciatis qui. Voluptate omnis totam nemo natus tempore voluptatem. Est et sed iure neque dolor deserunt. Quasi pariatur et rem dolorem doloribus omnis totam.','Wildermanland'),(73,'berniece.heller@example.com','80a1b53a839b787c86a971f4dcfd28e21d37e172','Johnathon','Macejkovic',1,0,1,1,NULL,'Sint omnis dolore sed cumque. Molestiae tempore est qui repellendus dolores. Maiores ullam at velit et quo in.','Russelview'),(74,'emily03@example.org','ec66cbf03d749ed58c4cef4caa8a336aaaec47da','Leta','Kassulke',1,0,1,1,NULL,'A delectus hic accusantium amet maiores. Sit laudantium neque voluptatum voluptatem ipsam delectus fugiat.','Port Nathentown'),(75,'ward.davis@example.org','0733ee1d80eec403a3754042c4cd9026440348b0','Sterling','Rippin',1,0,1,1,NULL,'Minima qui dolorem dolor sapiente assumenda similique. Quidem iure quia error animi. Cupiditate et et voluptatibus est doloribus ut voluptatum. Beatae error pariatur nam odit vel.','East Gaetanofurt'),(76,'rodriguez.gia@example.com','c097670ac2042bbfdf1a96678b9a6a150dca60bb','Darrion','O\'Kon',1,0,1,1,NULL,'Eum culpa ex similique et. Labore iure aut repellat mollitia quidem labore culpa. Nostrum inventore dolor iure omnis totam in.','North Bryce'),(77,'lennie.huel@example.org','992dab331879be7a41773542fe7c8baff999b568','Sheridan','Rowe',1,0,1,1,NULL,'Iure earum dolores modi nihil est. Sequi et qui perferendis assumenda necessitatibus. Fugiat molestias doloribus et in est et. Ut facilis vero exercitationem et rem.','Kinghaven'),(78,'hhoppe@example.net','e6c3f9f4a0493937b42028b5ccb0b1797d9ed9e4','Joana','Fritsch',1,0,1,1,NULL,'Quaerat numquam dolor quia quis. Qui omnis sint ad culpa non aperiam quam ut. Quia aut facere aut. Velit quasi architecto suscipit consequatur aut.','Rohanfort'),(79,'cathryn29@example.net','a2c6105a8da5396a7bd19ba88d3159c94ecb756b','Bell','Conroy',1,0,1,1,NULL,'Alias laborum quo inventore aut unde rerum ipsam. Adipisci voluptatem deserunt tempore eligendi sit.','Jewellmouth'),(80,'hilpert.monica@example.com','7d84733a5e68e5e0671ff7a0bea84ccb4f3bf078','Alva','Vandervort',1,0,1,1,NULL,'Voluptas tempore et voluptas et. In sit porro qui et animi temporibus velit aut. Voluptatum ut est commodi ea. Ea assumenda maxime aut vero architecto. Ipsa laudantium doloremque voluptatem repudiandae praesentium.','Hodkiewiczstad'),(81,'williamson.asha@example.org','a45bab4b86892ecb3a95128e8a2b843a05e03d4a','Seth','Moen',1,0,0,1,NULL,'Molestiae consequatur aspernatur eaque ipsam molestiae. Cupiditate quia et sit aut veniam fugiat sed. Animi maxime quisquam natus ipsum voluptatibus. Quaerat aperiam temporibus reiciendis dolorem voluptatum dolores ipsam.','New Marshall'),(82,'remington.bartoletti@example.net','db64af5b068766737e640aef6389627ed3849d85','Laura','Kunde',1,0,0,1,NULL,'Qui dolor eveniet est itaque. Aliquam iure ea fugiat et iusto et officia repellat. Qui ab sit voluptatum minus voluptatem quis ut. Incidunt molestiae ut voluptas quia corporis. Sunt tenetur neque qui dignissimos voluptatum et aut.','Hintzburgh'),(83,'roslyn.ryan@example.com','9fc0d52e92515677d1008cab4e1367237277fb6b','Gaston','Schuster',1,1,1,1,NULL,'Nam tenetur consequatur ipsum quisquam fuga. Blanditiis et sit ad quibusdam aut temporibus. Atque maxime dolorum aut. Amet perferendis est enim nihil ad sed.','South Randy'),(84,'muller.alf@example.com','1eae415fe0aa6733d1094917a951f9a9f2e819f4','Rozella','Cummings',1,0,0,1,NULL,'Pariatur nemo distinctio sapiente quaerat sint vero totam. Optio est reprehenderit adipisci nemo expedita optio. Dicta et dolores eveniet ut.','Baileymouth'),(85,'rosemary.marquardt@example.org','387c6dfcd1d906e8a6cbfef03ab6500006412598','Darrin','Wolf',1,1,1,1,NULL,'Esse quis velit ratione non temporibus. Suscipit et nemo alias qui doloribus quia. Ratione aspernatur ullam sit eaque unde.','Daphneyfurt'),(86,'hilbert50@example.com','77469fe9071a901566a2e87f8eead6b5bd3667ff','Sibyl','Spencer',1,0,0,1,NULL,'Eum illo et temporibus unde cupiditate ex dignissimos. Id et accusantium officiis sunt voluptatem sit.','Rennershire'),(87,'mae.bayer@example.com','2ba91f4667e0aee4d23ee2ae8a236e40f8cf4d82','Norwood','Feeney',1,1,0,1,NULL,'Est consequatur consequatur doloribus quas atque reprehenderit. Fugit facilis eaque voluptas doloribus qui dolorum commodi. Laboriosam quam sint ea molestias eveniet sit. Aut quasi enim facilis quia officia.','North Gaetano'),(88,'ejacobson@example.net','bdd75c165e4b2fe81f27afcf416560baa1a11a2e','Frances','Glover',1,0,0,1,NULL,'Mollitia nam amet culpa suscipit. Repellendus qui accusantium libero eveniet. Minima alias quas et magni iusto blanditiis temporibus.','East Jaydeport'),(89,'uheidenreich@example.org','1adb6afaff2d0c59180f070a146315062ee880de','Yessenia','Franecki',1,0,1,1,NULL,'Aut libero provident enim quia. Sint voluptatem quod aut suscipit fugit id. Sapiente minima quis sit. Repudiandae totam eos autem harum repudiandae qui velit.','West Callietown'),(90,'miller.langworth@example.net','a172b02c78b605ba4b18694976f1a948e48071e3','Beaulah','Dickens',1,0,0,1,NULL,'At inventore occaecati deserunt quo adipisci. Aspernatur dolorum voluptas deleniti dicta. Molestiae similique porro nam numquam quasi debitis. Inventore aliquam ut debitis et.','Oratown'),(91,'abbott.aglae@example.net','2edfa78d6d8ec6489be74b3e2bfa916edad1d4f2','Dora','Armstrong',1,0,1,1,NULL,'Ipsam illum sit ex ad explicabo provident voluptas. Accusantium sed quaerat vitae. Delectus est delectus reprehenderit aut.','Lake Karelle'),(92,'bhauck@example.com','3a8fa3311c284bd7ccd02751ecbcd5f23c05083d','Brant','Bosco',1,0,1,1,NULL,'Fuga et necessitatibus optio. Sed eos natus aut distinctio quos autem autem assumenda. Omnis est eos sed vitae dolore.','Cullenhaven'),(93,'hickle.yvonne@example.com','7b1ddc34eec1848e190e277fe1d5ec8a9d2afd79','Avery','Wintheiser',1,0,1,1,NULL,'Est aut veritatis nesciunt. Ut et minus necessitatibus. Laboriosam repellendus voluptatem quia. Nostrum rerum explicabo et facilis atque.','Mertzside'),(94,'xmacejkovic@example.com','5bcfd8f11189c0fa16c746abec4df189bde76f93','Ryley','Trantow',1,0,1,1,NULL,'Nihil est eum et explicabo labore earum voluptatum. Ab rerum molestiae aut dolore. Molestiae numquam placeat ut nemo. Consectetur labore recusandae voluptatibus expedita repudiandae distinctio in.','East Madysonborough'),(95,'egibson@example.com','cadff2ff9fd92cfe8b7d1702c3d352e87c0448d3','Gerald','Lindgren',1,1,0,1,NULL,'Recusandae consequuntur consequatur consequuntur cum. Veritatis voluptatem architecto et quia eum aperiam nobis.','Port Josiane'),(96,'vicky.hegmann@example.net','bf24e6c3f2c308bde7af1550a1e94658e875d6ca','Serena','Johns',1,0,1,1,NULL,'Id officia dolores fugit consequatur eaque dicta ipsam. Quas repellendus neque dolor qui aspernatur et perferendis adipisci. Blanditiis ad magnam excepturi rem occaecati aliquam.','Port Glennachester'),(97,'laurence39@example.org','931180af2d276981e496c27167eff0c9d75a8ea3','Erick','Jacobi',1,0,1,1,NULL,'Vitae possimus ut aut incidunt. Eaque sint voluptatem velit est excepturi debitis. Harum quia porro tempore placeat tempora est.','New Brianafurt'),(98,'curt.marks@example.org','c4c0a39896937d6bcfd219905c39bccbbff4283a','Wilber','Schuster',1,0,1,1,NULL,'Qui ut quibusdam architecto asperiores assumenda. Incidunt occaecati omnis corrupti iure perferendis repellendus itaque. Neque laudantium voluptas aperiam inventore. Quam in aliquam molestias dolorum.','North Kian'),(99,'sward@example.com','1b430b40805f12171c9d754215ec05b33ae9ff78','Jaqueline','Treutel',1,0,0,1,NULL,'Sed est nemo magni est. Quos eligendi consequatur commodi earum fugiat perspiciatis nobis alias.','Hyattland'),(100,'vivien33@example.org','b90953205019f7f7015932ed03a595243fc0b5ed','Green','Murazik',1,0,0,1,NULL,'Facere esse et unde alias. Quo autem magnam fugiat. Vero suscipit aut quia autem nihil atque dolor molestiae.','East Jedidiahtown'),(101,'999@gmail.com','$2a$10$WRfHi37xDPoJ2bMAPd4tRuXgttpOs.VpPiN2f3fHHvTOxYd./TrcS','999','999',1,0,1,1,NULL,NULL,NULL),(102,'555@gmail.com','$2a$10$95WEGHwW7b78SY1NKyussekEwa2dq27C6KYH6HT8qQ85eVVc9E4Gi','555','Nguyen',1,0,1,1,NULL,NULL,NULL),(103,'mr.sn5.kma@gmail.com','$2a$10$r0WWIrcQZ/Psi83oeoZmhODgtdNAgJVSIx9Z5nWwhg4k2qz4zVR8a','San','Nguyen',1,0,1,1,NULL,NULL,NULL),(104,'33555@gmail.com','$2a$10$H2E/lscmbcDfs2ot6y/RqOm5SnBNtCREISAUTMYs3Kl2o0i1cPZwO','555','Nguyen',1,0,1,1,NULL,NULL,NULL),(105,'22222@gmail.com','$2a$10$fQAFoNkOlAHHZ/HjsYqGMe09u7dKpLQdPwkbn6SQsXLGJWnAma0/2','San','Nguyen',1,0,1,1,NULL,NULL,NULL),(106,'admin@gmail.com','$2a$10$Z.HMPf/8My1RXowB2IK/B.cKYIqCw9fdUT4cqoBEfOZzVTuIJC3SO','Admin','Admin',1,0,1,2,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_language`
--

LOCK TABLES `user_language` WRITE;
/*!40000 ALTER TABLE `user_language` DISABLE KEYS */;
INSERT INTO `user_language` VALUES (2,3),(7,3),(35,3),(46,3),(48,3),(49,3),(57,3),(58,3),(59,3),(86,3),(5,4),(9,4),(41,4),(49,4),(58,4),(60,4),(65,4),(77,4),(8,5),(9,5),(10,5),(12,5),(29,5),(35,5),(41,5),(65,5),(78,5),(90,5),(96,5),(19,6),(47,6),(55,6),(84,6),(1,7),(25,7),(30,7),(39,7),(52,7),(57,7),(61,7),(64,7),(67,7),(70,7),(75,7),(100,7),(7,8),(11,8),(19,8),(20,8),(25,8),(37,8),(56,8),(58,8),(68,8),(77,8),(81,8),(92,8),(94,8),(99,8),(12,9),(16,9),(19,9),(33,9),(40,9),(54,9),(58,9),(67,9),(72,9),(80,9),(92,9),(97,9),(98,9),(9,10),(11,10),(26,10),(35,10),(39,10),(54,10),(64,10),(67,10),(84,10),(89,10);
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

-- Dump completed on 2019-11-05 18:32:46
