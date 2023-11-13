create table if not exists estados(
    id serial,
    
    name varchar(50) not null,

    sigla varchar(50) not null,

    constraint pk_estados primary key (id)

);

