CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
    senha VARCHAR(14) NOT NULL 
);


CREATE TABLE IF NOT EXISTS carteira (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    saldo NUMERIC(12, 2) NOT NULL,
    origem VARCHAR(2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);


CREATE TABLE IF NOT EXISTS remessa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    remessa NUMERIC(12, 2) NOT NULL,
    id_usuario_remetente INT NOT NULL,
    id_usuario_destinatario INT NOT NULL,
    data_remessa TIMESTAMP NOT NULL,
    FOREIGN KEY (id_usuario_remetente) REFERENCES usuario(id),
    FOREIGN KEY (id_usuario_destinatario) REFERENCES usuario(id)
);


