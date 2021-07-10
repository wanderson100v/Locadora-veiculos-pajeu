package model.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.adapter.ReservaDisponibilidade;
import model.banco.ReservaHoje;
import model.banco.ReservaPendente;
import model.dao.DaoReserva;
import model.dao.IDaoReserva;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;
import model.vo.Cliente;
import model.vo.Filial;
import model.vo.Reserva;

public class BoReserva extends BoAdapter<Reserva> implements IBoReserva {
	private IDaoReserva daoReserva = new DaoReserva();
	
	public BoReserva() {
		super(new DaoReserva());
	}
	
	@Override
	public void cadastrar(Reserva entidade) throws BoException, DaoException {
		if(disponibilidadeCategoriaEmFilial(entidade.getCategoriaVeiculo().getId(), entidade.getFilial().getId(),entidade.getDataRetirada()))
			daoReserva.cadastrar(entidade);
		else
			throw new BoException("Não há veiculos disponiveis para o horario, "
					+ "categoria de veículo e filial definidos");
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
