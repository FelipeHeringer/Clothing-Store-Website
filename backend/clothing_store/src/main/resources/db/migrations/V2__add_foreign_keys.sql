-- COMPRAS references USUARIOS and METODOS_PAGAMENTO
ALTER TABLE compras
ADD CONSTRAINT fk_compras_pessoa
FOREIGN KEY (id_pessoa) REFERENCES pessoas_fisicas(id);

ALTER TABLE compras
ADD CONSTRAINT fk_compras_metodo
FOREIGN KEY (id_mtd_pagamento) REFERENCES metodos_pagamento(id);

-- ITENS_COMPRA references COMPRAS and PRODUTOS_VARIACOES
ALTER TABLE itens_compra
ADD CONSTRAINT fk_itens_compra_compra
FOREIGN KEY (id_compra) REFERENCES compras(id);

ALTER TABLE itens_compra
ADD CONSTRAINT fk_itens_compra_produto_variacao
FOREIGN KEY (id_produto_variacao) REFERENCES produtos_variacoes(id);

-- PRODUTOS_VARIACOES references PRODUTOS, CORES, and TAMANHOS
ALTER TABLE produtos_variacoes
ADD CONSTRAINT fk_produtos_variacoes_produto
FOREIGN KEY (id_produto) REFERENCES produtos(id);

ALTER TABLE produtos_variacoes
ADD CONSTRAINT fk_produtos_variacoes_cor
FOREIGN KEY (id_cor) REFERENCES cores(id);

ALTER TABLE produtos_variacoes
ADD CONSTRAINT fk_produtos_variacoes_tamanho
FOREIGN KEY (id_tamanho) REFERENCES tamanhos(id);

-- PRODUTOS references COLECOES and CATEGORIAS
ALTER TABLE produtos
ADD CONSTRAINT fk_produtos_colecao
FOREIGN KEY (id_colecao) REFERENCES colecoes(id);

ALTER TABLE produtos
ADD CONSTRAINT fk_produtos_categoria
FOREIGN KEY (id_categoria) REFERENCES categorias(id);

-- ENDERECOS_PESSOAS references ENDERECOS and PESSOAS_FISICAS
ALTER TABLE enderecos_pessoas
ADD CONSTRAINT fk_enderecos_pessoas_endereco
FOREIGN KEY (id_endereco) REFERENCES enderecos(id);

ALTER TABLE enderecos_pessoas
ADD CONSTRAINT fk_enderecos_pessoas_pessoa
FOREIGN KEY (id_pessoa) REFERENCES pessoas_fisicas(id);

-- PESSOAS_FISICAS references USUARIOS
ALTER TABLE pessoas_fisicas
ADD CONSTRAINT fk_pessoas_fisicas_usuario
FOREIGN KEY (id_usuario) REFERENCES usuarios(id);

-- USUARIOS references FAVORITOS
ALTER TABLE usuarios
ADD CONSTRAINT fk_usuarios_favoritos
FOREIGN KEY (id_favoritos) REFERENCES favoritos(id);

-- ENDERECOS references CEP, CIDADES, ESTADOS
ALTER TABLE enderecos
ADD CONSTRAINT fk_enderecos_cep
FOREIGN KEY (id_cep) REFERENCES CEP(id);

ALTER TABLE enderecos
ADD CONSTRAINT fk_enderecos_cidade
FOREIGN KEY (id_cidade) REFERENCES cidades(id);

ALTER TABLE enderecos
ADD CONSTRAINT fk_enderecos_estado
FOREIGN KEY (id_estado) REFERENCES estados(id);
