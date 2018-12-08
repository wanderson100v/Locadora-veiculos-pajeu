CREATE ROLE gerente SUPERUSER;
GRANT SELECT, INSERT ON ALL TABLES IN SCHEMA public TO gerente;

CREATE ROLE administrador;
GRANT SELECT, INSERT ON ALL TABLES IN SCHEMA public TO administrador;

CREATE ROLE atendente;
GRANT SELECT, INSERT ON locacao,reserva,endereco, cliente,fisico,juridico,
veiculo,automovel,caminhoneta_carga,manutencao,automovel_acessorio
TO atendente;
GRANT SELECT ON categoria_veiculo TO atendente;
GRANT UPDATE ON funcionario TO atendente;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO atendente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO gerente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO administrador;


