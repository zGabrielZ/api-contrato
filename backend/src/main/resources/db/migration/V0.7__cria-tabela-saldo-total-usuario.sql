create table TB_SALDO_TOTAL_USUARIO(
    id bigint not null auto_increment,
    valor decimal(10, 2) not null,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

alter table TB_SALDO_TOTAL_USUARIO add constraint ck_saldo_total_usuario_01 check (valor >= 0.0);