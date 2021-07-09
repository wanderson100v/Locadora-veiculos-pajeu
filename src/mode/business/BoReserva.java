package mode.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.adapter.ReservaDisponibilidade;
import model.banco.ReservaHoje;
import model.banco.ReservaPendente;
import model.dao.DaoReserva;
import model.dao.IDaoReserva;
import model.entidade.Automovel;
import model.entidade.CaminhonetaCarga;
import model.entidade.CategoriaVeiculo;
import model.entidade.Cliente;
import model.entidade.Filial;
import model.entidade.Reserva;
import model.excecoes.BoException;
import model.excecoes.DaoException;

public class BoReserva implements IBoReserva {
	private IDaoReserva daoReserva = new DaoReserva();
	private IBoVeiculo boVeiculo = BoVeiculo.getInstance();
	private IBoLocacao boLocacao = BoLocacao.getInstance();
	private static IBoReserva instance;
	
	private BoReserva() {}
	
	public static IBoReserva getInstance() {
		if(instance == null)
			instance = new BoReserva();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Reserva entidade) throws BoException {
		try {
			if(entidade.getId() != null)
				daoReserva.editar(entidade);
			else {
				if(disponibilidadeCategoriaEmFilial(entidade.getCategoriaVeiculo().getId(), entidade.getFilial().getId(),entidade.getDataRetirada()))
					daoReserva.cadastrar(entidade);
				else
					throw new BoException("Não há veiculos disponiveis para o horario, "
							+ "categoria de veículo e filial definidos");
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Reserva entidade) throws BoException {
		try {
			daoReserva.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Reserva buscarID(Long id) throws BoException {
		try {
			return daoReserva.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Reserva> buscarAll() throws BoException {
		try {
			return daoReserva.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Reserva> buscarPorExemplo(Reserva exemploEntidade) throws BoException {
		try {
			return daoReserva.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void validarConcorrenciaReserva(Reserva reserva)throws BoException {
		long totalPrevistoLocacao = boLocacao.totalLocacoePrevisaoEntrega(reserva.getFilial(),reserva.getCategoriaVeiculo(),reserva.getDataRetirada());
		long totalDisponivel = boVeiculo.totalVeiculoDisponivel(reserva.getFilial(),reserva.getCategoriaVeiculo());
		long totalReservado = totalReservaDataRetirada(reserva.getCategoriaVeiculo());
		System.out.println();
		System.out.println(totalDisponivel+"total disponivel");
		System.out.println(totalPrevistoLocacao+"total previsto locado");
		System.out.println(totalReservado+"total reservado");
		System.out.println();
		System.out.println(totalDisponivel+totalPrevistoLocacao+"total disponivel e previsto");
		long reservavel = totalDisponivel+totalPrevistoLocacao;
		if(totalDisponivel+totalPrevistoLocacao == 0)
			throw new BoException("Não Há veiculos disponiveis para essa categoria na filial");
		if(reservavel <= totalReservado)
			throw new BoException("NÃO H�? VEICULOS SUFICIENTES DISPONIVEIS PARA RESERVA ESSA DATA NESTA CATEGORIA E FILIAL");
	}
	
	@Override
	public List<ReservaHoje> buscarReservaHoje() throws BoException {
		try {
			return daoReserva.buscarReservaHoje();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	

	@Override
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(dadoCliente);
			cliente.setCodigo(dadoCliente);
			cliente.setEmail(dadoCliente);
			cliente.setTelefone(dadoCliente);
			return daoReserva.buscarReservaPendente(cliente);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial) throws BoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(dadoCliente);
			cliente.setCodigo(dadoCliente);
			cliente.setEmail(dadoCliente);
			cliente.setTelefone(dadoCliente);
			return daoReserva.buscarReservaPendente(cliente,filial);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CategoriaVeiculo categoriaVeiculo, Long filialId,
			LocalDateTime horario) throws BoException {
		try {
			if(categoriaVeiculo.getVeiculoExemplo() instanceof CaminhonetaCarga)
				return daoReserva.buscarReservaDisponibilidadeSuperior((CaminhonetaCarga) categoriaVeiculo.getVeiculoExemplo(), filialId, horario);
			else if(categoriaVeiculo.getVeiculoExemplo() instanceof Automovel) {
				return daoReserva.buscarReservaDisponibilidadeSuperior((Automovel) categoriaVeiculo.getVeiculoExemplo(), filialId, horario);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)
			throws BoException {
		try {
			return daoReserva.buscarReservaDisponibilidade(filialId, horario);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	public Boolean disponibilidadeCategoriaEmFilial(Long categoriaVeiculoId, Long filialId, LocalDateTime horario) throws BoException{
		try {
		 if(daoReserva.buscarReservaDisponibilidade(categoriaVeiculoId, filialId,horario).getDisponivel() >0)
			 return true;
		 return false;
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException {
		try {
			return daoReserva.totalReservaDataRetirada(categoriaVeiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Reserva> buscaPorBuscaAbrangente(String busca) throws BoException{
		try {
			return daoReserva.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Reserva> buscaPorBuscaAbrangente(String busca, Reserva reserva,LocalDate de , LocalDate ate) throws BoException{
		try {
			return daoReserva.buscaPorBuscaAbrangente(busca, reserva, de, ate);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> buscarReservasOrigemLocacaoFinalizada(LocalDate de, LocalDate ate,
			String agruparPor) throws BoException {
		try {
			return daoReserva.buscarReservasOrigemLocacaoFinalizada(de, ate, agruparPor);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> buscarReservasImpedidas(LocalDate de, LocalDate ate)
			throws BoException {
		try {
			return daoReserva.buscarReservasImpedidas(de, ate);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
