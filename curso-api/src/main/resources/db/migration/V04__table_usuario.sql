CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY, 
	nome VARCHAR(42) NOT NULL, 
	email VARCHAR(50) NOT NULL, 
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@cursoapi.com', 'admin');
INSERT INTO usuario (id, nome, email, senha) values (2, 'Mauricio Filho', 'mauricio_1souza@outlook.com', 'admin');
INSERT INTO usuario (id, nome, email, senha) values (3, 'Convidado', 'convidado@cursoapi.com', 'user');
