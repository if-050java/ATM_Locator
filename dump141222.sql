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
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atm`
--

LOCK TABLES `atm` WRITE;
/*!40000 ALTER TABLE `atm` DISABLE KEYS */;
INSERT INTO `atm` VALUES (33,'м. Івано-Франківськ, вул. Січових стрільців, 15',48.919509,24.709152,NULL,NULL,1,1,288),(34,'м. Івано-Франківськ, вул. Андрія Мельника, 2',48.918838,24.714501,NULL,NULL,1,1,288),(35,'м. Івано-Франківськ, вул. Гетьмана Мазепи, 38',48.919353,24.702641,NULL,NULL,1,1,288),(36,'м. Івано-Франківськ, вул. Галицька, 62',48.933872,24.706236,NULL,NULL,1,0,288),(37,'м. Івано-Франківськ, вул. Євгена Коновальця, 262Б',48.88854,24.709298,NULL,NULL,1,1,288),(38,'м. Івано-Франківськ, вул. Степана Бандери, 79',48.907239,24.715876,NULL,NULL,1,1,288),(39,'м. Івано-Франківськ, вул. Чорновола, 35',48.916831,24.705988,NULL,NULL,1,0,288),(40,'м. Івано-Франківськ, вул. Шашкевича, 4',48.919999,24.711851,NULL,NULL,1,1,288),(41,'м. Івано-Франківськ, вул. Юліана Целевича, 11',48.9444,24.703,NULL,'',1,1,213),(42,'м. Івано-Франківськ, вул. Галицька, 91',48.932,24.7072,NULL,NULL,1,0,213),(47,'м. Івано-Франківськ, вул. Мазепи, 175',48.90261899999999,24.682700000000068,NULL,NULL,1,0,217),(51,'м. Івано-Франківськ, вул. Галицька, 162',48.9448537,24.693742199999974,NULL,NULL,1,1,217),(52,'вул. Вовчинецька, 225А',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(53,'вул. Незалежності, 19',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(54,'вул. Грушевського, 2',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(55,'вул. Грушевського, 11',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(56,'вул. Січових Стрільців, 13',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(57,'вул. Дністровська, 28',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(58,'вул. Незалежності, 34-а',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(59,'вул. Тичини, 1',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(60,'вул. Шопена, 9',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(61,'вул. П.Орлика, 7',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(62,'Північний бульвар, 2а',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(63,'вул. Грюнвальська, 11',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(64,'вул. Сахарова, 32',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(65,'вул. Привокзальна,1',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(66,'вул. Довга, 98/17',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(67,'вул. Коновальця, 132',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(68,'вул. Незалежності, 19',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(69,'вул.Галицька, 145А',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(70,'вул. Тролейбусна, 2',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(71,'вул. Ребета, 3',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(72,'вул. Незалежності, 162',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(73,'вул. Хоткевича, 48',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(74,'Мазепи 168В',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(75,'вул. Вовчинецька, 225',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(76,'Івано-Франківськ, вул.Миколайчука, 2',NULL,NULL,'2014-12-18 19:51:48',NULL,0,0,278),(77,'  м. Івано-Франківськ, вул. Незалежності, 19',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(78,'  м. Івано-Франківськ, вул. Грушевського, 11',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(79,'  м. Івано-Франківськ, вул. Шопена, 9/1',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(80,'  м. Івано-Франківськ, вул. П.Орлика, 7',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(81,'  м.Івано-Франківськ, вул.Тролейбусна, 2',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(82,'  м. Івано-Франківськ, вул. Польова, 6',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(83,'  Івано-Франківська область, м. Тисмениця, вул. Галицька, 21в',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(84,'  Івано-Франківська область, м. Галич, вул. Коновальця, 19',NULL,NULL,'2014-12-18 19:51:48',NULL,0,2,278),(85,'вул.Галицька, 27 м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,2,301),(86,'вул.Будівельників, 18а м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,1,301),(87,'вул.Грушевського, 2 м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,1,301),(88,'вул.Незалежності, 20 м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,1,301),(89,'вул.Миколайчука, 2 м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,1,301),(90,'вул.Галицька, 112д м.Івано-Франківськ',NULL,NULL,'2014-12-22 19:20:12',NULL,0,1,301),(91,'вул.Шевченка, 18 м.Надвірна',NULL,NULL,'2014-12-22 19:20:12',NULL,0,2,301),(92,'пр.Л.Українки, 1 м.Калуш',NULL,NULL,'2014-12-22 19:20:12',NULL,0,2,301),(93,'вул.Січових Стрільців, 4а м.Бурштин',NULL,NULL,'2014-12-22 19:20:12',NULL,0,2,301),(94,'пл.Роксолани, 6 м.Рогатин',NULL,NULL,'2014-12-22 19:20:12',NULL,0,2,301);
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
INSERT INTO `atmnetworks` VALUES (-1,'Unassigned'),(1,'Приватбанк'),(2,'Атмосфера'),(3,'Euronet'),(4,'Радiус'),(5,'УкрКард');
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
INSERT INTO `banks` VALUES (189,NULL,NULL,'2014-12-02 15:24:33',NULL,'ЕКСПРЕС-БАНК',NULL,-1,322959),(190,NULL,NULL,'2014-12-02 15:24:33',NULL,'КЛІРИНГОВИЙ ДІМ',NULL,-1,300647),(191,'','','2014-12-02 16:34:13','','УКООПСПІЛКА','',4,322625),(192,'','','2014-12-02 16:41:42','','УКРГАЗБАНК','',5,320478),(193,'','','2014-12-02 16:25:57','','ІНДУСТРІАЛБАНК','',4,313849),(194,NULL,NULL,'2014-12-02 15:24:33',NULL,'НОВИЙ',NULL,-1,305062),(195,'','','2014-12-02 16:13:10','','Південний','',2,328209),(196,NULL,NULL,'2014-12-02 15:24:33',NULL,'АРТЕМ-БАНК',NULL,-1,300885),(197,'','','2014-12-02 16:36:49','','БАНК БОГУСЛАВ','',5,380322),(198,'','','2014-12-02 16:37:00','','БАНК ВЕЛЕС','',5,322799),(199,NULL,NULL,'2014-12-02 15:24:33',NULL,'НАЦІОНАЛЬНІ ІНВЕСТИЦІЇ',NULL,-1,300498),(200,'','','2014-12-02 16:33:44','','БАНК РЕНЕСАНС КАПІТАЛ','',4,380010),(201,NULL,NULL,'2014-12-02 15:24:33',NULL,'ФІНАНСИ ТА КРЕДИТ',NULL,-1,300131),(202,'','','2014-12-02 16:04:06','','БМ БАНК','',2,380913),(203,'','','2014-12-02 16:09:48','','Дельта Банк','',2,380236),(204,NULL,NULL,'2014-12-02 15:24:33',NULL,'ЗЛАТОБАНК',NULL,-1,380612),(205,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІМЕКСБАНК',NULL,-1,328384),(206,NULL,NULL,'2014-12-02 15:24:34',NULL,'СОЮЗ',NULL,-1,380515),(207,NULL,NULL,'2014-12-02 15:24:34',NULL,'ТК КРЕДИТ',NULL,-1,322830),(208,NULL,NULL,'2014-12-02 15:24:34',NULL,'КІБ',NULL,-1,322540),(209,'','','2014-12-02 16:26:58','','МетаБанк','',4,313582),(210,'','','2014-12-02 16:27:13','','МІСТО БАНК','',4,328760),(211,NULL,NULL,'2014-12-02 15:24:34',NULL,'НК БАНК',NULL,-1,322432),(212,'','','2014-12-02 16:12:39','','ОТП Банк','',2,300528),(213,'bank_atm213.png','bank_off213.png','2014-12-18 15:44:20','','Ощадбанк','',1,300465),(214,'','','2014-12-02 16:13:26','','ПІРЕУС БАНК МКБ','',2,300658),(215,'','','2014-12-02 16:29:53','','ПРОКРЕДИТ БАНК','',4,320984),(216,NULL,NULL,'2014-12-02 15:24:34',NULL,'Український банк реконструкції та розвитку',NULL,-1,380883),(217,'bank_atm217.gif','bank_off217.gif','2014-12-18 16:53:17','','Райффайзен Банк Аваль','',-1,300335),(218,NULL,NULL,'2014-12-02 15:24:34',NULL,'РЕГІОН-БАНК',NULL,-1,351254),(219,NULL,NULL,'2014-12-02 15:24:34',NULL,'РОДОВІД БАНК',NULL,-1,321712),(220,'','','2014-12-02 16:16:22','','СБЕРБАНК РОСІЇ','',2,320627),(221,NULL,NULL,'2014-12-02 15:24:34',NULL,'СП БАНК',NULL,-1,304706),(222,'','','2014-12-02 16:34:00','','ТАСКОМБАНК','',4,339500),(223,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРБУДІНВЕСТБАНК',NULL,-1,380377),(224,NULL,NULL,'2014-12-02 15:24:34',NULL,'Укрексімбанк',NULL,-1,322313),(225,'','','2014-12-02 15:48:30','','УКРСИББАНК','',3,351005),(226,NULL,NULL,'2014-12-02 15:24:34',NULL,'ФОРТУНА-БАНК',NULL,-1,300904),(227,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНГ Банк Україна',NULL,-1,300539),(228,NULL,NULL,'2014-12-02 15:24:34',NULL,'А - БАНК',NULL,-1,307770),(229,'','','2014-12-02 16:33:04','','РАДАБАНК','',4,306500),(230,NULL,NULL,'2014-12-02 15:24:34',NULL,'СТОЛИЧНИЙ',NULL,-1,397133),(231,'','','2014-12-02 16:01:20','','АВАНТ-БАНК','',2,380708),(232,NULL,NULL,'2014-12-02 15:24:34',NULL,'Агрокомбанк',NULL,-1,322302),(233,NULL,NULL,'2014-12-02 15:24:34',NULL,'АРКАДА',NULL,-1,322335),(234,'','','2014-12-02 16:26:08','','КАПІТАЛ','',4,334828),(235,'','','2014-12-02 16:11:41','','КИЇВ','',2,322498),(236,NULL,NULL,'2014-12-02 15:24:34',NULL,'КОНКОРД',NULL,-1,307350),(237,'','','2014-12-02 16:26:33','','Львів','',4,325268),(238,'','','2014-12-02 16:41:01','','Траст-капітал','',5,380106),(239,NULL,NULL,'2014-12-02 15:24:34',NULL,'АЛЬПАРІ БАНК',NULL,-1,380894),(240,'','','2014-12-02 16:02:11','','АЛЬФА-БАНК','',2,300346),(241,NULL,NULL,'2014-12-02 15:24:34',NULL,'АПЕКС-БАНК',NULL,-1,380720),(242,NULL,NULL,'2014-12-02 15:24:34',NULL,'АСВІО БАНК',NULL,-1,353489),(243,NULL,NULL,'2014-12-02 15:24:34',NULL,'АСТРА БАНК',NULL,-1,380548),(244,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК АВАНГАРД',NULL,-1,380946),(245,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК АЛЬЯНС',NULL,-1,300119),(246,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК ВОСТОК',NULL,-1,307123),(247,'','','2014-12-02 16:25:03','','ГРАНТ','',4,351607),(248,'','','2014-12-02 16:02:34','','БАНК ІНВЕСТИЦІЙ ТА ЗАОЩАДЖЕНЬ','',2,380281),(249,'','','2014-12-02 16:21:35','','БАНК КАМБІО','',4,380399),(250,'','','2014-12-02 16:39:12','','КИЇВСЬКА РУСЬ','',5,319092),(251,'','','2014-12-02 16:37:21','','Контракт','',5,322465),(252,'','','2014-12-02 16:22:49','','БАНК КРЕДИТ ДНІПРО','',4,305749),(253,'','','2014-12-02 16:02:47','','БАНК МИХАЙЛІВСЬКИЙ','',2,380935),(254,'','','2014-12-02 16:37:51','','БАНК НАЦІОНАЛЬНИЙ КРЕДИТ','',5,320702),(255,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК ПЕТРОКОММЕРЦ-УКРАЇНА',NULL,-1,300120),(256,NULL,NULL,'2014-12-02 15:24:34',NULL,'ПОРТАЛ',NULL,-1,339016),(257,NULL,NULL,'2014-12-02 15:24:34',NULL,'БАНК СІЧ',NULL,-1,380816),(258,NULL,NULL,'2014-12-02 15:24:34',NULL,'СОФІЙСЬКИЙ',NULL,-1,380861),(259,NULL,NULL,'2014-12-02 15:24:34',NULL,'ТРАСТ',NULL,-1,380474),(260,'','','2014-12-02 16:03:30','','Український капітал','',2,320371),(261,'','','2014-12-02 15:47:17','','БАНК ФОРВАРД','',3,380418),(262,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЮНІСОН',NULL,-1,380902),(263,'','','2014-12-02 16:21:14','','БАНК 3/4','',4,380645),(264,'','','2014-12-02 16:03:52','','БГ БАНК','',2,320995),(265,'','','2014-12-02 16:09:00','','БТА БАНК','',2,321723),(266,'','','2014-12-02 16:47:11','','ВБР','',4,380719),(267,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВЕКТОР БАНК',NULL,-1,339038),(268,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВЕРНУМ БАНК',NULL,-1,380689),(269,'','','2014-12-02 16:09:19','','ВіЕйБі Банк','',2,380537),(270,'','','2014-12-02 16:09:34','','ВіЕс Банк','',2,325213),(271,NULL,NULL,'2014-12-02 15:24:34',NULL,'ВТБ БАНК',NULL,-1,321767),(272,NULL,NULL,'2014-12-02 15:24:34',NULL,'Держзембанк',NULL,-1,380968),(273,'','','2014-12-02 16:10:11','','ДІАМАНТБАНК','',2,320854),(274,NULL,NULL,'2014-12-02 15:24:34',NULL,'ДІВІ БАНК',NULL,-1,380827),(275,NULL,NULL,'2014-12-02 15:24:34',NULL,'Дойче Банк ДБУ',NULL,-1,380731),(276,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЕНЕРГОБАНК',NULL,-1,300272),(277,'','','2014-12-02 16:25:21','','ЄВРОПРОМБАНК','',4,377090),(278,'','','2014-12-02 16:10:57','','Ідея Банк','',2,336310),(279,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНВЕСТИЦІЙНО-ТРАСТОВИЙ БАНК',NULL,-1,380957),(280,'','','2014-12-02 16:38:49','','Інтеграл-банк','',5,320735),(281,NULL,NULL,'2014-12-02 15:24:34',NULL,'ГЕФЕСТ',NULL,-1,377120),(282,NULL,NULL,'2014-12-02 15:24:34',NULL,'ГЛОБУС',NULL,-1,380526),(283,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЄВРОБАНК',NULL,-1,380355),(284,NULL,NULL,'2014-12-02 15:24:34',NULL,'ЗЕМЕЛЬНИЙ КАПІТАЛ',NULL,-1,305880),(285,NULL,NULL,'2014-12-02 15:24:34',NULL,'ІНВЕСТБАНК',NULL,-1,328210),(286,NULL,NULL,'2014-12-02 15:24:34',NULL,'НАДРА',NULL,-1,380764),(287,NULL,NULL,'2014-12-02 15:24:34',NULL,'ПРЕМІУМ',NULL,-1,339555),(288,'bank_atm288.jpg','bank_off288.png','2014-12-18 16:35:56','','ПРИВАТБАНК','www.privatbank.ua',1,305299),(289,NULL,NULL,'2014-12-02 15:24:34',NULL,'СТАНДАРТ',NULL,-1,380690),(290,'','','2014-12-02 16:42:00','','ФІНАНСОВА ІНІЦІАТИВА','',5,380054),(291,NULL,NULL,'2014-12-02 15:24:34',NULL,'ФІНАНСОВИЙ ПАРТНЕР',NULL,-1,380872),(292,NULL,NULL,'2014-12-02 15:24:34',NULL,'ХРЕЩАТИК',NULL,-1,300670),(293,NULL,NULL,'2014-12-02 15:24:34',NULL,'Центр',NULL,-1,380742),(294,NULL,NULL,'2014-12-02 15:24:34',NULL,'КЛАСИКБАНК',NULL,-1,306704),(295,'','','2014-12-02 16:39:50','','КОМІНВЕСТБАНК','',5,312248),(296,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТ ЄВРОПА БАНК',NULL,-1,380366),(297,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТ ОПТИМА БАНК',NULL,-1,380571),(298,NULL,NULL,'2014-12-02 15:24:34',NULL,'КРЕДИТВЕСТ БАНК',NULL,-1,380441),(299,'','','2014-12-02 16:11:57','','Кредитпромбанк','',2,300863),(300,'','','2014-12-02 16:12:10','','КРЕДІ АГРІКОЛЬ БАНК','',2,300614),(301,'','','2014-12-02 16:26:20','','КРЕДОБАНК','',4,325912),(302,NULL,NULL,'2014-12-02 15:24:34',NULL,'КСГ БАНК',NULL,-1,380292),(303,'','','2014-12-02 16:12:24','','ЛЕГБАНК','',2,300056),(304,'','','2014-12-02 16:27:25','','МАРФІН БАНК','',4,328168),(305,NULL,NULL,'2014-12-02 15:24:34',NULL,'МЕГАБАНК',NULL,-1,351629),(306,NULL,NULL,'2014-12-02 15:24:34',NULL,'МІБ',NULL,-1,380582),(307,'','','2014-12-02 16:40:03','','МІСЬКИЙ КОМЕРЦІЙНИЙ БАНК','',5,339339),(308,'','','2014-12-02 16:28:11','','МОТОР-БАНК','',4,313009),(309,NULL,NULL,'2014-12-02 15:24:34',NULL,'НЕОС БАНК',NULL,-1,320940),(310,'','','2014-12-02 16:40:19','','ОКСІ БАНК','',5,325990),(311,NULL,NULL,'2014-12-02 15:24:34',NULL,'ОМЕГА БАНК',NULL,-1,300164),(312,'','','2014-12-02 16:28:27','','ПЕРШИЙ ІНВЕСТИЦІЙНИЙ БАНК','',4,300506),(313,'','','2014-12-02 16:40:31','','Полтава-банк','',5,331489),(314,NULL,NULL,'2014-12-02 15:24:34',NULL,'Промінвестбанк',NULL,-1,300012),(315,'','','2014-12-02 16:32:52','','ПРОФІН БАНК','',4,334594),(316,'','','2014-12-02 16:45:10','','ПтБ','',2,380388),(317,'','','2014-12-02 16:28:59','','ПУМБ','',4,334851),(318,'','','2014-12-02 16:32:36','','ПФБ','',4,331768),(319,'','','2014-12-02 16:40:42','','РАДИКАЛ БАНК','',5,319111),(320,NULL,NULL,'2014-12-02 15:24:34',NULL,'РОЗРАХУНКОВИЙ ЦЕНТР',NULL,-1,344443),(321,NULL,NULL,'2014-12-02 15:24:34',NULL,'СЕБ КОРПОРАТИВНИЙ БАНК',NULL,-1,380797),(322,NULL,NULL,'2014-12-02 15:24:34',NULL,'СІТІБАНК',NULL,-1,300584),(323,'','','2014-12-02 16:34:38','','УКРБІЗНЕСБАНК','',4,334969),(324,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРГАЗПРОМБАНК',NULL,-1,320843),(325,'','','2014-12-02 16:16:40','','УКРІНБАНК','',2,300142),(326,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРКОМУНБАНК',NULL,-1,304988),(327,NULL,NULL,'2014-12-02 15:24:34',NULL,'УКРСОЦБАНК',NULL,-1,300023),(328,NULL,NULL,'2014-12-02 15:24:34',NULL,'УНІВЕРСАЛ БАНК',NULL,-1,322001),(329,NULL,NULL,'2014-12-02 15:24:35',NULL,'УНІКОМБАНК',NULL,-1,335902),(330,'','','2014-12-02 16:41:25','','УПБ','',5,300205),(331,NULL,NULL,'2014-12-02 15:24:35',NULL,'ФІНАНС БАНК',NULL,-1,300896),(332,'','','2014-12-02 16:35:23','','ФІНБАНК','',4,328685),(333,NULL,NULL,'2014-12-02 15:24:35',NULL,'ФІНЕКСБАНК',NULL,-1,380311),(334,'','','2014-12-02 16:42:13','','ЮНЕКС БАНК','',5,322539),(335,NULL,NULL,'2014-12-02 15:24:35',NULL,'ЮСБ БАНК',NULL,-1,305987),(336,'','','2014-12-02 16:15:41','','ПРАВЕКС-БАНК','',2,380838),(337,NULL,NULL,'2014-12-02 15:24:35',NULL,'ПОЛІКОМБАН',NULL,-1,353100),(338,'','','2014-12-02 16:19:04','','АКОРДБАНК','',4,380634),(339,NULL,NULL,'2014-12-02 15:24:35',NULL,'СМАРТБАНК',NULL,-1,380786),(340,'','','2014-12-02 16:16:54','','ФІДОБАНК','',2,300175),(341,'','','2014-12-02 16:23:27','','БАНК ФАМІЛЬНИЙ','',4,334840);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'єдявталимжліимта','2014-12-18 18:42:43',39,1),(2,'впроавпроіапр','2014-12-18 18:43:20',39,1),(3,'sfgh','2014-12-22 18:31:37',36,1),(4,'srftgh','2014-12-22 18:31:43',36,1),(5,'xcfghj','2014-12-22 18:34:15',36,1),(6,'dsfyju','2014-12-22 18:34:50',36,1),(7,'srgj','2014-12-22 18:35:04',36,1),(8,'dfgj','2014-12-22 18:35:44',36,1),(9,'rtyu','2014-12-22 18:36:30',36,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (1,NULL,41,1),(3,NULL,36,1),(4,NULL,42,1),(6,NULL,33,1),(7,NULL,40,1);
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DATED` datetime NOT NULL,
  `logger` varchar(250) DEFAULT NULL,
  `LEVEL` varchar(10) NOT NULL,
  `MESSAGE` varchar(1000) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,'2014-12-22 19:04:54','ContextLoader:285','INFO','Root WebApplicationContext: initialization started'),(2,'2014-12-22 19:04:55','XmlWebApplicationContext:512','INFO','Refreshing Root WebApplicationContext: startup date [Mon Dec 22 19:04:55 EET 2014]; root of context hierarchy'),(3,'2014-12-22 19:04:55','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/app-context.xml]'),(4,'2014-12-22 19:04:55','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/spring-security.xml]'),(5,'2014-12-22 19:04:55','SpringSecurityCoreVersion:58','INFO','You are running with Spring Security Core 3.2.5.RELEASE'),(6,'2014-12-22 19:04:56','SecurityNamespaceHandler:75','INFO','Spring Security \'config\' module version is 3.2.5.RELEASE'),(7,'2014-12-22 19:04:56','FilterInvocationSecurityMetadataSourceParser:134','INFO','Creating access control expression attribute \'hasRole(\'ADMIN\')\' for /admin**'),(8,'2014-12-22 19:04:56','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/jdbc.properties]'),(9,'2014-12-22 19:04:56','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/email.properties]'),(10,'2014-12-22 19:04:56','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/matcher.properties]'),(11,'2014-12-22 19:04:56','LocalContainerEntityManagerFactoryBean:287','INFO','Building JPA container EntityManagerFactory for persistence unit \'default\''),(12,'2014-12-22 19:04:59','LocalDataSourceJobStore:667','INFO','Using db table-based data access locking (synchronization).'),(13,'2014-12-22 19:04:59','LocalDataSourceJobStore:145','INFO','JobStoreCMT initialized.'),(14,'2014-12-22 19:04:59','JdbcUserDetailsManager:130','INFO','No authentication manager set. Reauthentication of users when changing passwords will not be performed.'),(15,'2014-12-22 19:05:00','DefaultFilterChainValidator:124','INFO','Checking whether login URL \'/login\' is accessible with your configuration'),(16,'2014-12-22 19:05:00','DefaultLifecycleProcessor:334','INFO','Starting beans in phase 2147483647'),(17,'2014-12-22 19:05:00','SchedulerFactoryBean:642','INFO','Starting Quartz Scheduler now'),(18,'2014-12-22 19:05:00','LocalDataSourceJobStore:861','INFO','Freed 0 triggers from \'acquired\' / \'blocked\' state.'),(19,'2014-12-22 19:05:00','LocalDataSourceJobStore:871','INFO','Recovering 0 jobs that were in-progress at the time of the last shut-down.'),(20,'2014-12-22 19:05:00','LocalDataSourceJobStore:884','INFO','Recovery complete.'),(21,'2014-12-22 19:05:00','LocalDataSourceJobStore:891','INFO','Removed 0 \'complete\' triggers.'),(22,'2014-12-22 19:05:00','LocalDataSourceJobStore:896','INFO','Removed 0 stale fired job entries.'),(23,'2014-12-22 19:05:00','ContextLoader:325','INFO','Root WebApplicationContext: initialization completed in 5545 ms'),(24,'2014-12-22 19:05:00','DispatcherServlet:457','INFO','FrameworkServlet \'mvc-dispatcher\': initialization started'),(25,'2014-12-22 19:05:00','XmlWebApplicationContext:512','INFO','Refreshing WebApplicationContext for namespace \'mvc-dispatcher-servlet\': startup date [Mon Dec 22 19:05:00 EET 2014]; parent: Root WebApplicationContext'),(26,'2014-12-22 19:05:00','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/mvc-dispatcher-servlet.xml]'),(27,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankEdit],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankEdit(int,org.springframework.ui.ModelMap)'),(28,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/banksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.AdminBanksController.banksListAjax()'),(29,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/updateBanksFromNbu],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.saveAllBank()'),(30,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankSaveAjax(com.ss.atmlocator.entity.Bank,int,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,javax.servlet.http.HttpServletRequest)'),(31,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankCreateNew],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankCreateNew(org.springframework.ui.ModelMap)'),(32,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBanks],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.banksList(org.springframework.ui.ModelMap)'),(33,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/networksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.AtmNetwork> com.ss.atmlocator.controller.AdminBanksController.networksListAjax()'),(34,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkSaveAjax(com.ss.atmlocator.entity.AtmNetwork,javax.servlet.http.HttpServletRequest)'),(35,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankDeleteAjax(int)'),(36,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkDeleteAjax(int)'),(37,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankAtmList],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.adminBankAtmList(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(38,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminPage(org.springframework.ui.ModelMap)'),(39,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/enable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.enableJob(java.lang.String)'),(40,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/addnew],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.addNewJob(org.springframework.ui.ModelMap)'),(41,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/edit/{currentJobName}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.editJob(java.lang.String,org.springframework.ui.ModelMap)'),(42,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/disable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.disableJob(java.lang.String)'),(43,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/save],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.saveJob(com.ss.atmlocator.parser.scheduler.JobModel,java.lang.String,org.springframework.ui.ModelMap)'),(44,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/users],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminUsers(org.springframework.ui.ModelMap)'),(45,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/run],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.runJob(java.lang.String)'),(46,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.deleteJob(java.lang.String)'),(47,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/tes],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.testParser()'),(48,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/bankParser],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.showParsers(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(49,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/saveChanges],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.saveChanges(java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,int,org.springframework.ui.ModelMap)'),(50,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<com.ss.atmlocator.entity.AtmComment>> com.ss.atmlocator.controller.AtmController.getComments(int)'),(51,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AtmController.addComment(int,java.lang.String,java.security.Principal)'),(52,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredlogin],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkLogin(java.lang.String)'),(53,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredemail],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkEmail(java.lang.String)'),(54,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.Set<com.ss.atmlocator.entity.AtmOffice>> com.ss.atmlocator.controller.FavoritesController.getFavorites(java.security.Principal)'),(55,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.addFavorite(int,java.security.Principal)'),(56,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.delFromFavorites(int,java.security.Principal)'),(57,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/feedback],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FeedbackController.putFeedback(java.lang.String,java.security.Principal)'),(58,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/login],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.LoginController.login(java.lang.String,org.springframework.ui.ModelMap)'),(59,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.MainController.indexPage(org.springframework.ui.ModelMap)'),(60,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getATMs],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.Collection<com.ss.atmlocator.entity.AtmOffice> com.ss.atmlocator.controller.MapController.getATMs(java.lang.Integer,java.lang.Integer,double,double,int,boolean,boolean,java.security.Principal)'),(61,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getBanksByNetwork],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.MapController.getBanksByNetwork(java.lang.Integer)'),(62,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNotices],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.NoticesController.banksList(org.springframework.ui.ModelMap)'),(63,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/profile],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.ProfileController.profile(org.springframework.ui.ModelMap,java.security.Principal)'),(64,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/registering],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.registering(java.lang.String,java.lang.String,java.lang.String,java.lang.String,org.springframework.ui.Model,javax.servlet.http.HttpServletRequest)'),(65,'2014-12-22 19:05:01','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/signup],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.signup()'),(66,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/testU],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.TestUbanksController.updateAll()'),(67,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{value}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.entity.User> com.ss.atmlocator.controller.UsersRestController.findUser(java.lang.String)'),(68,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.utils.jQueryAutoCompleteResponse> com.ss.atmlocator.controller.UsersRestController.getUserNames(java.lang.String)'),(69,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.UsersRestController.deleteUser(int)'),(70,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[PATCH],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.FieldError>> com.ss.atmlocator.controller.UsersRestController.updateUser(int,com.ss.atmlocator.entity.User,boolean,java.security.Principal,org.springframework.validation.BindingResult)'),(71,'2014-12-22 19:05:02','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}/avatar],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.ObjectError>> com.ss.atmlocator.controller.UsersRestController.updateAvatar(int,com.ss.atmlocator.utils.UploadedFile,org.springframework.validation.BindingResult,javax.servlet.http.HttpServletRequest)'),(72,'2014-12-22 19:05:02','SimpleUrlHandlerMapping:315','INFO','Mapped URL path [/resources/**] onto handler \'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0\''),(73,'2014-12-22 19:05:02','TilesConfigurer:222','INFO','TilesConfigurer: adding definitions [/WEB-INF/spring/tiles.xml]'),(74,'2014-12-22 19:05:02','TilesConfigurer:511','INFO','Found JSP 2.1 ExpressionFactory'),(75,'2014-12-22 19:05:02','DispatcherServlet:476','INFO','FrameworkServlet \'mvc-dispatcher\': initialization completed in 1855 ms'),(76,'2014-12-22 19:05:42','XmlWebApplicationContext:1044','INFO','Closing WebApplicationContext for namespace \'mvc-dispatcher-servlet\': startup date [Mon Dec 22 19:05:00 EET 2014]; parent: Root WebApplicationContext'),(77,'2014-12-22 19:05:42','XmlWebApplicationContext:1044','INFO','Closing Root WebApplicationContext: startup date [Mon Dec 22 19:04:55 EET 2014]; root of context hierarchy'),(78,'2014-12-22 19:05:42','DefaultLifecycleProcessor:349','INFO','Stopping beans in phase 2147483647'),(79,'2014-12-22 19:05:42','SchedulerFactoryBean:752','INFO','Shutting down Quartz Scheduler'),(80,'2014-12-22 19:05:42','LocalContainerEntityManagerFactoryBean:441','INFO','Closing JPA EntityManagerFactory for persistence unit \'default\''),(81,'2014-12-22 19:05:54','ContextLoader:285','INFO','Root WebApplicationContext: initialization started'),(82,'2014-12-22 19:05:54','XmlWebApplicationContext:512','INFO','Refreshing Root WebApplicationContext: startup date [Mon Dec 22 19:05:54 EET 2014]; root of context hierarchy'),(83,'2014-12-22 19:05:54','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/app-context.xml]'),(84,'2014-12-22 19:05:54','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/spring-security.xml]'),(85,'2014-12-22 19:05:55','SpringSecurityCoreVersion:58','INFO','You are running with Spring Security Core 3.2.5.RELEASE'),(86,'2014-12-22 19:05:55','SecurityNamespaceHandler:75','INFO','Spring Security \'config\' module version is 3.2.5.RELEASE'),(87,'2014-12-22 19:05:55','FilterInvocationSecurityMetadataSourceParser:134','INFO','Creating access control expression attribute \'hasRole(\'ADMIN\')\' for /admin**'),(88,'2014-12-22 19:05:55','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/jdbc.properties]'),(89,'2014-12-22 19:05:55','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/email.properties]'),(90,'2014-12-22 19:05:55','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/matcher.properties]'),(91,'2014-12-22 19:05:55','LocalContainerEntityManagerFactoryBean:287','INFO','Building JPA container EntityManagerFactory for persistence unit \'default\''),(92,'2014-12-22 19:05:57','LocalDataSourceJobStore:667','INFO','Using db table-based data access locking (synchronization).'),(93,'2014-12-22 19:05:57','LocalDataSourceJobStore:145','INFO','JobStoreCMT initialized.'),(94,'2014-12-22 19:05:57','JdbcUserDetailsManager:130','INFO','No authentication manager set. Reauthentication of users when changing passwords will not be performed.'),(95,'2014-12-22 19:05:58','DefaultFilterChainValidator:124','INFO','Checking whether login URL \'/login\' is accessible with your configuration'),(96,'2014-12-22 19:05:58','DefaultLifecycleProcessor:334','INFO','Starting beans in phase 2147483647'),(97,'2014-12-22 19:05:58','SchedulerFactoryBean:642','INFO','Starting Quartz Scheduler now'),(98,'2014-12-22 19:05:58','LocalDataSourceJobStore:861','INFO','Freed 0 triggers from \'acquired\' / \'blocked\' state.'),(99,'2014-12-22 19:05:58','LocalDataSourceJobStore:871','INFO','Recovering 0 jobs that were in-progress at the time of the last shut-down.'),(100,'2014-12-22 19:05:58','LocalDataSourceJobStore:884','INFO','Recovery complete.'),(101,'2014-12-22 19:05:58','LocalDataSourceJobStore:891','INFO','Removed 0 \'complete\' triggers.'),(102,'2014-12-22 19:05:58','LocalDataSourceJobStore:896','INFO','Removed 0 stale fired job entries.'),(103,'2014-12-22 19:05:58','ContextLoader:325','INFO','Root WebApplicationContext: initialization completed in 4071 ms'),(104,'2014-12-22 19:05:58','DispatcherServlet:457','INFO','FrameworkServlet \'mvc-dispatcher\': initialization started'),(105,'2014-12-22 19:05:58','XmlWebApplicationContext:512','INFO','Refreshing WebApplicationContext for namespace \'mvc-dispatcher-servlet\': startup date [Mon Dec 22 19:05:58 EET 2014]; parent: Root WebApplicationContext'),(106,'2014-12-22 19:05:58','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/mvc-dispatcher-servlet.xml]'),(107,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/networksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.AtmNetwork> com.ss.atmlocator.controller.AdminBanksController.networksListAjax()'),(108,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkSaveAjax(com.ss.atmlocator.entity.AtmNetwork,javax.servlet.http.HttpServletRequest)'),(109,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankDeleteAjax(int)'),(110,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkDeleteAjax(int)'),(111,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankAtmList],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.adminBankAtmList(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(112,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankEdit],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankEdit(int,org.springframework.ui.ModelMap)'),(113,'2014-12-22 19:05:58','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/banksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.AdminBanksController.banksListAjax()'),(114,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/updateBanksFromNbu],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.saveAllBank()'),(115,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankSaveAjax(com.ss.atmlocator.entity.Bank,int,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,javax.servlet.http.HttpServletRequest)'),(116,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankCreateNew],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankCreateNew(org.springframework.ui.ModelMap)'),(117,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBanks],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.banksList(org.springframework.ui.ModelMap)'),(118,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/run],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.runJob(java.lang.String)'),(119,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.deleteJob(java.lang.String)'),(120,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminPage(org.springframework.ui.ModelMap)'),(121,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/enable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.enableJob(java.lang.String)'),(122,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/addnew],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.addNewJob(org.springframework.ui.ModelMap)'),(123,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/edit/{currentJobName}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.editJob(java.lang.String,org.springframework.ui.ModelMap)'),(124,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/disable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.disableJob(java.lang.String)'),(125,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/save],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.saveJob(com.ss.atmlocator.parser.scheduler.JobModel,java.lang.String,org.springframework.ui.ModelMap)'),(126,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/users],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminUsers(org.springframework.ui.ModelMap)'),(127,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/saveChanges],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.saveChanges(java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,int,org.springframework.ui.ModelMap)'),(128,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/tes],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.testParser()'),(129,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/bankParser],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.showParsers(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(130,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AtmController.addComment(int,java.lang.String,java.security.Principal)'),(131,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<com.ss.atmlocator.entity.AtmComment>> com.ss.atmlocator.controller.AtmController.getComments(int)'),(132,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredemail],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkEmail(java.lang.String)'),(133,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredlogin],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkLogin(java.lang.String)'),(134,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.delFromFavorites(int,java.security.Principal)'),(135,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.Set<com.ss.atmlocator.entity.AtmOffice>> com.ss.atmlocator.controller.FavoritesController.getFavorites(java.security.Principal)'),(136,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.addFavorite(int,java.security.Principal)'),(137,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/feedback],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FeedbackController.putFeedback(java.lang.String,java.security.Principal)'),(138,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/login],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.LoginController.login(java.lang.String,org.springframework.ui.ModelMap)'),(139,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.MainController.indexPage(org.springframework.ui.ModelMap)'),(140,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getBanksByNetwork],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.MapController.getBanksByNetwork(java.lang.Integer)'),(141,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getATMs],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.Collection<com.ss.atmlocator.entity.AtmOffice> com.ss.atmlocator.controller.MapController.getATMs(java.lang.Integer,java.lang.Integer,double,double,int,boolean,boolean,java.security.Principal)'),(142,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNotices],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.NoticesController.banksList(org.springframework.ui.ModelMap)'),(143,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/profile],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.ProfileController.profile(org.springframework.ui.ModelMap,java.security.Principal)'),(144,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/registering],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.registering(java.lang.String,java.lang.String,java.lang.String,java.lang.String,org.springframework.ui.Model,javax.servlet.http.HttpServletRequest)'),(145,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/signup],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.signup()'),(146,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/testU],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.TestUbanksController.updateAll()'),(147,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{value}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.entity.User> com.ss.atmlocator.controller.UsersRestController.findUser(java.lang.String)'),(148,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.UsersRestController.deleteUser(int)'),(149,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[PATCH],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.FieldError>> com.ss.atmlocator.controller.UsersRestController.updateUser(int,com.ss.atmlocator.entity.User,boolean,java.security.Principal,org.springframework.validation.BindingResult)'),(150,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}/avatar],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.ObjectError>> com.ss.atmlocator.controller.UsersRestController.updateAvatar(int,com.ss.atmlocator.utils.UploadedFile,org.springframework.validation.BindingResult,javax.servlet.http.HttpServletRequest)'),(151,'2014-12-22 19:05:59','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.utils.jQueryAutoCompleteResponse> com.ss.atmlocator.controller.UsersRestController.getUserNames(java.lang.String)'),(152,'2014-12-22 19:06:00','SimpleUrlHandlerMapping:315','INFO','Mapped URL path [/resources/**] onto handler \'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0\''),(153,'2014-12-22 19:06:00','TilesConfigurer:222','INFO','TilesConfigurer: adding definitions [/WEB-INF/spring/tiles.xml]'),(154,'2014-12-22 19:06:00','TilesConfigurer:511','INFO','Found JSP 2.1 ExpressionFactory'),(155,'2014-12-22 19:06:00','DispatcherServlet:476','INFO','FrameworkServlet \'mvc-dispatcher\': initialization completed in 1656 ms'),(156,'2014-12-22 19:06:23','UsernamePasswordAuthenticationFilter:218','ERROR','An internal error occurred while trying to authenticate the user.'),(157,'2014-12-22 19:06:32','UsernamePasswordAuthenticationFilter:218','ERROR','An internal error occurred while trying to authenticate the user.'),(158,'2014-12-22 19:07:33','UsernamePasswordAuthenticationFilter:218','ERROR','An internal error occurred while trying to authenticate the user.'),(159,'2014-12-22 19:08:28','XmlWebApplicationContext:1044','INFO','Closing WebApplicationContext for namespace \'mvc-dispatcher-servlet\': startup date [Mon Dec 22 19:05:58 EET 2014]; parent: Root WebApplicationContext'),(160,'2014-12-22 19:08:28','XmlWebApplicationContext:1044','INFO','Closing Root WebApplicationContext: startup date [Mon Dec 22 19:05:54 EET 2014]; root of context hierarchy'),(161,'2014-12-22 19:08:28','DefaultLifecycleProcessor:349','INFO','Stopping beans in phase 2147483647'),(162,'2014-12-22 19:08:28','SchedulerFactoryBean:752','INFO','Shutting down Quartz Scheduler'),(163,'2014-12-22 19:08:28','LocalContainerEntityManagerFactoryBean:441','INFO','Closing JPA EntityManagerFactory for persistence unit \'default\''),(164,'2014-12-22 19:08:40','ContextLoader:285','INFO','Root WebApplicationContext: initialization started'),(165,'2014-12-22 19:08:40','XmlWebApplicationContext:512','INFO','Refreshing Root WebApplicationContext: startup date [Mon Dec 22 19:08:40 EET 2014]; root of context hierarchy'),(166,'2014-12-22 19:08:40','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/app-context.xml]'),(167,'2014-12-22 19:08:40','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/spring-security.xml]'),(168,'2014-12-22 19:08:40','SpringSecurityCoreVersion:58','INFO','You are running with Spring Security Core 3.2.5.RELEASE'),(169,'2014-12-22 19:08:41','SecurityNamespaceHandler:75','INFO','Spring Security \'config\' module version is 3.2.5.RELEASE'),(170,'2014-12-22 19:08:41','FilterInvocationSecurityMetadataSourceParser:134','INFO','Creating access control expression attribute \'hasRole(\'ADMIN\')\' for /admin**'),(171,'2014-12-22 19:08:41','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/jdbc.properties]'),(172,'2014-12-22 19:08:41','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/email.properties]'),(173,'2014-12-22 19:08:41','PropertyPlaceholderConfigurer:172','INFO','Loading properties file from ServletContext resource [/WEB-INF/spring/properties/matcher.properties]'),(174,'2014-12-22 19:08:41','LocalContainerEntityManagerFactoryBean:287','INFO','Building JPA container EntityManagerFactory for persistence unit \'default\''),(175,'2014-12-22 19:08:43','LocalDataSourceJobStore:667','INFO','Using db table-based data access locking (synchronization).'),(176,'2014-12-22 19:08:43','LocalDataSourceJobStore:145','INFO','JobStoreCMT initialized.'),(177,'2014-12-22 19:08:43','JdbcUserDetailsManager:130','INFO','No authentication manager set. Reauthentication of users when changing passwords will not be performed.'),(178,'2014-12-22 19:08:43','DefaultFilterChainValidator:124','INFO','Checking whether login URL \'/login\' is accessible with your configuration'),(179,'2014-12-22 19:08:43','DefaultLifecycleProcessor:334','INFO','Starting beans in phase 2147483647'),(180,'2014-12-22 19:08:44','SchedulerFactoryBean:642','INFO','Starting Quartz Scheduler now'),(181,'2014-12-22 19:08:44','LocalDataSourceJobStore:861','INFO','Freed 0 triggers from \'acquired\' / \'blocked\' state.'),(182,'2014-12-22 19:08:44','LocalDataSourceJobStore:871','INFO','Recovering 0 jobs that were in-progress at the time of the last shut-down.'),(183,'2014-12-22 19:08:44','LocalDataSourceJobStore:884','INFO','Recovery complete.'),(184,'2014-12-22 19:08:44','LocalDataSourceJobStore:891','INFO','Removed 0 \'complete\' triggers.'),(185,'2014-12-22 19:08:44','LocalDataSourceJobStore:896','INFO','Removed 0 stale fired job entries.'),(186,'2014-12-22 19:08:44','ContextLoader:325','INFO','Root WebApplicationContext: initialization completed in 3734 ms'),(187,'2014-12-22 19:08:44','DispatcherServlet:457','INFO','FrameworkServlet \'mvc-dispatcher\': initialization started'),(188,'2014-12-22 19:08:44','XmlWebApplicationContext:512','INFO','Refreshing WebApplicationContext for namespace \'mvc-dispatcher-servlet\': startup date [Mon Dec 22 19:08:44 EET 2014]; parent: Root WebApplicationContext'),(189,'2014-12-22 19:08:44','XmlBeanDefinitionReader:315','INFO','Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/mvc-dispatcher-servlet.xml]'),(190,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankEdit],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankEdit(int,org.springframework.ui.ModelMap)'),(191,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/banksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.AdminBanksController.banksListAjax()'),(192,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/updateBanksFromNbu],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.saveAllBank()'),(193,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankSaveAjax(com.ss.atmlocator.entity.Bank,int,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile,javax.servlet.http.HttpServletRequest)'),(194,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankCreateNew],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.bankCreateNew(org.springframework.ui.ModelMap)'),(195,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBanks],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.banksList(org.springframework.ui.ModelMap)'),(196,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/networksListAjax],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.AtmNetwork> com.ss.atmlocator.controller.AdminBanksController.networksListAjax()'),(197,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkSaveAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkSaveAjax(com.ss.atmlocator.entity.AtmNetwork,javax.servlet.http.HttpServletRequest)'),(198,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.bankDeleteAjax(int)'),(199,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNetworkDeleteAjax],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.OutResponse com.ss.atmlocator.controller.AdminBanksController.networkDeleteAjax(int)'),(200,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminBankAtmList],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminBanksController.adminBankAtmList(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(201,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminPage(org.springframework.ui.ModelMap)'),(202,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/enable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.enableJob(java.lang.String)'),(203,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/addnew],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.addNewJob(org.springframework.ui.ModelMap)'),(204,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/edit/{currentJobName}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.editJob(java.lang.String,org.springframework.ui.ModelMap)'),(205,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/disable],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.disableJob(java.lang.String)'),(206,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/save],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.saveJob(com.ss.atmlocator.parser.scheduler.JobModel,java.lang.String,org.springframework.ui.ModelMap)'),(207,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/users],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminController.adminUsers(org.springframework.ui.ModelMap)'),(208,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}/run],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.runJob(java.lang.String)'),(209,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/admin/{currentJobName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AdminController.deleteJob(java.lang.String)'),(210,'2014-12-22 19:08:44','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/tes],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.testParser()'),(211,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/bankParser],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.showParsers(com.ss.atmlocator.entity.Bank,org.springframework.ui.ModelMap)'),(212,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/saveChanges],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.AdminParserController.saveChanges(java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,int,org.springframework.ui.ModelMap)'),(213,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<com.ss.atmlocator.entity.AtmComment>> com.ss.atmlocator.controller.AtmController.getComments(int)'),(214,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/atms/{id}/comments],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.AtmController.addComment(int,java.lang.String,java.security.Principal)'),(215,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredemail],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkEmail(java.lang.String)'),(216,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/usercredlogin],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public com.ss.atmlocator.utils.CheckUserCredCode com.ss.atmlocator.controller.CheckUserCredAJAX.checkLogin(java.lang.String)'),(217,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.delFromFavorites(int,java.security.Principal)'),(218,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.Set<com.ss.atmlocator.entity.AtmOffice>> com.ss.atmlocator.controller.FavoritesController.getFavorites(java.security.Principal)'),(219,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/favorites/{id}],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FavoritesController.addFavorite(int,java.security.Principal)'),(220,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/feedback],methods=[PUT],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.FeedbackController.putFeedback(java.lang.String,java.security.Principal)'),(221,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/login],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.LoginController.login(java.lang.String,org.springframework.ui.ModelMap)'),(222,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.MainController.indexPage(org.springframework.ui.ModelMap)'),(223,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getBanksByNetwork],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.List<com.ss.atmlocator.entity.Bank> com.ss.atmlocator.controller.MapController.getBanksByNetwork(java.lang.Integer)'),(224,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/map/getATMs],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.util.Collection<com.ss.atmlocator.entity.AtmOffice> com.ss.atmlocator.controller.MapController.getATMs(java.lang.Integer,java.lang.Integer,double,double,int,boolean,boolean,java.security.Principal)'),(225,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/adminNotices],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.NoticesController.banksList(org.springframework.ui.ModelMap)'),(226,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/profile],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.ProfileController.profile(org.springframework.ui.ModelMap,java.security.Principal)'),(227,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/registering],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.registering(java.lang.String,java.lang.String,java.lang.String,java.lang.String,org.springframework.ui.Model,javax.servlet.http.HttpServletRequest)'),(228,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/signup],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.SignUpController.signup()'),(229,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/testU],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public java.lang.String com.ss.atmlocator.controller.TestUbanksController.updateAll()'),(230,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{value}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.entity.User> com.ss.atmlocator.controller.UsersRestController.findUser(java.lang.String)'),(231,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<com.ss.atmlocator.utils.jQueryAutoCompleteResponse> com.ss.atmlocator.controller.UsersRestController.getUserNames(java.lang.String)'),(232,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.lang.Void> com.ss.atmlocator.controller.UsersRestController.deleteUser(int)'),(233,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}],methods=[PATCH],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.FieldError>> com.ss.atmlocator.controller.UsersRestController.updateUser(int,com.ss.atmlocator.entity.User,boolean,java.security.Principal,org.springframework.validation.BindingResult)'),(234,'2014-12-22 19:08:45','RequestMappingHandlerMapping:180','INFO','Mapped \"{[/users/{id}/avatar],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}\" onto public org.springframework.http.ResponseEntity<java.util.List<org.springframework.validation.ObjectError>> com.ss.atmlocator.controller.UsersRestController.updateAvatar(int,com.ss.atmlocator.utils.UploadedFile,org.springframework.validation.BindingResult,javax.servlet.http.HttpServletRequest)'),(235,'2014-12-22 19:08:45','SimpleUrlHandlerMapping:315','INFO','Mapped URL path [/resources/**] onto handler \'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0\''),(236,'2014-12-22 19:08:45','TilesConfigurer:222','INFO','TilesConfigurer: adding definitions [/WEB-INF/spring/tiles.xml]'),(237,'2014-12-22 19:08:45','TilesConfigurer:511','INFO','Found JSP 2.1 ExpressionFactory'),(238,'2014-12-22 19:08:45','DispatcherServlet:476','INFO','FrameworkServlet \'mvc-dispatcher\': initialization completed in 1701 ms'),(239,'2014-12-22 19:08:56','UsernamePasswordAuthenticationFilter:218','ERROR','An internal error occurred while trying to authenticate the user.'),(240,'2014-12-22 19:20:08','SchcedService:65','INFO','Job was manually ruuned. Job name:sfgjdhj'),(241,'2014-12-22 19:20:08','SchcedService:65','INFO','Job was manually ruuned. Job name:sfgjdhj'),(242,'2014-12-22 19:20:08','KredoBankParser:90','INFO','Try to load start page http://www.kredobank.com.ua/our_coordinates.html'),(243,'2014-12-22 19:20:09','KredoBankParser:101','INFO','Try to parse ATMs from region івано-франківськаобласть'),(244,'2014-12-22 19:20:09','KredoBankParser:123','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/s/3068'),(245,'2014-12-22 19:20:09','KredoBankParser:131','INFO','Try to parse ATMs from city м. Івано-Франківськ'),(246,'2014-12-22 19:20:09','KredoBankParser:144','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/a/3085'),(247,'2014-12-22 19:20:10','KredoBankParser:131','INFO','Try to parse ATMs from city м. Надвірна'),(248,'2014-12-22 19:20:10','KredoBankParser:144','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/a/3232'),(249,'2014-12-22 19:20:10','KredoBankParser:131','INFO','Try to parse ATMs from city м. Калуш'),(250,'2014-12-22 19:20:10','KredoBankParser:144','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/a/3293'),(251,'2014-12-22 19:20:10','KredoBankParser:131','INFO','Try to parse ATMs from city м. Бурштин'),(252,'2014-12-22 19:20:10','KredoBankParser:144','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/a/3449'),(253,'2014-12-22 19:20:11','KredoBankParser:131','INFO','Try to parse ATMs from city м. Рогатин'),(254,'2014-12-22 19:20:11','KredoBankParser:144','INFO','Try to connect to URL http://www.kredobank.com.ua/xml/coord_data/a/3351'),(255,'2014-12-22 19:20:11','KredoBankParser:107','INFO','Parsing is done. Was parsed 10 ATMs and offices'),(256,'2014-12-22 19:23:27','OschadBankParser:84','INFO','Begin parse region: #0 Донецька область'),(257,'2014-12-22 19:26:39','OschadBankParser:84','INFO','Begin parse region: #0 Івано-Франківська область');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices`
--

DROP TABLE IF EXISTS `notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `notices` ENABLE KEYS */;
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
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('quartz-scheduler','dfsgnghf','dfgh','* * * * 1 ?','Europe/Helsinki');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('quartz-scheduler','sfgjdhj','dfsgnghf',NULL,'com.ss.atmlocator.parser.bankParsers.KredoBankExecutor','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0bankidt\0301t\0regionst\0/івано-франківськаобластьt\0urlt\0http://www.kredobank.com.uax\0');
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('quartz-scheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('quartz-scheduler','dfsgnghf','dfgh','sfgjdhj','dfsgnghf',NULL,1420063200000,-1,5,'WAITING','CRON',1419266493000,0,NULL,0,'');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
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
INSERT INTO `roles` VALUES (1,'Role for administrators','ADMIN'),(2,'Role for registered users','USER');
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
INSERT INTO `user_role` VALUES (1,1),(3,1),(1,2),(2,2),(3,2);
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
INSERT INTO `user_roles` VALUES (1,'ADMIN'),(2,'USER');
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
  `name` varchar(255) DEFAULT NULL,
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
INSERT INTO `users` VALUES (1,'avatar_1.jpg','admin@mail.com',1,'admin','c4ca4238a0b923820dcc509a6f75849b','2014-12-22 19:10:11','JamesBond'),(2,'defaultUserAvatar.jpg','user@mail.com',1,'user','c4ca4238a0b923820dcc509a6f75849b',NULL,NULL),(3,'defaultUserAvatar.jpg','olavin@ukr.net',1,'Oleg','c4ca4238a0b923820dcc509a6f75849b',NULL,NULL);
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

-- Dump completed on 2014-12-22 19:32:49
