CREATE TABLE contato(
  codigo INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NULL,
  telefone VARCHAR(50) NULL,
  email VARCHAR(50) NULL,
  dt_cad DATE NULL,
  obs TEXT,
  PRIMARY KEY(codigo)
);