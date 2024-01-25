INSERT INTO TB_PERFIL(DESCRICAO, AUTORIEDADE) VALUES ('Administrador', 'ROLE_ADMIN');
INSERT INTO TB_PERFIL(DESCRICAO, AUTORIEDADE) VALUES ('Cliente', 'ROLE_CLIENTE');

INSERT INTO TB_SALDO_TOTAL_USUARIO(VALOR, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES (0, NOW(), NULL);
INSERT INTO TB_USUARIO(NOME, SOBRENOME, EMAIL, DATA_CADASTRO, DATA_ATUALIZACAO, ID_SALDO_TOTAL) VALUES ('José', 'Silva', 'jose@email.com', NOW(), NULL, 1);
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES (1, 1);
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES (1, 2);

INSERT INTO TB_SALDO_TOTAL_USUARIO(VALOR, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES (0, NOW(), NULL);
INSERT INTO TB_USUARIO(NOME, SOBRENOME, EMAIL, DATA_CADASTRO, DATA_ATUALIZACAO, ID_SALDO_TOTAL) VALUES ('Marcos', 'Silva', 'marcos@email.com', NOW(), NULL, 2);
INSERT INTO TB_USUARIO_PERFIL(ID_USUARIO, ID_PERFIL) VALUES (2, 2);