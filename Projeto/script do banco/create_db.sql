-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema estanteuniversitaria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema estanteuniversitaria
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `estanteuniversitaria` DEFAULT CHARACTER SET utf8 ;
USE `estanteuniversitaria` ;

-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`usuario` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `cep` TEXT NULL DEFAULT NULL,
  `endereco` TEXT NULL DEFAULT NULL,
  `endNum` INT(11) NULL DEFAULT NULL,
  `endComplemento` TEXT NULL DEFAULT NULL,
  `estado` TEXT NULL DEFAULT NULL,
  `cidade` TEXT NULL DEFAULT NULL,
  `universidade` TEXT NULL DEFAULT NULL,
  `telefone` TEXT NULL DEFAULT NULL,
  `celular` TEXT NULL DEFAULT NULL,
  `nome` TEXT NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`livro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`livro` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`livro` (
  `idLivro` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` TEXT NULL DEFAULT NULL,
  `autor` TEXT NULL DEFAULT NULL,
  `editora` TEXT NULL DEFAULT NULL,
  `edicao` TEXT NULL DEFAULT NULL,
  `ano` INT(11) NULL DEFAULT NULL,
  `condicao` TEXT NULL DEFAULT NULL,
  `wishlist` TINYINT(1) NULL DEFAULT NULL,
  `idUsuario` INT(11) NOT NULL,
  `imagem` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`idLivro`),
  INDEX `fk_idUsuario_livro` (`idUsuario` ASC),
  CONSTRAINT `fk_idUsuario_livro`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`anuncio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`anuncio` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`anuncio` (
  `idAnuncio` INT(11) NOT NULL AUTO_INCREMENT,
  `idLivro` INT(11) NULL DEFAULT NULL,
  `tipo` TEXT NULL DEFAULT NULL,
  `valor` DOUBLE NULL DEFAULT NULL,
  `idUsuario` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idAnuncio`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_livro_idx` (`idLivro` ASC),
  CONSTRAINT `fk_livro_anuncio`
    FOREIGN KEY (`idLivro`)
    REFERENCES `estanteuniversitaria`.`livro` (`idLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_anuncio`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`transacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`transacao` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`transacao` (
  `idTransacao` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuarioAnunciante` INT(11) NULL DEFAULT NULL,
  `idUsuarioCliente` INT(11) NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `idAnuncio` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idTransacao`),
  INDEX `id_usuario_anunciante_idx` (`idUsuarioAnunciante` ASC),
  INDEX `fk_usuario_cliente_idx` (`idUsuarioCliente` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_anuncio_anunciante`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteuniversitaria`.`anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_anunciante`
    FOREIGN KEY (`idUsuarioAnunciante`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_cliente`
    FOREIGN KEY (`idUsuarioCliente`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`avaliacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`avaliacao` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`avaliacao` (
  `idAvaliacao` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuarioAvaliado` INT(11) NULL DEFAULT NULL,
  `idUsuarioAvaliador` INT(11) NULL DEFAULT NULL,
  `nota` INT(11) NULL DEFAULT NULL,
  `review` TEXT NULL DEFAULT NULL,
  `idTransacao` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idAvaliacao`),
  INDEX `fk_usuario_avaliado_idx` (`idUsuarioAvaliado` ASC),
  INDEX `fk_usuario_avaliador_idx` (`idUsuarioAvaliador` ASC),
  INDEX `fk_transacao_idx` (`idTransacao` ASC),
  CONSTRAINT `fk_transacao_avaliacao`
    FOREIGN KEY (`idTransacao`)
    REFERENCES `estanteuniversitaria`.`transacao` (`idTransacao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_avaliado`
    FOREIGN KEY (`idUsuarioAvaliado`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_avaliador`
    FOREIGN KEY (`idUsuarioAvaliador`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`chat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`chat` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`chat` (
  `idChat` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuario1` INT(11) NULL DEFAULT NULL,
  `idUsuario2` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idChat`),
  INDEX `fk_usuario1_idx` (`idUsuario1` ASC),
  INDEX `fk_usuario2_idx` (`idUsuario2` ASC),
  CONSTRAINT `fk_usuario1_chat`
    FOREIGN KEY (`idUsuario1`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario2_chat`
    FOREIGN KEY (`idUsuario2`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`comentario` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`comentario` (
  `idComentario` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` INT(11) NULL DEFAULT NULL,
  `idAnuncio` INT(11) NULL DEFAULT NULL,
  `texto` TEXT NULL DEFAULT NULL,
  `data` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idComentario`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_anuncio_comentario`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteuniversitaria`.`anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_comentario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`favoritos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`favoritos` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`favoritos` (
  `idFavorito` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` INT(11) NULL DEFAULT NULL,
  `idAnuncio` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idFavorito`),
  INDEX `fk_usuario_idx` (`idUsuario` ASC),
  INDEX `fk_anuncio_idx` (`idAnuncio` ASC),
  CONSTRAINT `fk_anuncio_favoritos`
    FOREIGN KEY (`idAnuncio`)
    REFERENCES `estanteuniversitaria`.`anuncio` (`idAnuncio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_favoritos`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `estanteuniversitaria`.`mensagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `estanteuniversitaria`.`mensagem` ;

CREATE TABLE IF NOT EXISTS `estanteuniversitaria`.`mensagem` (
  `idMensagem` INT(11) NOT NULL AUTO_INCREMENT,
  `idChat` INT(11) NULL DEFAULT NULL,
  `idUsuarioEnvia` INT(11) NULL DEFAULT NULL,
  `idUsuarioRecebe` INT(11) NULL DEFAULT NULL,
  `mensagem` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`idMensagem`),
  INDEX `fk_usuario_envia_idx` (`idUsuarioEnvia` ASC),
  INDEX `fk_usuario_recebe_idx` (`idUsuarioRecebe` ASC),
  INDEX `fk_chat_idx` (`idChat` ASC),
  CONSTRAINT `fk_chat_mensagem`
    FOREIGN KEY (`idChat`)
    REFERENCES `estanteuniversitaria`.`chat` (`idChat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_envia`
    FOREIGN KEY (`idUsuarioEnvia`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_recebe`
    FOREIGN KEY (`idUsuarioRecebe`)
    REFERENCES `estanteuniversitaria`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
