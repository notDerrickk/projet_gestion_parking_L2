-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for projet_parking
CREATE DATABASE IF NOT EXISTS `projet_parking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `projet_parking`;

-- Dumping structure for table projet_parking.abonnements
CREATE TABLE IF NOT EXISTS `abonnements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` int(11) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_abonnement_user` (`utilisateur_id`),
  CONSTRAINT `fk_abonnement_user` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.abonnements: ~1 rows (approximately)
REPLACE INTO `abonnements` (`id`, `utilisateur_id`, `date_debut`, `date_fin`, `montant`) VALUES
	(1, 2, '2025-10-28', '2026-10-28', 75.00);

-- Dumping structure for table projet_parking.paiements
CREATE TABLE IF NOT EXISTS `paiements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  `date_paiement` datetime NOT NULL,
  `moyen` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_paiement_ticket` (`ticket_id`),
  CONSTRAINT `fk_paiement_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.paiements: ~0 rows (approximately)

-- Dumping structure for table projet_parking.parking
CREATE TABLE IF NOT EXISTS `parking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(200) NOT NULL,
  `adresse` varchar(300) DEFAULT NULL,
  `nb_places_totales` int(11) DEFAULT 0,
  `tarif_abonnement` decimal(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.parking: ~1 rows (approximately)
REPLACE INTO `parking` (`id`, `nom`, `adresse`, `nb_places_totales`, `tarif_abonnement`) VALUES
	(10, 'Parking Valin de la Vaissiere', '1 Place Valin de la Vaissiere', 50, 75.00);

-- Dumping structure for table projet_parking.places
CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) NOT NULL,
  `etat` tinyint(1) NOT NULL DEFAULT 0,
  `type` varchar(50) NOT NULL,
  `parking_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_places_numero_parking` (`numero`,`parking_id`),
  KEY `fk_places_parking` (`parking_id`),
  CONSTRAINT `fk_places_parking` FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.places: ~50 rows (approximately)
REPLACE INTO `places` (`id`, `numero`, `etat`, `type`, `parking_id`) VALUES
	(100, 1, 1, 'Normale', 10),
	(101, 2, 0, 'Normale', 10),
	(102, 3, 0, 'Normale', 10),
	(103, 4, 0, 'Normale', 10),
	(104, 5, 0, 'Normale', 10),
	(105, 6, 0, 'Normale', 10),
	(106, 7, 0, 'Normale', 10),
	(107, 8, 0, 'Normale', 10),
	(108, 9, 0, 'Normale', 10),
	(109, 10, 0, 'Normale', 10),
	(110, 11, 0, 'Normale', 10),
	(111, 12, 0, 'Normale', 10),
	(112, 13, 0, 'Normale', 10),
	(113, 14, 0, 'Normale', 10),
	(114, 15, 0, 'Normale', 10),
	(115, 16, 0, 'Normale', 10),
	(116, 17, 0, 'Normale', 10),
	(117, 18, 0, 'Normale', 10),
	(118, 19, 0, 'Normale', 10),
	(119, 20, 0, 'Normale', 10),
	(120, 21, 0, 'Normale', 10),
	(121, 22, 0, 'Normale', 10),
	(122, 23, 0, 'Normale', 10),
	(123, 24, 0, 'Normale', 10),
	(124, 25, 0, 'Normale', 10),
	(125, 26, 0, 'Normale', 10),
	(126, 27, 0, 'Normale', 10),
	(127, 28, 0, 'Normale', 10),
	(128, 29, 0, 'Normale', 10),
	(129, 30, 0, 'Normale', 10),
	(130, 31, 0, 'Normale', 10),
	(131, 32, 0, 'Normale', 10),
	(132, 33, 0, 'Normale', 10),
	(133, 34, 0, 'Normale', 10),
	(134, 35, 0, 'Normale', 10),
	(135, 36, 0, 'Normale', 10),
	(136, 37, 0, 'Normale', 10),
	(137, 38, 0, 'Normale', 10),
	(138, 39, 0, 'Normale', 10),
	(139, 40, 0, 'Normale', 10),
	(140, 41, 0, 'Reservee', 10),
	(141, 42, 0, 'Reservee', 10),
	(142, 43, 0, 'Reservee', 10),
	(143, 44, 0, 'Reservee', 10),
	(144, 45, 0, 'Reservee', 10),
	(145, 46, 1, 'Reservee', 10),
	(146, 47, 0, 'Reservee', 10),
	(147, 48, 0, 'Reservee', 10),
	(148, 49, 0, 'Reservee', 10),
	(149, 50, 0, 'Reservee', 10);

-- Dumping structure for table projet_parking.tickets
CREATE TABLE IF NOT EXISTS `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `immatriculation` varchar(30) NOT NULL,
  `heure_entree` datetime NOT NULL,
  `heure_sortie` datetime DEFAULT NULL,
  `montant` decimal(10,2) DEFAULT 0.00,
  `est_paye` tinyint(1) NOT NULL DEFAULT 0,
  `place_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.tickets: ~0 rows (approximately)
REPLACE INTO `tickets` (`id`, `immatriculation`, `heure_entree`, `heure_sortie`, `montant`, `est_paye`, `place_id`) VALUES
	(13, 'AB-750-BS', '2025-12-13 02:50:23', NULL, 0.00, 0, 100);

-- Dumping structure for table projet_parking.utilisateurs
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role` enum('ADMIN','CLIENT') NOT NULL DEFAULT 'CLIENT',
  `place_id` int(11) DEFAULT NULL,
  `immatriculation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_util_place` (`place_id`),
  CONSTRAINT `fk_util_place` FOREIGN KEY (`place_id`) REFERENCES `places` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projet_parking.utilisateurs: ~2 rows (approximately)
REPLACE INTO `utilisateurs` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `role`, `place_id`, `immatriculation`) VALUES
	(1, 'Dupont', 'Jean', 'admin@parking.com', 'admin123', 'ADMIN', NULL, NULL),
	(2, 'Martin', 'Sophie', 'sophie.martin@mail.com', 'client123', 'CLIENT', 145, 'HG-416-AS');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
