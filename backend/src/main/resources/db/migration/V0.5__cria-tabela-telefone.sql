create table TB_TELEFONE(
    id bigint not null auto_increment,
    ddd varchar(2) not null,
    numero varchar(9) not null,
    descricao varchar(255),
    tipo_telefone varchar(255) not null,
    id_usuario bigint not null,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

alter table TB_TELEFONE add constraint fk_telefone_usuario foreign key (id_usuario) references TB_USUARIO(id);
alter table TB_TELEFONE add constraint ck_tipo_telefone_01 check (tipo_telefone in ('RESIDENCIAL','CELULAR'));
alter table TB_TELEFONE add constraint ck_tipo_telefone_02 check (
(tipo_telefone = 'RESIDENCIAL' and (CHAR_LENGTH(numero) = 9)) or (tipo_telefone = 'CELULAR' and (CHAR_LENGTH(numero) = 8))
);