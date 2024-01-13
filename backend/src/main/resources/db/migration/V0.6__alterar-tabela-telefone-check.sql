alter table TB_TELEFONE drop check ck_tipo_telefone_02;
alter table TB_TELEFONE add constraint ck_tipo_telefone_02 check (
(tipo_telefone = 'RESIDENCIAL' and (CHAR_LENGTH(numero) = 8)) or (tipo_telefone = 'CELULAR' and (CHAR_LENGTH(numero) = 9))
);