CREATE TABLE imagens_produtos (
  id          INTEGER       NOT NULL AUTO_INCREMENT UNIQUE,
  id_produto  INTEGER       NOT NULL,
  id_variacao INTEGER       NULL,
  url         TEXT          NOT NULL,
  tipo        VARCHAR(20)   NOT NULL CHECK (tipo IN ('MAIN', 'CAROUSEL')),
  posicao     INTEGER       NOT NULL DEFAULT 0,
  criado_em   TIMESTAMP     NOT NULL DEFAULT now(),

  PRIMARY KEY (id),

  CONSTRAINT unique_produto_imagem_posicao
    UNIQUE (id_produto, id_variacao, tipo, posicao),

  FOREIGN KEY (id_produto)  REFERENCES produtos(id)          ON DELETE CASCADE,
  FOREIGN KEY (id_variacao) REFERENCES produtos_variacoes(id) ON DELETE CASCADE
);

CREATE INDEX idx_produto_imagens_id_produto ON imagens_produtos(id_produto);
CREATE INDEX idx_produto_imagens_id_variacao ON imagens_produtos(id_variacao);
