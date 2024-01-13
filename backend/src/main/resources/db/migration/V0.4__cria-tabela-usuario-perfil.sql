create table TB_USUARIO_PERFIL(
    id_usuario bigint not null,
    id_perfil bigint not null
);

alter table TB_USUARIO_PERFIL add constraint fk_usuario_perfil_usuario foreign key (id_usuario) references TB_USUARIO(id);
alter table TB_USUARIO_PERFIL add constraint fk_usuario_perfil_perfil foreign key (id_perfil) references TB_PERFIL(id);