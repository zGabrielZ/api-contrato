create table TB_HISTORICO_SALDO(
    id bigint not null auto_increment,
    id_saldo bigint not null,
    id_usuario bigint not null,
    valor decimal(10, 2) not null,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

alter table TB_HISTORICO_SALDO add constraint fk_historico_saldo_saldo foreign key (id_saldo) references TB_SALDO(id);
alter table TB_HISTORICO_SALDO add constraint fk_historico_saldo_usuario foreign key (id_usuario) references TB_USUARIO(id);