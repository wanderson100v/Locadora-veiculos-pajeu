CREATE ROLE gerente SUPERUSER CREATEUSER;
GRANT SELECT,UPDATE , DELETE, INSERT ON ALL TABLES IN SCHEMA public TO gerente;


CREATE ROLE admin LOGIN PASSWORD 'admin' in group gerente;

CREATE ROLE administrador CREATEUSER;
GRANT SELECT, UPDATE , DELETE, INSERT ON ALL TABLES IN SCHEMA public TO administrador;


CREATE ROLE atendente CREATEUSER;
GRANT SELECT ON ALL TABLES IN SCHEMA public  TO atendente;
GRANT UPDATE , DELETE ,INSERT ON locacao,reserva,endereco, cliente,fisico,juridico,
	veiculo,automovel,caminhoneta_carga,manutencao,automovel_acessorio TO atendente;
GRANT UPDATE ON funcionario TO atendente;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO atendente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO gerente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO administrador;

drop table reserva_hoje;

create or replace view reserva_hoje 
as select r.id, extract('hour'From r.data_retirada) as hora ,cat.tipo , r.estado_reserva, cli.nome as nome_cliente, f.nome as nome_filial
from reserva as r inner join cliente as cli on(cli.id = r.cliente_id)
inner join filial as f on(f.id = r.filial_id) 
inner join categoria_veiculo as cat on(r.categoriaveiculo_id = cat.id)
where DATE(r.data_retirada) = current_date order by hora ;

GRANT SELECT ON reserva_hoje TO gerente;
GRANT SELECT ON reserva_hoje TO administrador;
GRANT SELECT ON reserva_hoje TO atendente;

