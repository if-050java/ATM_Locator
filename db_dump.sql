
C:\Develop\workspace\ATM_Locator>"C:\Program Files\MySQL\MySQL Workbench 6.2 CE\mysqldump.exe" -u root -proot atmlocator 
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
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
  `bank_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_9ullxpauvabhfpsfrbls9hboq` (`bank_id`),
  CONSTRAINT `FK_9ullxpauvabhfpsfrbls9hboq` FOREIGN KEY (`bank_id`) REFERENCES `banks` (`Id`)
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
INSERT INTO `atmnetworks` VALUES (-1,'Unassigned'),(1,'Приватбанк'),(2,'Атмосфера'),(3,'Euronet'),(4,'Рад?ус'),(5,'УкрКард');
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
  `network_id` int(11) DEFAULT NULL,
  `mfoCode` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_gt16uxf7jnyii8i4sgi5j90dd` (`network_id`),
  CONSTRAINT `FK_gt16uxf7jnyii8i4sgi5j90dd` FOREIGN KEY (`network_id`) REFERENCES `atmnetworks` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=782 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banks`
--

LOCK TABLES `banks` WRITE;
/*!40000 ALTER TABLE `banks` DISABLE KEYS */;
INSERT INTO `banks` VALUES (681,'bank_atm_681.png','bank_off_681.png',NULL,'bank_logo681.png','Приватбанк','https://privatbank.ua/',1,555444),(682,'','',NULL,'','ПАО \"Креди Агриколь Банк\"','http://credit-agricole.com.ua/rus/',2,123),(683,NULL,NULL,NULL,NULL,'ПАО \"АКБ \"Киев\"','http://www.bank.kiev.ua',2,123456),(684,NULL,NULL,NULL,NULL,'АКБ \"Легбанк\"','http://www.legbank.kiev.ua',2,123456),(685,NULL,NULL,NULL,NULL,'АО \"БМ Банк\"','http://www.bmbank.com.ua',2,123456),(686,NULL,NULL,NULL,NULL,'ПАО \"БТА Банк\"','http://www.bta.kiev.ua',2,123456),(687,NULL,NULL,NULL,NULL,'ПАО \"Диамантбанк\"','http://www.diamantbank.ua/ru',2,123456),(688,NULL,NULL,NULL,NULL,'ПАО \"ВиЭйБи Банк\"','http://www.vab.ua/rus/',2,123456),(689,NULL,NULL,NULL,NULL,'ПАО \"Альфа-Банк\"','http://www.alfabank.ua',2,123456),(690,NULL,NULL,NULL,NULL,'ПАО \"Банк \"Украинский капитал\"','http://www.ukrcapital.com.ua',2,123456),(691,NULL,NULL,NULL,NULL,'АО \"ОТП Банк\"','http://www.otpbank.com.ua/',2,123456),(692,NULL,NULL,NULL,NULL,'ПАО \"Дельта Банк\"','http://www.deltabank.com.ua',2,123456),(693,NULL,NULL,NULL,NULL,'АО \"Сбербанк России\"','http://sberbank.ua/',2,123456),(694,NULL,NULL,NULL,NULL,'АБ \"Пивденный\"','http://www.bank.com.ua/',2,123456),(695,NULL,NULL,NULL,NULL,'ПУАО \"ФИДОБАНК\"','http://www.fidobank.ua',2,123456),(696,NULL,NULL,NULL,NULL,'ПАО \"Банк Первый\"','http://www.bankpershiy.com.ua/rus/',2,123456),(697,NULL,NULL,NULL,NULL,'АО \"Пиреус Банк МКБ\"','http://www.piraeusbank.ua/ua/index.html',2,123456),(698,NULL,NULL,NULL,NULL,'АО \"Укринбанк\"','http://www.ukrinbank.com/index.php?TemplateName=firstpage',2,123456),(699,NULL,NULL,NULL,NULL,'ПАОКБ \"ПРАВЭКС-БАНК\"','http://www.pravex.com/rus',2,123456),(700,NULL,NULL,NULL,NULL,'ПАО \"Платинум Банк\"','http://www.platinumbank.com.ua',2,123456),(701,NULL,NULL,NULL,NULL,'ПАО \"ВиЭс Банк\"','http://www.vsbank.com.ua/ru/index',2,123456),(702,NULL,NULL,NULL,NULL,'ПАО \"Идея Банк\"','http://www.ideabank.com.ua/',2,123456),(703,NULL,NULL,NULL,NULL,'ПАО \"АВАНТ-БАНК\"','http://www.avantbank.com.ua',2,123456),(704,NULL,NULL,NULL,NULL,'ПАО \"Банк инвестиций и сбережений\"','http://www.bisbank.com.ua/site/index.php?lang=RU',2,123456),(705,NULL,NULL,NULL,NULL,'ПАО \"КБ \"АКТИВ-БАНК\"','http://www.abank.com.ua/index.php?lang=ru',2,123456),(706,NULL,NULL,NULL,NULL,'ПАО \"Укргазпромбанк\"','http://www.ugpb.com/index.php?cmd=page&pid=1',2,123456),(707,NULL,NULL,NULL,NULL,'ПАО \"Банк Михайловский\"','http://www.mbank.kiev.ua/ru/index.htm',2,123456),(708,NULL,NULL,NULL,NULL,'ПАО \"Банк Камбио\"','http://www.cambio.com.ua/site/ru/mainpage/',2,123456),(709,NULL,NULL,NULL,NULL,'ПАО \"Вернум банк\"','http://vernumbank.com/',2,123456),(710,NULL,NULL,NULL,NULL,'ПАО КБ \"Финансовая Инициатива\"','http://www.the-bank.com.ua/site/index.php?lang=RU',2,123456),(711,NULL,NULL,NULL,NULL,'ПАО \"Банк \"Национальные инвестиции\"','',2,123456),(712,NULL,NULL,NULL,NULL,'ПАТ \"Банк Форвард\"','http://forward-bank.com/',3,123456),(713,NULL,NULL,NULL,NULL,'ПАТ \"Банк Форум\"','http://www.forum.ua/',3,123456),(714,NULL,NULL,NULL,NULL,'ПАТ \"УкрСиббанк\"','http://www.ukrsibbank.com/',3,123456),(715,'','',NULL,'','ПАТ \"УніКредит Банк\"','http://www.unicreditbank.com.ua/',2,123456),(716,NULL,NULL,NULL,NULL,'ПАТ КБ \"Акордбанк\"','http://www.accordbank.com.ua/',4,123456),(717,NULL,NULL,NULL,NULL,'ПАТ \"Актабанк\"','http://www.aktabank.com/ua/',4,123456),(718,NULL,NULL,NULL,NULL,'ПАТ \"Банк 3/4\"','http://www.bank34.ua/',4,123456),(719,NULL,NULL,NULL,NULL,'ПАТ \"Банк Камб?о\"','http://www.cambio.com.ua/',4,123456),(720,NULL,NULL,NULL,NULL,'ПАТ \"Банк К?пру\"','http://www.bankofcyprus.com.ua/',4,123456),(721,NULL,NULL,NULL,NULL,'ПАТ \"Банк Кредит Дн?про\"','http://creditdnepr.com.ua/ukr/',4,123456),(722,NULL,NULL,NULL,NULL,'ПАТ \"Банк Меркур?й\"','http://www.mercury-bank.com/',4,123456),(723,NULL,NULL,NULL,NULL,'ПАТ \"Банк Фам?льний\"','http://www.fbank.com.ua/',4,123456),(724,NULL,NULL,NULL,NULL,'ПАТ \"Всеукраїнський Банк Розвитку\"','http://www.vbr-bank.com.ua/ua/',4,123456),(725,NULL,NULL,NULL,NULL,'ПАТ Банк \"Грант\"','http://www.grant.kharkov.ua/',4,123456),(726,NULL,NULL,NULL,NULL,'ПАТ \"Європромбанк\"','http://europrombank.com/',4,123456),(727,'','',NULL,'','ПАТ АКБ \"Індустріалбанк\"','http://www.industrialbank.ua/',4,123456),(728,'','',NULL,'','ПАТ АКБ \"Капітал\"','http://bank-capital.com/',4,123456),(729,NULL,NULL,NULL,NULL,'ПАТ \"Кредобанк\"','http://www.kredobank.com.ua/',4,123456),(730,NULL,NULL,NULL,NULL,'ПАТ АКБ \"Льв?в\"','http://www.banklviv.com/',4,123456),(731,NULL,NULL,NULL,NULL,'ПАТ \"Марф?н Банк\"','http://marfinbank.ua/UA/Pages/Home.aspx',4,123456),(732,NULL,NULL,NULL,NULL,'ПАТ \"МетаБанк\"','http://www.mbank.com.ua/',4,123456),(733,NULL,NULL,NULL,NULL,'ПАТ \"М?сто банк\"','http://www.mistobank.com.ua/',4,123456),(734,NULL,NULL,NULL,NULL,'ПАТ Банк \"Морський\"','http://www.bank.gov.ua/control/uk/publish/article?art_id=7361501',4,123456),(735,NULL,NULL,NULL,NULL,'ПАТ \"Мотор-банк\"','http://motor-bank.ua/',4,123456),(736,NULL,NULL,NULL,NULL,'ПАТ \"Перший ?нвестиц?йний Банк\"','http://www.pinbank.ua/',4,123456),(737,NULL,NULL,NULL,NULL,'ПАТ \"Перший Український М?жнародний Банк\"','http://pumb.ua/',4,123456),(738,NULL,NULL,NULL,NULL,'ПАТ КБ \"П?вденкомбанк\"','http://www.pivdencombank.com/index.php?id=home_ukr',4,123456),(739,NULL,NULL,NULL,NULL,'ПАТ \"АБ \"Порто-Франко\"','http://www.porto-franco.com/',4,123456),(740,NULL,NULL,NULL,NULL,'ПАТ \"ПроКредит Банк\"','http://www.procreditbank.com.ua/',4,123456),(741,NULL,NULL,NULL,NULL,'ПАТ КБ \"Промекономбанк\"','http://www.peb.com.ua/',4,123456),(742,NULL,NULL,NULL,NULL,'ПАТ \"Промислово-ф?нансовий банк\"','http://www.pfb.com.ua/',4,123456),(743,NULL,NULL,NULL,NULL,'ПАТ \"ПроФ?н Банк\"','http://www.profinbank.com/',4,123456),(744,NULL,NULL,NULL,NULL,'ПАТ \"АБ \"Радабанк\"','http://www.radabank.com.ua/',4,123456),(745,NULL,NULL,NULL,NULL,'ПАТ \"Реал Банк\"','http://realbank.com.ua/',4,123456),(746,NULL,NULL,NULL,NULL,'ПАТ \"Банк Ренесанс Кап?тал\"','http://www.rccf.ua/',4,123456),(747,NULL,NULL,NULL,NULL,'АТ \"ТАСкомбанк\"','http://www.tascombank.com.ua/index.php?lang=ukrainian',4,123456),(748,NULL,NULL,NULL,NULL,'ПАТ АБ \"Укоопсп?лка\"','http://www.bankukoopspilka.kiev.ua/',4,123456),(749,NULL,NULL,NULL,NULL,'ПАТ \"Український Б?знес Банк\"','http://ua.ubb.com.ua/',4,123456),(750,NULL,NULL,NULL,NULL,'ПАТ \"КБ \"Український ф?нансовий св?т\"','http://www.fg.gov.ua/payments/ufs-83.html',4,123456),(751,NULL,NULL,NULL,NULL,'ПАТ \"Ф?нбанк\"','http://www.finbank.odessa.ua/uk/',4,123456),(752,NULL,NULL,NULL,NULL,'ПАТ \"Ф?нростбанк\"','http://www.frb.com.ua/ua/',4,123456),(753,NULL,NULL,NULL,NULL,'ПАТ \"Автокразбанк\"','http://www.avtokrazbank.ua/',5,123456),(754,NULL,NULL,NULL,NULL,'ПАТ \"Банк Богуслав\"','http://www.bankboguslav.com.ua/',5,123456),(755,NULL,NULL,NULL,NULL,'ПАТ \"Банк Велес\"','http://www.bankveles.com/',5,123456),(756,NULL,NULL,NULL,NULL,'ПАТ Банк \"Контракт\"','http://www.kontrakt.ua/',5,123456),(757,NULL,NULL,NULL,NULL,'ПАТ \"Банк Народний Кап?тал\"','http://www.nkbank.com.ua/',5,123456),(758,NULL,NULL,NULL,NULL,'ПАТ \"Банк Нац?ональний Кредит\"','http://bnk.ua/ua/golovna',5,123456),(759,NULL,NULL,NULL,NULL,'ПАТ \"КБ \"Дан?ель\"','http://www.danielbank.kiev.ua/',5,123456),(760,NULL,NULL,NULL,NULL,'АТ \"ЄВРОГАЗБАНК\"','http://www.egb.kiev.ua/uk.html',5,123456),(761,NULL,NULL,NULL,NULL,'ПАТ \"Зах?д?нкомбанк\"','http://www.inkom.lutsk.ua/',5,123456),(762,NULL,NULL,NULL,NULL,'ПАТ \"?нтеграл-банк\"','http://www.integral.com.ua/uk',5,123456),(763,NULL,NULL,NULL,NULL,'ПАТ Банк \"Київська Русь\"','http://www.kruss.kiev.ua/',5,123456),(764,NULL,NULL,NULL,NULL,'ПАТ \"Комерц?йний ?ндустр?альний Банк\"','http://cib.com.ua/',5,123456),(765,NULL,NULL,NULL,NULL,'ПАТ \"Ком?нвестБанк\"','http://www.atcominvestbank.com/',5,123456),(766,NULL,NULL,NULL,NULL,'ПАТ \"М?ський комерц?йний банк\"','http://ua.citycommercebank.com/',5,123456),(767,NULL,NULL,NULL,NULL,'ПАТ \"Окс? Банк\"','http://www.okcibank.com.ua/',5,123456),(768,NULL,NULL,NULL,NULL,'ПАТ \"Полтава-банк\"','http://www.poltavabank.com/',5,123456),(769,NULL,NULL,NULL,NULL,'ПАТ \"Радикал Банк\"','http://radicalbank.ua/',5,123456),(770,NULL,NULL,NULL,NULL,'ПАТ \"АКБ \"Траст-кап?тал\"','http://www.tc-bank.com/',5,123456),(771,NULL,NULL,NULL,NULL,'ПАТ \"Український профес?йний банк\"','http://upb.ua/',5,123456),(772,NULL,NULL,NULL,NULL,'ПАТ \"Укргазбанк\"','http://www.ukrgasbank.com.ua/',5,123456),(773,NULL,NULL,NULL,NULL,'ПАТ \"КБ \"Ф?нансова ?н?ц?атива\"','http://www.finbank.com.ua/',5,123456),(774,NULL,NULL,NULL,NULL,'ПАТ \"Юнекс Банк\"','http://www.unexbank.ua/',5,123456),(775,'','',NULL,'','ПАО \"Креди Агриколь Банк\"','http://credit-agricole.com.ua/rus/',3,1234),(779,'','',NULL,'','ПАО \"Креди Агриколь Банк\"','http://credit-agricole.com.ua/rus/',2,1234),(780,'','',NULL,'','ПАО \"Креди Агриколь Банк\"','http://credit-agricole.com.ua/rus/',2,12345),(781,'bank_atm781.png','bank_off781.png',NULL,'bank_logo781.jpg','Test Bank','fhjshfsjkhfk',-1,1111);
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
  `atm_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_3mka83yrsd3tyj5mvb1ta1buy` (`atm_id`),
  KEY `FK_1x3vdhb5vv8eu5708riqe07wc` (`user_id`),
  CONSTRAINT `FK_1x3vdhb5vv8eu5708riqe07wc` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_3mka83yrsd3tyj5mvb1ta1buy` FOREIGN KEY (`atm_id`) REFERENCES `atm` (`Id`)
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
  KEY `FK_ifxvxbp4qteqok8f2kvid1drr` (`atm_id`),
  KEY `FK_8pr7wrtwt7dh3ehoh67f6ao18` (`user_id`),
  CONSTRAINT `FK_8pr7wrtwt7dh3ehoh67f6ao18` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_ifxvxbp4qteqok8f2kvid1drr` FOREIGN KEY (`atm_id`) REFERENCES `atm` (`Id`)
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
  `parser_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_t8a4thouaexpidhdi7paj0k1r` (`parser_id`),
  CONSTRAINT `FK_t8a4thouaexpidhdi7paj0k1r` FOREIGN KEY (`parser_id`) REFERENCES `parsers` (`Id`)
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
  `bank_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_251evea75ipggs9y7krq0ldqa` (`bank_id`),
  CONSTRAINT `FK_251evea75ipggs9y7krq0ldqa` FOREIGN KEY (`bank_id`) REFERENCES `banks` (`Id`)
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
  KEY `FK_5k3dviices5fr7560hvc81x4r` (`roles_id`),
  CONSTRAINT `FK_4edf6ibqo9873ixvuyri8xua2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_5k3dviices5fr7560hvc81x4r` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(1,2);
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
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin.jpg','admin@mail.com','','admin','1'),(2,'defaultUserAvatar.jpg','user@mail.com','','user','1');
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

-- Dump completed on 2014-11-28 17:09:03
