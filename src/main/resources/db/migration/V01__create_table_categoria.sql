create table categoria (
   id_categoria serial NOT NULL,
   nome varchar(255) NOT NULL,
   CONSTRAINT pk_categoria PRIMARY KEY (id_categoria),
   UNIQUE(nome)
);