CREATE DATABASE  IF NOT EXISTS `protokoll` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `protokoll`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: protokoll
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `nutzer`
--

DROP TABLE IF EXISTS `nutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nutzer` (
  `benutzername` varchar(45) NOT NULL,
  `vorname` varchar(45) NOT NULL,
  `nachname` varchar(45) NOT NULL,
  `email` varchar(120) NOT NULL,
  `aktiv` tinyint NOT NULL,
  PRIMARY KEY (`benutzername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nutzer`
--

LOCK TABLES `nutzer` WRITE;
/*!40000 ALTER TABLE `nutzer` DISABLE KEYS */;
INSERT INTO `nutzer` VALUES ('deaktiviert','De','Aktiviert','de.aktiviert@okv.de',0),('musterfrau','Maxi','Musterfrau','maxi.musterfrau@okv.de',1),('mustermann','Max','Mustermann','max.mustermann@okv.de',1),('trautwein','Alex','Trautwein','alex.trautwein@okv.de',1),('trautweinw','William','Trautwein','william.trautwein@okv.de',1);
/*!40000 ALTER TABLE `nutzer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protokoll`
--

DROP TABLE IF EXISTS `protokoll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `protokoll` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bezeichnung` varchar(120) NOT NULL,
  `datum` date NOT NULL,
  `protokollant` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `protokollant_idx` (`protokollant`),
  CONSTRAINT `protokollant` FOREIGN KEY (`protokollant`) REFERENCES `nutzer` (`benutzername`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protokoll`
--

LOCK TABLES `protokoll` WRITE;
/*!40000 ALTER TABLE `protokoll` DISABLE KEYS */;
INSERT INTO `protokoll` VALUES (1,'Erstes Protokoll','2022-08-20','trautweinw'),(2,'Zweites Protokoll','2022-07-20','musterfrau'),(3,'Leeres Protokoll','2022-06-05','mustermann');
/*!40000 ALTER TABLE `protokoll` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `benutzername` varchar(45) NOT NULL,
  PRIMARY KEY (`benutzername`),
  CONSTRAINT `benutzername` FOREIGN KEY (`benutzername`) REFERENCES `nutzer` (`benutzername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('trautwein'),('trautweinw');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anwesend`
--

DROP TABLE IF EXISTS `anwesend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anwesend` (
  `benutzername` varchar(45) NOT NULL,
  `id` int NOT NULL,
  `aussagen` mediumtext,
  `nummer` int DEFAULT NULL,
  PRIMARY KEY (`benutzername`,`id`),
  KEY `id_idx` (`id`),
  CONSTRAINT `benutzernameA` FOREIGN KEY (`benutzername`) REFERENCES `nutzer` (`benutzername`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `protokoll` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anwesend`
--

LOCK TABLES `anwesend` WRITE;
/*!40000 ALTER TABLE `anwesend` DISABLE KEYS */;
INSERT INTO `anwesend` VALUES ('musterfrau',1,'Musterfrau bekommt auch eine Testtext',3),('musterfrau',2,'Dies ist das von Musterfrau erstellte Protokoll',1),('mustermann',1,'Hier ist ein Mustertext vom Mustermann',2),('mustermann',2,NULL,2),('trautweinw',1,'Hier ist ein kleiner Text zum Testen welcher zu William Trautwein (trautweinw) zugeordnet ist.',1);
/*!40000 ALTER TABLE `anwesend` ENABLE KEYS */;
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-01 11:06:40
