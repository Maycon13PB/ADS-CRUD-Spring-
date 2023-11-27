create table if not exists cidades(
    id serial,
    name varchar(50) not null,
    estados int not null,
    constraint id_cidades primary key (id),
    constraint fk_estados foreign key (estados) references estados(id_estados)
);

