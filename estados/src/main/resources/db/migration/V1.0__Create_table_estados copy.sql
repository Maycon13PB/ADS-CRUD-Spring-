create table if not exists estados(
    id serial,
    
    name varchar(50) not null,

    sigla varchar(50) not null,
    constraint id_estados primary key (id)

);

