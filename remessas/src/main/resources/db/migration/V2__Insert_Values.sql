INSERT INTO usuario( nome, email, cpf_cnpj, senha) VALUES
 ('Ramesh', 'ramesh@email.com', '99410073065', '!123#1231'),
 ('Joao', 'joao@email.com', '70981163009', '!453#12451'),
 ('Empresa X', 'empresax@email.com', '12054412000193', '!453245#12451'),
 ('Empresa Y', 'empresaY@email.com', '01884992000120', '!4123455#2451'); 


INSERT INTO carteira( id_usuario, saldo, origem) VALUES
 (1, 1000000.50, 'PT'),
 (1, 5.50, 'EN'),
 (2, 100000.00, 'PT'),
 (2, 5.50, 'EN'),
 (3, 200000.00, 'PT'),
 (3, 5.50, 'EN'),
 (4, 450000.00, 'PT'),
 (4, 5.50, 'EN'); 

 
INSERT INTO remessa( remessa, id_usuario_remetente, id_usuario_destinatario) VALUES
 (35.50, 1, 2),
 (50, 2, 1)
 ;
