ALTER TABLE `vertice_database`.`estados` 
ADD COLUMN `uf` VARCHAR(2) NOT NULL AFTER `nome_estado`,
ADD UNIQUE INDEX `uf_UNIQUE` (`uf` ASC) VISIBLE;
;