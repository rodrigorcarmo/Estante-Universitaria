-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema estanteUniversitaria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema estanteUniversitaria
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `estanteUniversitaria` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `estanteUniversitaria` ;

-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Usuario` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Usuario` (
  `idUsuario` INT NOT NULL,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `cep` TEXT NULL,
  `endereco` TEXT NULL,
  `endNum` INT NULL,
  `endComplemetno` TEXT NULL,
  `estado` TEXT NULL,
  `cidade` TEXT NULL,
  `universidade` TEXT NULL,
  `telefone` TEXT NULL,
  `celular` TEXT NULL,
  `nome` TEXT NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Livro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Livro` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Livro` (
  `idLivro` INT NOT NULL,
  `nome` TEXT NULL,
  `autor` TEXT NULL,
  `editora` TEXT NULL,
  `edicao` TEXT NULL,
  `ano` INT NULL,
  `condicao` TEXT NULL,
  PRIMARY KEY (`idLivro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Anuncio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Anuncio` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Anuncio` (
  `idAnuncio` INT NOT NULL,
  `idLivro` INT NULL,
  `tipo` TEXT NULL,
  `valor` DOUBLE NULL,
  `idUsuario` INT NULL,
  PRIMARY KEY (`idAnuncio`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_livro_idx` (`idLivro` ASC),
  CONSTRAINT `fk_livro_anuncio`
    FOREIGN KEY (`idLivro`)
    REFERENCES `estanteUniversitaria`.`Livro` (`idLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_anuncio`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Transacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Transacao` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Transacao` (
  `idTransacao` INT NOT NULL,
  `idUsuarioAnunciante` INT NULL,
  `idUsuarioCliente` INT NULL,
  `status` INT NULL,
  `idAnuncio` INT NULL,
  PRIMARY KEY (`idTransacao`),
  INDEX `id_usuario_anunciante_idx` (`idUsuarioAnunciante` ASC),
  INDEX `fk_usuario_cliente_idx` (`idUsuarioCliente` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_usuario_anunciante`
    FOREIGN KEY (`idUsuarioAnunciante`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_cliente`
    FOREIGN KEY (`idUsuarioCliente`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_anuncio_anunciante`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteUniversitaria`.`Anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Avaliacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Avaliacao` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Avaliacao` (
  `idAvaliacao` INT NOT NULL,
  `idUsuarioAvaliado` INT NULL,
  `idUsuarioAvaliador` INT NULL,
  `nota` INT NULL,
  `review` TEXT NULL,
  `idTransacao` INT NULL,
  PRIMARY KEY (`idAvaliacao`),
  INDEX `fk_usuario_avaliado_idx` (`idUsuarioAvaliado` ASC),
  INDEX `fk_usuario_avaliador_idx` (`idUsuarioAvaliador` ASC),
  INDEX `fk_transacao_idx` (`idTransacao` ASC),
  CONSTRAINT `fk_usuario_avaliado`
    FOREIGN KEY (`idUsuarioAvaliado`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_avaliador`
    FOREIGN KEY (`idUsuarioAvaliador`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transacao_avaliacao`
    FOREIGN KEY (`idTransacao`)
    REFERENCES `estanteUniversitaria`.`Transacao` (`idTransacao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Wishlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Wishlist` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Wishlist` (
  `idWishlist` INT NOT NULL,
  `idLivro` INT NULL,
  `idUsuario` INT NULL,
  PRIMARY KEY (`idWishlist`),
  INDEX `fk_livro_idx` (`idLivro` ASC),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  CONSTRAINT `fk_livro_w`
    FOREIGN KEY (`idLivro`)
    REFERENCES `estanteUniversitaria`.`Livro` (`idLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_w`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Favoritos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Favoritos` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Favoritos` (
  `idFavorito` INT NOT NULL,
  `idUsuario` INT NULL,
  `idAnuncio` INT NULL,
  PRIMARY KEY (`idFavorito`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_usuario_favoritos`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_anuncio_favoritos`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteUniversitaria`.`Anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Comentario` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Comentario` (
  `idComentario` INT NOT NULL,
  `idUsuario` INT NULL,
  `idAnuncio` INT NULL,
  `texto` TEXT NULL,
  `data` DATE NULL,
  PRIMARY KEY (`idComentario`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_usuario_comentario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_anuncio_comentario`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteUniversitaria`.`Anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Chat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Chat` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Chat` (
  `idChat` INT NOT NULL,
  `idUsuario1` INT NULL,
  `idUsuario2` INT NULL,
  PRIMARY KEY (`idChat`),
  INDEX `fk_usuario1_idx` (`idUsuario1` ASC),
  INDEX `fk_usuario2_idx` (`idUsuario2` ASC),
  CONSTRAINT `fk_usuario1_chat`
    FOREIGN KEY (`idUsuario1`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario2_chat`
    FOREIGN KEY (`idUsuario2`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estanteUniversitaria`.`Mensagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteUniversitaria`.`Mensagem` ;

CREATE TABLE IF NOT EXISTS `estanteUniversitaria`.`Mensagem` (
  `idMensagem` INT NOT NULL,
  `idChat` INT NULL,
  `idUsuarioEnvia` INT NULL,
  `idUsuarioRecebe` INT NULL,
  `mensagem` TEXT NULL,
  PRIMARY KEY (`idMensagem`),
  INDEX `fk_usuario_envia_idx` (`idUsuarioEnvia` ASC),
  INDEX `fk_usuario_recebe_idx` (`idUsuarioRecebe` ASC),
  INDEX `fk_chat_idx` (`idChat` ASC),
  CONSTRAINT `fk_chat_mensagem`
    FOREIGN KEY (`idChat`)
    REFERENCES `estanteUniversitaria`.`Chat` (`idChat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_envia`
    FOREIGN KEY (`idUsuarioEnvia`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_recebe`
    FOREIGN KEY (`idUsuarioRecebe`)
    REFERENCES `estanteUniversitaria`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
