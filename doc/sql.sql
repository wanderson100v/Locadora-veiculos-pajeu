CREATE ROLE gerente SUPERUSER;
CREATE ROLE emanoela LOGIN PASSWORD 'emanoela' IN ROLE gerente;
GRANT SELECT, INSERT ON ALL TABLES IN SCHEMA public TO gerente;

CREATE ROLE administrador;
GRANT SELECT, INSERT ON ALL TABLES IN SCHEMA public TO administrador;
CREATE ROLE wanderson100v LOGIN PASSWORD 'wanderson100v' IN ROLE administrador;

CREATE ROLE atendente;
CREATE ROLE frederico LOGIN PASSWORD 'frederico' IN ROLE atendente;
GRANT SELECT, INSERT ON 
locacao,reserva,endereco,
cliente,fisico,juridico,
veiculo,automovel,caminhoneta_carga,
manutencao,automovel_acessorio
TO atendente;

GRANT SELECT ON categoria_veiculo TO atendente;

GRANT UPDATE ON funcionario TO atendente;

ALTER ROLE wanderson100v WITH PASSWORD '123';
ALTER ROLE wanderson100v RENAME TO wanderson;


CREATE OR REPLACE FUNCTION auterar_login_usuario() RETURNS trigger 
AS $BODY$ 
BEGIN
ALTER ROLE OLD.login RENAME TO NEW.login;
RETURN NEW;	
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER updateLogin 
ALTER UPDATE OF login ON funcionario 
FOR EACH ROW
EXECUTE PROCEDURE auterar_login_usuario(); 
