package dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import adapter.ReservaDisponibilidade;
import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
import excecoes.DaoException;

public interface IDaoReserva extends IDao<Reserva>{
	
	String TOTAL_DATA_RETIRADA = "reserva.totalDataRetirada";
	String TUDO_RESERVA_HOJE = "reservaHoje.buscarTudo";
	String RESERVA_PENDENTE_POR_CLIENTE = "reservaPendente.buscarPorCliente";
	String RESERVA_PENDENTE_POR_CLIENTE_FILIAL = "reservaPendente.buscarPorCliente_Filial";
	String RESERVA_DISPONIBILIDADE_PARCIAL = 
			"select cate.id as id_categoria" + 
			
			" ,cate.tipo as tipo_categoria" +
			
			" ,cate.valor_diaria as valor_diaria_categoria" +
			
			" ,cate.descricao as descricao_categoria" +
			
			" ,(select count(*) from locacao as l" + 
			" inner join veiculo as v on(v.id = l.veiculo_id)" + 
			" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = l.filialentrega_id)" + 
			" where l.data_devolucao <= :horario" +
			" and l.finalizado = false" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" + 
			" ) as prev_locacao " +
			
			" ,(select count(*) from veiculo as v " +
			" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = v.filial_id)" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" + 
			" and v.ativo = true" + 
			" and v.locado = false" + 
			" and " 
				+ " (select count(*) from manutencao as m"
				+ " where m.estado = 1" // manutenção em progresso
				+ " and m.veiculo_id = v.id "
				+ " and make_interval(hours \\:= m.custo_horas)" 
				+ " + m.data_hora_inicio"  
				+ " <= :horario )"
				+ " = (select count(*) from manutencao as m"
				+ " where m.estado = 1"
				+ " and m.veiculo_id = v.id) "
				+ " "
			+" and " 
			+ " (select count(*) from manutencao as m"
			+ " where m.estado = 1" // manutenção em progresso
			+ " and m.veiculo_id = v.id "
			+ " and make_interval(hours \\:= m.custo_horas)" 
			+ " + m.data_hora_inicio"  
			+ " <= :horario )"
			+ " != 0 "
			+" ) as prev_manutencao"+ 
			
			" ,(select count(*) from reserva as r" + 
			" inner join categoria_veiculo as c on(c.id = r.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = r.filial_id)" + 
			" where r.estado_reserva = 1" + // reserva pendente
			" and r.data_retirada <= :horario" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" + 
			" ) as prev_reserva"+
			
			" ,(select count(*) from locacao as l" + 
			" inner join veiculo as v on(v.id = l.veiculo_id)" + 
			" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = v.filial_id)" + 
			" where l.finalizado = false" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" + 
			" ) as total_locacao"+ 
			
			" ,(select count(*) from veiculo as v" +
			" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = v.filial_id)" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" +
			" and v.ativo = true" + 
			" and v.locado = false" + 
			" and " 
				+ "(select count(*) from manutencao as m"
				+ " where m.estado !=2 " // manutenção não finalizada
				+ " and m.veiculo_id = v.id) "
				+ " > 0"+
			" ) as total_manutencao"+ 
			
			" ,(select count(*) from reserva as r" + 
			" inner join categoria_veiculo as c on(c.id = r.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = r.filial_id)" + 
			" where r.estado_reserva = 1" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" + 
			" ) as total_reserva"+
			
			" ,(select count(*) from veiculo as v" +
			" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
			" inner join filial as f on(f.id = v.filial_id)" + 
			" and f.id = fili.id" + 
			" and c.id = cate.id" +
			" and v.ativo = true" + 
			" ) as total_veiculo"+ 
			" from ";
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws DaoException;
		
	public List<ReservaHoje> buscarReservaHoje() throws DaoException;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente) throws DaoException ;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente, Filial filial) throws DaoException ;
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidadeSuperior(Automovel automovel, Long filialId,LocalDateTime horario) throws DaoException;
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidadeSuperior(CaminhonetaCarga automovel, Long filialId,LocalDateTime horario) throws DaoException; 
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario) throws DaoException;

	public ReservaDisponibilidade buscarReservaDisponibilidade(Long categoriaVeiculoId, Long filialId,LocalDateTime horario) throws DaoException ;

	public List<Reserva> buscaPorBuscaAbrangente(String busca, Reserva reserva,LocalDate de , LocalDate ate) throws DaoException;

	public List<Map<String, Object>> buscarReservasOrigemLocacaoFinalizada(LocalDate de , LocalDate ate, String agruparPor) throws DaoException;

	public List<Map<String, Object>> buscarReservasImpedidas(LocalDate de , LocalDate ate) throws DaoException;
}
