-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema online-pharmacy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema online-pharmacy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `online-pharmacy` DEFAULT CHARACTER SET utf8 ;
USE `online-pharmacy` ;

-- -----------------------------------------------------
-- Table `online-pharmacy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the users.',
  `name` VARCHAR(30) NOT NULL COMMENT 'This column stores information about the user name. ',
  `lastname` VARCHAR(50) NOT NULL COMMENT 'This column stores information about the user lastname.',
  `email` VARCHAR(264) NOT NULL COMMENT 'This column stores information about the user e-mail. ',
  `role` ENUM('client', 'admin', 'doctor', 'pharm') NOT NULL COMMENT 'This column stores the user role.',
  `password` VARCHAR(64) NOT NULL COMMENT 'This column stores hashcode of user password.',
  `cash` DECIMAL(10,2) UNSIGNED NULL COMMENT 'This column stores user cash. It  depends on user role. If role \'admin\', \'doctor\' or \'pharm\' then column is NULL.',
  `speciality` VARCHAR(30) NULL COMMENT 'This column stores user speciality. It  depends on user role. If role \'admin\' or \'client\' then column is NULL.',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This table stores basic information about the user.	';


-- -----------------------------------------------------
-- Table `online-pharmacy`.`substance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`substance` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the substances.',
  `name` VARCHAR(35) NOT NULL COMMENT 'This column stores information about the substance name.',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This table stores names of substances and is an inforamtion table for the search for analogues.';


-- -----------------------------------------------------
-- Table `online-pharmacy`.`drug`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`drug` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the drugs.',
  `substance_id` INT UNSIGNED NOT NULL COMMENT 'This column stores id of the active substance of the drug.',
  `name` VARCHAR(35) NOT NULL COMMENT 'This column stores information about the drug name.',
  `company` VARCHAR(35) NOT NULL COMMENT 'This column stores information about the name of  company in which drug is made.',
  `country` VARCHAR(25) NOT NULL COMMENT 'This column stores information about the name of  country in which drug is made.',
  `price` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'This column stores price of the drug.',
  `count` INT NOT NULL COMMENT 'This column stores count of the drug.',
  `dosage` INT UNSIGNED NOT NULL COMMENT 'This column stores information about drug dosage.',
  `type` ENUM('pill', 'capsule', 'solution', 'ointment', 'drops', 'syrup') NOT NULL COMMENT 'This column stores the type of the drug.',
  `is_required_recipe` TINYINT(1) UNSIGNED NOT NULL COMMENT 'This column can stores 2 values(0 or 1). 0 - this drug hasn\'t recipe. 1 - this drug has recipe.',
  PRIMARY KEY (`id`, `substance_id`),
  INDEX `fk_Drug_Substance1_idx` (`substance_id` ASC),
  CONSTRAINT `fk_Drug_Substance1`
    FOREIGN KEY (`substance_id`)
    REFERENCES `online-pharmacy`.`substance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This table stores basic information about the drug. ';


-- -----------------------------------------------------
-- Table `online-pharmacy`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`order` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the orders.',
  `client_id` INT UNSIGNED NOT NULL COMMENT 'This column stores the client id.',
  `price` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'This column stores the order price.',
  `date` DATETIME NOT NULL COMMENT 'This column stores the date and time when the order was placed.',
  `is_paid` TINYINT(1) UNSIGNED NOT NULL COMMENT 'This column can stores 2 values(0 or 1). 0 - this order isn\'t paid. 1 - this order is paid.',
  PRIMARY KEY (`id`, `client_id`),
  INDEX `fk_order_user1_idx` (`client_id` ASC),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`client_id`)
    REFERENCES `online-pharmacy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This table stores basic information about client orders';


-- -----------------------------------------------------
-- Table `online-pharmacy`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`recipe` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the recipes.',
  `drug_id` INT UNSIGNED NOT NULL COMMENT 'This column stores drug id.',
  `doctor_id` INT UNSIGNED NOT NULL COMMENT 'This column stores doctor id.',
  `client_id` INT UNSIGNED NOT NULL COMMENT 'This column stores client id.',
  `count` INT UNSIGNED NOT NULL COMMENT 'This column stores count of the drugs.',
  PRIMARY KEY (`id`, `drug_id`, `client_id`, `doctor_id`),
  INDEX `fk_recipe_drug1_idx` (`drug_id` ASC),
  INDEX `fk_recipe_user1_idx` (`doctor_id` ASC),
  INDEX `fk_recipe_user2_idx` (`client_id` ASC),
  CONSTRAINT `fk_recipe_drug1`
    FOREIGN KEY (`drug_id`)
    REFERENCES `online-pharmacy`.`drug` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_user1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_user2`
    FOREIGN KEY (`client_id`)
    REFERENCES `online-pharmacy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This table stores inforamtion about the recipe for drug.';


-- -----------------------------------------------------
-- Table `online-pharmacy`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online-pharmacy`.`order_item` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'This column stores the identifiable values ​​for the order items.',
  `order_id` INT UNSIGNED NOT NULL COMMENT 'This column stores order id.',
  `drug_id` INT UNSIGNED NOT NULL,
  `recipe_id` INT UNSIGNED NULL COMMENT 'This column stores recipe id.',
  `count` INT UNSIGNED NOT NULL COMMENT 'This column stores the quantity of a certain type of drug.',
  PRIMARY KEY (`id`, `drug_id`, `order_id`),
  INDEX `fk_order_item_drug1_idx` (`drug_id` ASC),
  INDEX `fk_order_item_order1_idx` (`order_id` ASC),
  INDEX `fk_order_item_recipe1_idx` (`recipe_id` ASC),
  CONSTRAINT `fk_order_item_drug1`
    FOREIGN KEY (`drug_id`)
    REFERENCES `online-pharmacy`.`drug` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `online-pharmacy`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `online-pharmacy`.`recipe` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This table stores infromation about one order. ';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
