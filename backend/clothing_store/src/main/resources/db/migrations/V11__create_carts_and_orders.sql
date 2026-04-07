ALTER TABLE produtos_variacoes
ADD COLUMN estoque INTEGER NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS carrinhos (
    id       INTEGER      NOT NULL AUTO_INCREMENT UNIQUE,
    id_pessoa INTEGER     NOT NULL,
    status   VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    criado_em   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_carrinho_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoas_fisicas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS itens_carrinho (
    id              INTEGER      NOT NULL AUTO_INCREMENT UNIQUE,
    id_carrinho     INTEGER      NOT NULL,
    id_variacao     INTEGER      NOT NULL,
    quantidade      INTEGER      NOT NULL,
    preco_unitario  DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_carrinho  FOREIGN KEY (id_carrinho) REFERENCES carrinhos(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_variacao  FOREIGN KEY (id_variacao) REFERENCES produtos_variacoes(id)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id                INTEGER       NOT NULL AUTO_INCREMENT UNIQUE,
    id_pessoa         INTEGER       NOT NULL,
    status            VARCHAR(30)   NOT NULL DEFAULT 'PENDING',
    valor_total       DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    metodo_pagamento  VARCHAR(50),
    criado_em         TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_pedido_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoas_fisicas(id)
);

CREATE TABLE IF NOT EXISTS itens_pedido (
    id             INTEGER       NOT NULL AUTO_INCREMENT UNIQUE,
    id_pedido      INTEGER       NOT NULL,
    id_variacao    INTEGER       NOT NULL,
    quantidade     INTEGER       NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal       DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_pedido   FOREIGN KEY (id_pedido)   REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_ped_var  FOREIGN KEY (id_variacao) REFERENCES produtos_variacoes(id)
);