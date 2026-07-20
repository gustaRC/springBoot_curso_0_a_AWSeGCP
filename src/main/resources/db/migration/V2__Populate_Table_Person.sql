LOCK TABLES `person` WRITE;

INSERT INTO `person` (`id`, `address`, `first_name`, `last_name`) VALUES
    (1,'Onde Judas perdeu as botas','Primeiro Nome','Segundo nome'),
    (3,'Onde Judas perdeu as botas','Primeiro Nome','Segundo nome'),
    (4,'Onde Judas perdeu as botas','Primeiro Nome','Segundo nome');

UNLOCK TABLES;
