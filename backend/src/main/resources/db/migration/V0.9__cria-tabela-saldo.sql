create table TB_SALDO(
    id bigint not null auto_increment,
    id_usuario bigint not null,
    valor decimal(10, 2) not null,
    tipo_movimentacao varchar(255) not null,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

alter table TB_SALDO add constraint fk_saldo_usuario foreign key (id_usuario) references TB_USUARIO(id);
alter table TB_SALDO add constraint ck_tipo_movimentacao_01 check (tipo_movimentacao in ('DEPOSITO','SAQUE'));