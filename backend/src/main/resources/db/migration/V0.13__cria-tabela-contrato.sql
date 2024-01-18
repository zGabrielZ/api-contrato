create table TB_CONTRATO(
    id bigint not null auto_increment,
    numero bigint not null,
    data date not null,
    valor_total decimal(10, 2) not null,
    situacao_contrato varchar(255) not null,
    id_usuario bigint not null,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

alter table TB_CONTRATO add constraint fk_contrato_usuario foreign key (id_usuario) references TB_USUARIO(id);
alter table TB_CONTRATO add constraint ck_situacao_contrato_01 check (situacao_contrato in ('INICIADO','CONCLUIDO'));