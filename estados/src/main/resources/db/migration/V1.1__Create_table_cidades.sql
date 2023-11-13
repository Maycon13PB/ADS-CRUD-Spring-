create table if not exists cidades(
    id serial,
    
    name varchar(50) not null,

    constraint pk_cidades primary key (id)

);

