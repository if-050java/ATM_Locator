CREATE DATABASE IF NOT EXISTS `atmlocator` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `atmlocator`;
-- MySQL dump 10.13  Distrib 5.6.21, for Win64 (x86_64)
--
-- Host: localhost    Database: atmlocator
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `atm`
--

DROP TABLE IF EXISTS `atm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atm` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `bank_id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_ATM_BANKS` (`bank_id`),
  CONSTRAINT `FK_ATM_BANKS` FOREIGN KEY (`bank_id`) REFERENCES `banks` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atm`
--

LOCK TABLES `atm` WRITE;
/*!40000 ALTER TABLE `atm` DISABLE KEYS */;
/*!40000 ALTER TABLE `atm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atmnetworks`
--

DROP TABLE IF EXISTS `atmnetworks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atmnetworks` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atmnetworks`
--

LOCK TABLES `atmnetworks` WRITE;
/*!40000 ALTER TABLE `atmnetworks` DISABLE KEYS */;
INSERT INTO `atmnetworks` VALUES (-1,'Unassigned');
INSERT INTO `atmnetworks` VALUES (1,'Приватбанк');
INSERT INTO `atmnetworks` VALUES (2,'Атмосфера');
INSERT INTO `atmnetworks` VALUES (3,'Euronet');
INSERT INTO `atmnetworks` VALUES (4,'Радiус');
INSERT INTO `atmnetworks` VALUES (5,'УкрКард');
/*!40000 ALTER TABLE `atmnetworks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banks`
--

DROP TABLE IF EXISTS `banks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banks` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `iconAtm` varchar(255) DEFAULT NULL,
  `iconOffice` varchar(255) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `webSite` varchar(255) DEFAULT NULL,
  `network_id` int(11) DEFAULT '-1',
  `mfoCode` int(6) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `mfoCode_uniq` (`mfoCode`),
  KEY `FK_BANKS_ATMNETWORKS` (`network_id`),
  CONSTRAINT `FK_BANKS_ATMNETWORKS` FOREIGN KEY (`network_id`) REFERENCES `atmnetworks` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banks`
--

LOCK TABLES `banks` WRITE;
/*!40000 ALTER TABLE `banks` DISABLE KEYS */;
INSERT INTO `banks` VALUES (189,NULL,NULL,'2014-12-02 15:24:33',NULL,'ЕКСПРЕС-БАНК',NULL,-1,322959);
INSERT INTO `banks` VALUES (190,NULL,NULL,'2014-12-02 15:24:33',NULL,'КЛІРИНГОВИЙ ДІМ',NULL,-1,300647);
INSERT INTO `banks` VALUES (191,'','','2014-12-02 16:34:13','','УКООПСПІЛКА','',4,322625);
INSERT INTO `banks` VALUES (192,'','','2014-12-02 16:41:42','','УКРГАЗБАНК','',5,320478);
INSERT INTO `banks` VALUES (193,'','','2014-12-02 16:25:57','','ІНДУСТРІАЛБАНК','',4,313849);
INSERT INTO `banks` VALUES (194,NULL,NULL,'2014-12-02 15:24:33',NULL,'НОВИЙ',NULL,-1,305062);
INSERT INTO `banks` VALUES (195,'','','2014-12-02 16:13:10','','Південний','',2,328209);
INSERT INTO `banks` VALUES (196,NULL,NULL,'2014-12-02 15:24:33',NULL,'АРТЕМ-БАНК',NULL,-1,300885);
INSERT INTO `banks` VALUES (197,'','','2014-12-02 16:36:49','','БАНК БОГУСЛАВ','',5,380322);
INSERT INTO `banks` VALUES (198,'','','2014-12-02 16:37:00','','БАНК ВЕЛЕС','',5,322799);
INSERT INTO `banks` VALUES (199,NULL,NULL,'2014-12-02 15:24:33',NULL,'НАЦІОНАЛЬНІ ІНВЕСТИЦІЇ',NULL,-1,300498);
INSERT INTO `banks` VALUES (200,'','','2014-12-02 16:33:44','','БАНК РЕНЕСАНС КАПІТАЛ','',4,380010);
INSERT INTO `banks` VALUES (201,NULL,NULL,'2014-12-02 15:24:33',NULL,'ФІНАНСИ ТА КРЕДИТ',NULL,-1,300131);
INSERT INTO `banks` VALUES (202,'','','2014-12-02 16:04:06','','БМ БАНК','',2,380913);
INSERT INTO `banks` VALUES (203,'','','2014-12-02 16:09:48','','Дельта Банк','',2,380236);
INSERT INTO `banks` VALUES (204,NULL,NULL,'2014-12-02 15:24:33',NULL,'ЗЛАТОБАНК',NULL,-1,380612);
INSERT INTO `banks` VALUES (205,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІМЕКСБАНК',NULL,-1,328384);
INSERT INTO `banks` VALUES (206,NULL,NULL,'2014-12-02 15:24:34',NULL,'СОЮЗ',NULL,-1,380515);
INSERT INTO `banks` VALUES (207,NULL,NULL,'2014-12-02 15:24:34',NULL,'ТК КРЕДИТ',NULL,-1,322830);
INSERT INTO `banks` VALUES (208,NULL,NULL,'2014-12-02 15:24:34',NULL,'КІБ',NULL,-1,322540);
INSERT INTO `banks` VALUES (209,'','','2014-12-02 16:26:58','','МетаБанк','',4,313582);
INSERT INTO `banks` VALUES (210,'','','2014-12-02 16:27:13','','МІСТО БАНК','',4,328760);
INSERT INTO `banks` VALUES (211,NULL,NULL,'2014-12-02 15:24:34',NULL,'НК БАНК',NULL,-1,322432);
INSERT INTO `banks` VALUES (212,'','','2014-12-02 16:12:39','','ОТП Банк','',2,300528);
INSERT INTO `banks` VALUES (213,NULL,NULL,'2014-12-02 15:24:34',NULL,'Ощадбанк',NULL,-1,300465);
INSERT INTO `banks` VALUES (214,'','','2014-12-02 16:13:26','','ПІРЕУС БАНК МКБ','',2,300658);
INSERT INTO `banks` VALUES (215,'','','2014-12-02 16:29:53','','ПРОКРЕДИТ БАНК','',4,320984);
INSERT INTO `banks` VALUES (216,NULL,NULL,'2014-12-02 15:24:34',NULL,'Український банк реконструкції та розвитку',NULL,-1,380883);
INSERT INTO `banks` VALUES (217,NULL,NULL,'2014-12-02 15:24:34',NULL,'Райффайзен Банк Аваль',NULL,-1,300335);
INSERT INTO `banks` VALUES (218,NULL,NULL,'2014-12-02 15:24:34',NULL,'РЕГІОН-БАНК',NULL,-1,351254);
INSERT INTO `banks` VALUES (219,NULL,NULL,'2014-12-02 15:24:34',NULL,'РОДОВІД БАНК',NULL,-1,321712);
INSERT INTO `banks` VALUES (220,'','','2014-12-02 16:16:22','','СБЕРБАНК РОСІЇ','',2,320627);
INSERT INTO `banks` VALUES (221,NULL,NULL,'2014-12-02 15:24:34',NULL,'СП БАНК',NULL,-1,304706);
INSERT INTO `banks` VALUES (222,'','','2014-12-02 16:34:00','','ТАСКОМБАНК','',4,339500);
INSERT INTO `banks` VALUES (223,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРБУДІНВЕСТБАНК',NULL,-1,380377);
INSERT INTO `banks` VALUES (224,NULL,NULL,'2014-12-02 15:24:34',NULL,'Укрексімбанк',NULL,-1,322313);
INSERT INTO `banks` VALUES (225,'','','2014-12-02 15:48:30','','УКРСИББАНК','',3,351005);
INSERT INTO `banks` VALUES (226,NULL,NULL,'2014-12-02 15:24:34',NULL,'ФОРТУНА-БАНК',NULL,-1,300904);
INSERT INTO `banks` VALUES (227,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНГ Банк Україна',NULL,-1,300539);
INSERT INTO `banks` VALUES (228,NULL,NULL,'2014-12-02 15:24:34',NULL,'А - БАНК',NULL,-1,307770);
INSERT INTO `banks` VALUES (229,'','','2014-12-02 16:33:04','','РАДАБАНК','',4,306500);
INSERT INTO `banks` VALUES (230,NULL,NULL,'2014-12-02 15:24:34',NULL,'СТОЛИЧНИЙ',NULL,-1,397133);
INSERT INTO `banks` VALUES (231,'','','2014-12-02 16:01:20','','АВАНТ-БАНК','',2,380708);
INSERT INTO `banks` VALUES (232,NULL,NULL,'2014-12-02 15:24:34',NULL,'Агрокомбанк',NULL,-1,322302);
INSERT INTO `banks` VALUES (233,NULL,NULL,'2014-12-02 15:24:34',NULL,'АРКАДА',NULL,-1,322335);
INSERT INTO `banks` VALUES (234,'','','2014-12-02 16:26:08','','КАПІТАЛ','',4,334828);
INSERT INTO `banks` VALUES (235,'','','2014-12-02 16:11:41','','КИЇВ','',2,322498);
INSERT INTO `banks` VALUES (236,NULL,NULL,'2014-12-02 15:24:34',NULL,'КОНКОРД',NULL,-1,307350);
INSERT INTO `banks` VALUES (237,'','','2014-12-02 16:26:33','','Львів','',4,325268);
INSERT INTO `banks` VALUES (238,'','','2014-12-02 16:41:01','','Траст-капітал','',5,380106);
INSERT INTO `banks` VALUES (239,NULL,NULL,'2014-12-02 15:24:34',NULL,'АЛЬПАРІ БАНК',NULL,-1,380894);
INSERT INTO `banks` VALUES (240,'','','2014-12-02 16:02:11','','АЛЬФА-БАНК','',2,300346);
INSERT INTO `banks` VALUES (241,NULL,NULL,'2014-12-02 15:24:34',NULL,'АПЕКС-БАНК',NULL,-1,380720);
INSERT INTO `banks` VALUES (242,NULL,NULL,'2014-12-02 15:24:34',NULL,'АСВІО БАНК',NULL,-1,353489);
INSERT INTO `banks` VALUES (243,NULL,NULL,'2014-12-02 15:24:34',NULL,'АСТРА БАНК',NULL,-1,380548);
INSERT INTO `banks` VALUES (244,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК АВАНГАРД',NULL,-1,380946);
INSERT INTO `banks` VALUES (245,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК АЛЬЯНС',NULL,-1,300119);
INSERT INTO `banks` VALUES (246,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК ВОСТОК',NULL,-1,307123);
INSERT INTO `banks` VALUES (247,'','','2014-12-02 16:25:03','','ГРАНТ','',4,351607);
INSERT INTO `banks` VALUES (248,'','','2014-12-02 16:02:34','','БАНК ІНВЕСТИЦІЙ ТА ЗАОЩАДЖЕНЬ','',2,380281);
INSERT INTO `banks` VALUES (249,'','','2014-12-02 16:21:35','','БАНК КАМБІО','',4,380399);
INSERT INTO `banks` VALUES (250,'','','2014-12-02 16:39:12','','КИЇВСЬКА РУСЬ','',5,319092);
INSERT INTO `banks` VALUES (251,'','','2014-12-02 16:37:21','','Контракт','',5,322465);
INSERT INTO `banks` VALUES (252,'','','2014-12-02 16:22:49','','БАНК КРЕДИТ ДНІПРО','',4,305749);
INSERT INTO `banks` VALUES (253,'','','2014-12-02 16:02:47','','БАНК МИХАЙЛІВСЬКИЙ','',2,380935);
INSERT INTO `banks` VALUES (254,'','','2014-12-02 16:37:51','','БАНК НАЦІОНАЛЬНИЙ КРЕДИТ','',5,320702);
INSERT INTO `banks` VALUES (255,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК ПЕТРОКОММЕРЦ-УКРАЇНА',NULL,-1,300120);
INSERT INTO `banks` VALUES (256,NULL,NULL,'2014-12-02 15:24:34',NULL,'ПОРТАЛ',NULL,-1,339016);
INSERT INTO `banks` VALUES (257,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК СІЧ',NULL,-1,380816);
INSERT INTO `banks` VALUES (258,NULL,NULL,'2014-12-02 15:24:34',NULL,'СОФІЙСЬКИЙ',NULL,-1,380861);
INSERT INTO `banks` VALUES (259,NULL,NULL,'2014-12-02 15:24:34',NULL,'ТРАСТ',NULL,-1,380474);
INSERT INTO `banks` VALUES (260,'','','2014-12-02 16:03:30','','Український капітал','',2,320371);
INSERT INTO `banks` VALUES (261,'','','2014-12-02 15:47:17','','БАНК ФОРВАРД','',3,380418);
INSERT INTO `banks` VALUES (262,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЮНІСОН',NULL,-1,380902);
INSERT INTO `banks` VALUES (263,'','','2014-12-02 16:21:14','','БАНК 3/4','',4,380645);
INSERT INTO `banks` VALUES (264,'','','2014-12-02 16:03:52','','БГ БАНК','',2,320995);
INSERT INTO `banks` VALUES (265,'','','2014-12-02 16:09:00','','БТА БАНК','',2,321723);
INSERT INTO `banks` VALUES (266,'','','2014-12-02 16:47:11','','ВБР','',4,380719);
INSERT INTO `banks` VALUES (267,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВЕКТОР БАНК',NULL,-1,339038);
INSERT INTO `banks` VALUES (268,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВЕРНУМ БАНК',NULL,-1,380689);
INSERT INTO `banks` VALUES (269,'','','2014-12-02 16:09:19','','ВіЕйБі Банк','',2,380537);
INSERT INTO `banks` VALUES (270,'','','2014-12-02 16:09:34','','ВіЕс Банк','',2,325213);
INSERT INTO `banks` VALUES (271,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВТБ БАНК',NULL,-1,321767);
INSERT INTO `banks` VALUES (272,NULL,NULL,'2014-12-02 15:24:34',NULL,'Держзембанк',NULL,-1,380968);
INSERT INTO `banks` VALUES (273,'','','2014-12-02 16:10:11','','ДІАМАНТБАНК','',2,320854);
INSERT INTO `banks` VALUES (274,NULL,NULL,'2014-12-02 15:24:34',NULL,'ДІВІ БАНК',NULL,-1,380827);
INSERT INTO `banks` VALUES (275,NULL,NULL,'2014-12-02 15:24:34',NULL,'Дойче Банк ДБУ',NULL,-1,380731);
INSERT INTO `banks` VALUES (276,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЕНЕРГОБАНК',NULL,-1,300272);
INSERT INTO `banks` VALUES (277,'','','2014-12-02 16:25:21','','ЄВРОПРОМБАНК','',4,377090);
INSERT INTO `banks` VALUES (278,'','','2014-12-02 16:10:57','','Ідея Банк','',2,336310);
INSERT INTO `banks` VALUES (279,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНВЕСТИЦІЙНО-ТРАСТОВИЙ БАНК',NULL,-1,380957);
INSERT INTO `banks` VALUES (280,'','','2014-12-02 16:38:49','','Інтеграл-банк','',5,320735);
INSERT INTO `banks` VALUES (281,NULL,NULL,'2014-12-02 15:24:34',NULL,'ГЕФЕСТ',NULL,-1,377120);
INSERT INTO `banks` VALUES (282,NULL,NULL,'2014-12-02 15:24:34',NULL,'ГЛОБУС',NULL,-1,380526);
INSERT INTO `banks` VALUES (283,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЄВРОБАНК',NULL,-1,380355);
INSERT INTO `banks` VALUES (284,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЗЕМЕЛЬНИЙ КАПІТАЛ',NULL,-1,305880);
INSERT INTO `banks` VALUES (285,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНВЕСТБАНК',NULL,-1,328210);
INSERT INTO `banks` VALUES (286,NULL,NULL,'2014-12-02 15:24:34',NULL,'НАДРА',NULL,-1,380764);
INSERT INTO `banks` VALUES (287,NULL,NULL,'2014-12-02 15:24:34',NULL,'ПРЕМІУМ',NULL,-1,339555);
INSERT INTO `banks` VALUES (288,'','','2014-12-02 15:45:50','','ПРИВАТБАНК','www.privatbank.ua',1,305299);
INSERT INTO `banks` VALUES (289,NULL,NULL,'2014-12-02 15:24:34',NULL,'СТАНДАРТ',NULL,-1,380690);
INSERT INTO `banks` VALUES (290,'','','2014-12-02 16:42:00','','ФІНАНСОВА ІНІЦІАТИВА','',5,380054);
INSERT INTO `banks` VALUES (291,NULL,NULL,'2014-12-02 15:24:34',NULL,'ФІНАНСОВИЙ ПАРТНЕР',NULL,-1,380872);
INSERT INTO `banks` VALUES (292,NULL,NULL,'2014-12-02 15:24:34',NULL,'ХРЕЩАТИК',NULL,-1,300670);
INSERT INTO `banks` VALUES (293,NULL,NULL,'2014-12-02 15:24:34',NULL,'Центр',NULL,-1,380742);
INSERT INTO `banks` VALUES (294,NULL,NULL,'2014-12-02 15:24:34',NULL,'КЛАСИКБАНК',NULL,-1,306704);
INSERT INTO `banks` VALUES (295,'','','2014-12-02 16:39:50','','КОМІНВЕСТБАНК','',5,312248);
INSERT INTO `banks` VALUES (296,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТ ЄВРОПА БАНК',NULL,-1,380366);
INSERT INTO `banks` VALUES (297,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТ ОПТИМА БАНК',NULL,-1,380571);
INSERT INTO `banks` VALUES (298,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТВЕСТ БАНК',NULL,-1,380441);
INSERT INTO `banks` VALUES (299,'','','2014-12-02 16:11:57','','Кредитпромбанк','',2,300863);
INSERT INTO `banks` VALUES (300,'','','2014-12-02 16:12:10','','КРЕДІ АГРІКОЛЬ БАНК','',2,300614);
INSERT INTO `banks` VALUES (301,'','','2014-12-02 16:26:20','','КРЕДОБАНК','',4,325912);
INSERT INTO `banks` VALUES (302,NULL,NULL,'2014-12-02 15:24:34',NULL,'КСГ БАНК',NULL,-1,380292);
INSERT INTO `banks` VALUES (303,'','','2014-12-02 16:12:24','','ЛЕГБАНК','',2,300056);
INSERT INTO `banks` VALUES (304,'','','2014-12-02 16:27:25','','МАРФІН БАНК','',4,328168);
INSERT INTO `banks` VALUES (305,NULL,NULL,'2014-12-02 15:24:34',NULL,'МЕГАБАНК',NULL,-1,351629);
INSERT INTO `banks` VALUES (306,NULL,NULL,'2014-12-02 15:24:34',NULL,'МІБ',NULL,-1,380582);
INSERT INTO `banks` VALUES (307,'','','2014-12-02 16:40:03','','МІСЬКИЙ КОМЕРЦІЙНИЙ БАНК','',5,339339);
INSERT INTO `banks` VALUES (308,'','','2014-12-02 16:28:11','','МОТОР-БАНК','',4,313009);
INSERT INTO `banks` VALUES (309,NULL,NULL,'2014-12-02 15:24:34',NULL,'НЕОС БАНК',NULL,-1,320940);
INSERT INTO `banks` VALUES (310,'','','2014-12-02 16:40:19','','ОКСІ БАНК','',5,325990);
INSERT INTO `banks` VALUES (311,NULL,NULL,'2014-12-02 15:24:34',NULL,'ОМЕГА БАНК',NULL,-1,300164);
INSERT INTO `banks` VALUES (312,'','','2014-12-02 16:28:27','','ПЕРШИЙ ІНВЕСТИЦІЙНИЙ БАНК','',4,300506);
INSERT INTO `banks` VALUES (313,'','','2014-12-02 16:40:31','','Полтава-банк','',5,331489);
INSERT INTO `banks` VALUES (314,NULL,NULL,'2014-12-02 15:24:34',NULL,'Промінвестбанк',NULL,-1,300012);
INSERT INTO `banks` VALUES (315,'','','2014-12-02 16:32:52','','ПРОФІН БАНК','',4,334594);
INSERT INTO `banks` VALUES (316,'','','2014-12-02 16:45:10','','ПтБ','',2,380388);
INSERT INTO `banks` VALUES (317,'','','2014-12-02 16:28:59','','ПУМБ','',4,334851);
INSERT INTO `banks` VALUES (318,'','','2014-12-02 16:32:36','','ПФБ','',4,331768);
INSERT INTO `banks` VALUES (319,'','','2014-12-02 16:40:42','','РАДИКАЛ БАНК','',5,319111);
INSERT INTO `banks` VALUES (320,NULL,NULL,'2014-12-02 15:24:34',NULL,'РОЗРАХУНКОВИЙ ЦЕНТР',NULL,-1,344443);
INSERT INTO `banks` VALUES (321,NULL,NULL,'2014-12-02 15:24:34',NULL,'СЕБ КОРПОРАТИВНИЙ БАНК',NULL,-1,380797);
INSERT INTO `banks` VALUES (322,NULL,NULL,'2014-12-02 15:24:34',NULL,'СІТІБАНК',NULL,-1,300584);
INSERT INTO `banks` VALUES (323,'','','2014-12-02 16:34:38','','УКРБІЗНЕСБАНК','',4,334969);
INSERT INTO `banks` VALUES (324,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРГАЗПРОМБАНК',NULL,-1,320843);
INSERT INTO `banks` VALUES (325,'','','2014-12-02 16:16:40','','УКРІНБАНК','',2,300142);
INSERT INTO `banks` VALUES (326,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРКОМУНБАНК',NULL,-1,304988);
INSERT INTO `banks` VALUES (327,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРСОЦБАНК',NULL,-1,300023);
INSERT INTO `banks` VALUES (328,NULL,NULL,'2014-12-02 15:24:34',NULL,'УНІВЕРСАЛ БАНК',NULL,-1,322001);
INSERT INTO `banks` VALUES (329,NULL,NULL,'2014-12-02 15:24:35',NULL,'УНІКОМБАНК',NULL,-1,335902);
INSERT INTO `banks` VALUES (330,'','','2014-12-02 16:41:25','','УПБ','',5,300205);
INSERT INTO `banks` VALUES (331,NULL,NULL,'2014-12-02 15:24:35',NULL,'ФІНАНС БАНК',NULL,-1,300896);
INSERT INTO `banks` VALUES (332,'','','2014-12-02 16:35:23','','ФІНБАНК','',4,328685);
INSERT INTO `banks` VALUES (333,NULL,NULL,'2014-12-02 15:24:35',NULL,'ФІНЕКСБАНК',NULL,-1,380311);
INSERT INTO `banks` VALUES (334,'','','2014-12-02 16:42:13','','ЮНЕКС БАНК','',5,322539);
INSERT INTO `banks` VALUES (335,NULL,NULL,'2014-12-02 15:24:35',NULL,'ЮСБ БАНК',NULL,-1,305987);
INSERT INTO `banks` VALUES (336,'','','2014-12-02 16:15:41','','ПРАВЕКС-БАНК','',2,380838);
INSERT INTO `banks` VALUES (337,NULL,NULL,'2014-12-02 15:24:35',NULL,'ПОЛІКОМБАН',NULL,-1,353100);
INSERT INTO `banks` VALUES (338,'','','2014-12-02 16:19:04','','АКОРДБАНК','',4,380634);
INSERT INTO `banks` VALUES (339,NULL,NULL,'2014-12-02 15:24:35',NULL,'СМАРТБАНК',NULL,-1,380786);
INSERT INTO `banks` VALUES (340,'','','2014-12-02 16:16:54','','ФІДОБАНК','',2,300175);
INSERT INTO `banks` VALUES (341,'','','2014-12-02 16:23:27','','БАНК ФАМІЛЬНИЙ','',4,334840);
/*!40000 ALTER TABLE `banks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `timeCreated` datetime DEFAULT NULL,
  `atm_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_COMMENTS_ATM` (`atm_id`),
  KEY `FK_COMMENTS_USERS` (`user_id`),
  CONSTRAINT `FK_COMMENTS_ATM` FOREIGN KEY (`atm_id`) REFERENCES `atm` (`Id`),
  CONSTRAINT `FK_COMMENTS_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favorites` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `timeCreated` datetime DEFAULT NULL,
  `atm_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_FAVORITES_ATM` (`atm_id`),
  KEY `FK_FAVORITES_USERS` (`user_id`),
  CONSTRAINT `FK_FAVORITES_ATM` FOREIGN KEY (`atm_id`) REFERENCES `atm` (`Id`),
  CONSTRAINT `FK_FAVORITES_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parser_params`
--

DROP TABLE IF EXISTS `parser_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parser_params` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `parameter` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `parser_id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PP_PARSERS` (`parser_id`),
  CONSTRAINT `FK_PP_PARSERS` FOREIGN KEY (`parser_id`) REFERENCES `parsers` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parser_params`
--

LOCK TABLES `parser_params` WRITE;
/*!40000 ALTER TABLE `parser_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `parser_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parsers`
--

DROP TABLE IF EXISTS `parsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parsers` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `lastRun` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `bank_id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PARSERS_BANKS` (`bank_id`),
  CONSTRAINT `FK_PARSERS_BANKS` FOREIGN KEY (`bank_id`) REFERENCES `banks` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parsers`
--

LOCK TABLES `parsers` WRITE;
/*!40000 ALTER TABLE `parsers` DISABLE KEYS */;
/*!40000 ALTER TABLE `parsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Role for administrators','ADMIN');
INSERT INTO `roles` VALUES (2,'Role for registered users','USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FK_USER_ROLE_ROLES` (`roles_id`),
  CONSTRAINT `FK_USER_ROLE_ROLES` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK_USER_ROLE_USERS` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1);
INSERT INTO `user_role` VALUES (3,1);
INSERT INTO `user_role` VALUES (1,2);
INSERT INTO `user_role` VALUES (2,2);
INSERT INTO `user_role` VALUES (3,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `rolename` varchar(100) NOT NULL,
  UNIQUE KEY `userid` (`user_id`),
  CONSTRAINT `users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN');
INSERT INTO `user_roles` VALUES (2,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` int(1) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastLoging` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'defaultUserAvatar.jpg','admin@mail.com',1,'admin','c4ca4238a0b923820dcc509a6f75849b','2014-12-02 15:45:31');
INSERT INTO `users` VALUES (2,'defaultUserAvatar.jpg','user@mail.com',1,'user','c4ca4238a0b923820dcc509a6f75849b',NULL);
INSERT INTO `users` VALUES (3,'defaultUserAvatar.jpg','olavin@ukr.net',1,'Oleg','c4ca4238a0b923820dcc509a6f75849b',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-02 17:27:42
