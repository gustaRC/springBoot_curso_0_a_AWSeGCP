LOCK TABLES `person` WRITE;

INSERT INTO `person` (`id`, `address`, `first_name`, `last_name`, `gender`) VALUES
    (1,'Onde Judas perdeu as botas','Primeiro Nome 1','Segundo nome 1', 'Male'),
    (2,'Onde Jonas perdeu as botas','Primeiro Nome 2','Segundo nome 2', 'Female'),
    (3,'Onde João perdeu as botas','Primeiro Nome 3','Segundo nome 3', 'Female');

UNLOCK TABLES;
