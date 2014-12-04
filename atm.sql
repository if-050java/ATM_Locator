/*!40100 DEFAULT CHARACTER SET utf8 */;
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

USE `atmlocator`;

INSERT INTO atm (type, state, address, latitude, longitude, bank_id) VALUES 
	(1, 1, "м. Івано-Франківськ, вул. Січових стрільців, 15", 48.919509, 24.709152, 288),
	(1, 1, "м. Івано-Франківськ, вул. Андрія Мельника, 2", 48.918838, 24.714501, 288),
	(1, 1, "м. Івано-Франківськ, вул. Гетьмана Мазепи, 38", 48.919353, 24.702641, 288),
	(1, 1, "м. Івано-Франківськ, вул. Галицька, 62", 48.933872, 24.706236, 288),
	(1, 1, "м. Івано-Франківськ, вул. Євгена Коновальця, 262Б", 48.888540, 24.709298, 288),
	(1, 1, "м. Івано-Франківськ, вул. Степана Бандери, 79", 48.907239, 24.715876, 288),
	(1, 1, "м. Івано-Франківськ, вул. Чорновола, 35", 48.916831, 24.705988, 288),
	(1, 1, "м. Івано-Франківськ, вул. Шашкевича, 4", 48.919999, 24.711851, 288);
