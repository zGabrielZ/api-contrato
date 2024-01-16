alter table TB_USUARIO add column id_saldo_total bigint;
alter table TB_USUARIO add constraint fk_usuario_saldo_total foreign key (id_saldo_total) references TB_SALDO_TOTAL_USUARIO(id);