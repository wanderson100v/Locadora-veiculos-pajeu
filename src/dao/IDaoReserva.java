package dao;

import java.time.LocalDateTime;
import java.util.List;

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
	String RESERVA_DISPONIBILIDADE_PARCIAL = "select" + 
				" cate.tipo as tipo_categoria" +
				" ,cate.valor_diaria as valor_diaria_categoria" +
				" ,cate.descricao as descricao_categoria" +
				" ,(select count(*) from veiculo as v"+ 
				" inner join filial as f on(f.id = v.filial_id)" + 
				" left join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
				" where v.ativo = true" + 
				" and v.locado = false"+ 
				" and f.id = fili.id" + 
				" and c.id = cate.id" + 
				" ) as reservavel" +
				" ,(select count(*) from locacao as l" + 
				" inner join veiculo as v on(v.id = l.veiculo_id)" + 
				" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
				" inner join filial as f on(f.id = l.filialentrega_id)" + 
				" where l.data_devolucao <= :horario" + 
				" and f.id = fili.id" + 
				" and c.id = cate.id" + 
				" ) as a_receber " +
				" ,(select count(*) from reserva as r" + 
				" inner join categoria_veiculo as c on(c.id = r.categoriaveiculo_id)" + 
				" inner join filial as f on(f.id = r.filial_id)" + 
				" where r.estado_reserva = 1" + 
				" and f.id = fili.id" + 
				" and c.id = cate.id" + 
				" ) as reservado"+ 
				" from ";
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws DaoException;
		
	public List<ReservaHoje> buscarReservaHoje() throws DaoException;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente) throws DaoException ;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente, Filial filial) throws DaoException ;
	
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(Automovel automovel, Long filialId,LocalDateTime horario) throws DaoException;
	
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CaminhonetaCarga automovel, Long filialId,LocalDateTime horario) throws DaoException; 
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario) throws DaoException;

}
