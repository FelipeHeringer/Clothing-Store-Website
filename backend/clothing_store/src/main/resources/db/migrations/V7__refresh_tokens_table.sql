CREATE TABLE IF NOT EXISTS refresh_tokens (
    id INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
    token VARCHAR(500) NOT NULL UNIQUE,
    id_usuario INTEGER NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    revogado BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMP NOT NULL,
    revogado_em TIMESTAMP,
    PRIMARY KEY(id)
);

ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_tokens_usuario
FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE;