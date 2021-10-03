CREATE TABLE subcategoria (
	id_subcategoria serial NOT NULL,
	nome varchar(255) NULL,
	id_categoria bigint NOT NULL,
	CONSTRAINT pk_subcategoria PRIMARY KEY (id_subcategoria),
	CONSTRAINT fk_subcategoria_categoria
          FOREIGN KEY(id_categoria)
    	  REFERENCES categoria(id_categoria),
    UNIQUE(nome)
);