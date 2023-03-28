CREATE DATABASE IF NOT EXISTS `FISK` 
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `JOUEURS` (
  `IdJoueur` INT NOT NULL UNSIGNED AUTO_INCREMENT,
  `motDePasse` VARCHAR(4) NOT NULL,
  `Pseudo` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`IdJoueur`));

CREATE TABLE `PARTIES` (
    `IdPartie` INT NOT NULL UNSIGNED AUTO_INCREMENT,
    `DateDebut` DATETIME NOT NULL,
    'DateFin' DATETIME,
    'IdGagnant' INT,
    PRIMARY KEY ('idPartie'),
    CONSTRAINT `fk_IdJoueur_Parties`
        FOREIGN KEY (`IdGagnant`)
        REFERENCES `JOUEURS` (`IdJoueur`));

CREATE TABLE `JOUEURS_PARTIES` (
    `IdJoueur` INT NOT NULL,
    `IdPartie` INT NOT NULL,
    PRIMARY KEY (`IdJoueur`, `IdPartie`),
    CONSTRAINT `fk_IdJoueur_Joueurs_Parties`
        FOREIGN KEY (`IdJoueur`)
        REFERENCES `JOUEURS` (`IdJoueur`),
    CONSTRAINT `fk_IdPartie_Joueurs_Parties`
        FOREIGN KEY (`IdPartie`)
        REFERENCES `PARTIES` (`IdPartie`));

CREATE TABLE `SUCCES` (
    `IdSucces` INT NOT NULL UNSIGNED AUTO_INCREMENT,
    `Nom` VARCHAR(32),
    `Description` VARCHAR(128),
    PRIMARY KEY (`IdSucces`));

CREATE TABLE `JOUEURS_SUCCES` (
    `IdJoueur` INT NOT NULL,
    `IdSucces` INT NOT NULL,
    'DateObtention' DATETIME NOT NULL,
    PRIMARY KEY (`IdJoueur`, `IdSucces`),
    CONSTRAINT `fk_IdJoueur_Joueurs_Succes`
        FOREIGN KEY (`IdJoueur`)
        REFERENCES `JOUEURS` (`IdJoueur`),
    CONSTRAINT `fk_IdSucces_Joueurs_Succes`
        FOREIGN KEY (`IdSucces`)
        REFERENCES `SUCCES` (`IdSucces`));