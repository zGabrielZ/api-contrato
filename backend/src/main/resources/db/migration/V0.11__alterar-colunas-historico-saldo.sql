alter table TB_HISTORICO_SALDO drop column valor;
alter table TB_HISTORICO_SALDO add column valor_atual decimal(10,2);
alter table TB_HISTORICO_SALDO add column quantidade_informada decimal(10,2);