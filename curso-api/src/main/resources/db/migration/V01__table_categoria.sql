CREATE TABLE categoria(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Restaurante');
INSERT INTO categoria (nome) values ('Transporte');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Mercado');
INSERT INTO categoria (nome) values ('Cruzeiro');
INSERT INTO categoria (nome) values ('Plano de Saúde');