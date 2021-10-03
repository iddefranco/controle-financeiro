create table lancamento (
   id_lancamento serial NOT NULL,
   valor numeric(19,2) not null,
   data date not null,
   comentario Text NULL,
   id_subcategoria bigint,
   CONSTRAINT pk_lancamento PRIMARY KEY (id_lancamento),
   	CONSTRAINT fk_lancamento_subcategoria
             FOREIGN KEY(id_subcategoria)
       	  REFERENCES subcategoria(id_subcategoria)
);