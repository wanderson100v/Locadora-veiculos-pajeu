--SQL a ser usado após criação do banco pelo hibernate--

-- limpando os logs anteriores
DELETE FROM public.log_acessorio;
DELETE FROM public.log_automovel;
DELETE FROM public.log_caminhoneta_carga;
DELETE FROM public.log_categoria_veiculo;
DELETE FROM public.log_cliente;
DELETE FROM public.log_endereco;
DELETE FROM public.log_filial;
DELETE FROM public.log_fisico;
DELETE FROM public.log_funcionario;
DELETE FROM public.log_juridico;
DELETE FROM public.log_locacao;
DELETE FROM public.log_manutencao;
DELETE FROM public.log_reserva;
DELETE FROM public.log_veiculo;

DROP TABLE reserva_hoje;
DROP TABLE reserva_pendente;
-- Criando a função de backup, que é apagado do banco após a criação, pois, 
-- se utiliza da classe backup que é também uma classe mapeada pelo hibernate

CREATE OR REPLACE FUNCTION public.checar_backup(
	)
    RETURNS backup
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare 
		ultimo_backup Backup%RowType;
	begin
		select into ultimo_backup b.* from Backup as b
		where (b.estado = 0 or b.estado = 1) 
		and b.data_ocorrencia < current_timestamp 
		order by b.data_ocorrencia desc
		limit 1 ;

		update backup set estado = 2 
		where estado = 0 
		and id != ultimo_backup.id 
		and data_ocorrencia < current_timestamp;
		
		return ultimo_backup;
	end
$BODY$;

-- Criando views
CREATE OR REPLACE VIEW public.locacoes_finalizada
 AS
 SELECT date(locacao.data_devolucao) AS devolucao,
    count(*) AS locacoes,
    sum(locacao.valor_diaria) AS valor_real,
    sum(locacao.valor_pago) AS valor_pago,
    sum(locacao.valor_diaria) - sum(locacao.valor_pago) AS restante
   FROM locacao
  WHERE locacao.finalizado = true
  GROUP BY (date(locacao.data_devolucao))
  ORDER BY (date(locacao.data_devolucao));
  
CREATE OR REPLACE VIEW public.reserva_hoje
 AS
 SELECT r.id,
    date_part('hour'::text, r.data_retirada) AS hora,
    cat.tipo,
    r.estado_reserva,
    cli.nome AS nome_cliente,
    f.nome AS nome_filial
   FROM reserva r
     JOIN cliente cli ON cli.id = r.cliente_id
     JOIN filial f ON f.id = r.filial_id
     JOIN categoria_veiculo cat ON r.categoriaveiculo_id = cat.id
  WHERE date(r.data_retirada) = 'now'::text::date
  ORDER BY (date_part('hour'::text, r.data_retirada));
  
CREATE OR REPLACE VIEW public.reserva_impedida
 AS
 SELECT cli.codigo AS codigo_cliente,
    valor_dias_locacao(r.data_retirada, r.data_devolucao, c.valor_diaria::double precision) AS potencial_perdido,
    cast_estado_reserva(r.estado_reserva) AS estado,
    r.data_retirada AS planejada_retirada,
    r.data_devolucao AS planejada_devolucao
   FROM reserva r
     JOIN categoria_veiculo c ON c.id = r.categoriaveiculo_id
     JOIN cliente cli ON cli.id = r.cliente_id
  WHERE r.estado_reserva = 3 OR r.estado_reserva = 0;
  
CREATE OR REPLACE VIEW public.reserva_origem
 AS
 SELECT date(r.data_devolucao) AS devolucao,
    count(*) AS locacoes_geradas,
    sum(l.valor_pago) AS total_pago,
    sum(l.valor_diaria) AS valor_real,
    sum(l.valor_pago) AS valor_pago,
    sum(l.valor_diaria) - sum(l.valor_pago) AS restante
   FROM reserva r
     JOIN locacao l ON r.id = l.reservaorigem_id
     JOIN categoria_veiculo c ON c.id = r.categoriaveiculo_id
  WHERE l.finalizado = true
  GROUP BY (date(r.data_devolucao))
  ORDER BY (date(r.data_devolucao));
  
CREATE OR REPLACE VIEW public.reserva_pendente
 AS
 SELECT r.id,
    c.tipo,
    fun.nome AS nome_funcionario,
    cli.nome AS nome_cliente,
    cli.codigo AS codigo_cliente,
    cli.email,
    cli.telefone,
    r.data_retirada AS retirada,
    r.data_devolucao AS devolucao,
    fil.id AS filial_id,
    fil.nome AS nome_filial
   FROM reserva r
     JOIN funcionario fun ON fun.id = r.funcionario_id
     JOIN cliente cli ON cli.id = r.cliente_id
     JOIN categoria_veiculo c ON c.id = r.categoriaveiculo_id
     JOIN filial fil ON fil.id = r.filial_id
  WHERE r.estado_reserva = 1;

-- Definindo privilégios
GRANT SELECT, UPDATE, DELETE, INSERT ON ALL TABLES IN SCHEMA public TO gerente;
GRANT SELECT, UPDATE, DELETE, INSERT ON ALL TABLES IN SCHEMA public TO administrador;
GRANT SELECT ON ALL TABLES IN SCHEMA public  TO atendente;
GRANT UPDATE, DELETE ,INSERT ON locacao, reserva, endereco , cliente, fisico, juridico, veiculo, automovel, caminhoneta_carga, manutencao,automovel_acessorio TO atendente;
GRANT UPDATE ON funcionario TO atendente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO atendente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO gerente;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO administrador;

--Criando gatilhos para auditoria
CREATE TRIGGER audit_acessorio_trigger BEFORE UPDATE OR DELETE ON acessorio FOR EACH ROW EXECUTE PROCEDURE audit_acessorio_func();
CREATE TRIGGER audit_automovel_trigger BEFORE UPDATE OR DELETE ON automovel FOR EACH ROW EXECUTE PROCEDURE audit_automovel_func();
CREATE TRIGGER audit_caminhoneta_carga_trigger BEFORE UPDATE OR DELETE ON caminhoneta_carga FOR EACH ROW EXECUTE PROCEDURE audit_caminhoneta_carga_func();
CREATE TRIGGER audit_categoria_veiculo_trigger BEFORE UPDATE OR DELETE ON categoria_veiculo FOR EACH ROW EXECUTE PROCEDURE audit_categoria_veiculo_func();
CREATE TRIGGER audit_cliente_trigger BEFORE UPDATE OR DELETE ON cliente FOR EACH ROW EXECUTE PROCEDURE audit_cliente_func();
CREATE TRIGGER audit_endereco_trigger BEFORE UPDATE OR DELETE ON endereco FOR EACH ROW EXECUTE PROCEDURE audit_endereco_func();
CREATE TRIGGER audit_filial_trigger BEFORE UPDATE OR DELETE ON filial FOR EACH ROW EXECUTE PROCEDURE audit_filial_func();
CREATE TRIGGER audit_fisico_trigger BEFORE UPDATE OR DELETE ON fisico FOR EACH ROW EXECUTE PROCEDURE audit_fisico_func();
CREATE TRIGGER audit_funcionario_trigger BEFORE UPDATE OR DELETE ON funcionario FOR EACH ROW EXECUTE PROCEDURE audit_funcionario_func(); 
CREATE TRIGGER audit_juridico_trigger BEFORE UPDATE OR DELETE ON juridico FOR EACH ROW EXECUTE PROCEDURE audit_juridico_func();
CREATE TRIGGER audit_locacao_trigger BEFORE UPDATE OR DELETE ON locacao FOR EACH ROW EXECUTE PROCEDURE audit_locacao_func();
CREATE TRIGGER audit_manutencao_trigger BEFORE UPDATE OR DELETE ON manutencao FOR EACH ROW EXECUTE PROCEDURE audit_manutencao_func(); 
CREATE TRIGGER audit_reserva_trigger BEFORE UPDATE OR DELETE ON reserva FOR EACH ROW EXECUTE PROCEDURE audit_reserva_func();
CREATE TRIGGER audit_veiculo_trigger BEFORE UPDATE OR DELETE ON veiculo FOR EACH ROW EXECUTE PROCEDURE audit_veiculo_func();

INSERT INTO public.funcionario(
	id, ativo, cpf, nome)
	VALUES (1, True, 1234, 'admin');
	
CREATE ROLE a1234 LOGIN PASSWORD 'admin' IN ROLE administrador;