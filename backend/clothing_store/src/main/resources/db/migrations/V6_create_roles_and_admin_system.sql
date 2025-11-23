CREATE TABLE IF NOT EXISTS `papeis` (
    `id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
    `nome_papel` VARCHAR(50) NOT NULL UNIQUE,
    `descricao` VARCHAR(255),
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `usuarios_papeis` (
    `id_usuario` INTEGER NOT NULL,
    `id_papel` INTEGER NOT NULL,
    PRIMARY KEY(`id_usuario`, `id_papel`),
    FOREIGN KEY (`id_usuario`) REFERENCES `usuarios`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_papel`) REFERENCES `papeis`(`id`) ON DELETE CASCADE
);

ALTER TABLE `usuarios`
ADD COLUMN `is_ativo` BOOLEAN NOT NULL DEFAULT true;

INSERT INTO `papeis` (`nome_papel`, `descricao`) VALUES
('ROLE_USER', 'Papel de usuario padrao com permissões basicas'),
('ROLE_ADMIN', 'Papel de administrador com permissões para gerenciamento de produtos e pedidos'),
('ROLE_SUPER_ADMIN', 'Super Administrador acesso total ao sistema');