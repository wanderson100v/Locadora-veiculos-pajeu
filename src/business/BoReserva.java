package business;

import java.time.LocalDateTime;
import java.util.List;

import dao.DaoReserva;
import dao.IDaoReserva;
import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.BoException;
import excecoes.DaoException;

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
			if(entidade.getId() != null) {
				daoReserva.editar(entidade);
			}else {
				validarConcorrenciaReserva(entidade);
				daoReserva.cadastrar(entidade);
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
	
	private void validarConcorrenciaReserva(Reserva reserva)throws BoException {
		int totalPrevistoLocacao = boLocacao.totalLocacoePrevisaoEntrega(reserva.getFilial(),reserva.getCategoriaVeiculo(),reserva.getDataRetirada());
		int totalDisponivel = boVeiculo.totalVeiculoDisponivel(reserva.getFilial(),reserva.getCategoriaVeiculo());
		int totalReservado = totalReservaDataRetirada(reserva.getCategoriaVeiculo(),reserva.getDataRetirada());
		
		if(totalDisponivel+totalPrevistoLocacao < totalReservado)
			throw new BoException("NÃO HÁ VEICULOS DISPONIVEIS PARA RESERVA NESSA CATEGORIA E FILIAL");
	}

	@Override
	public int totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo, LocalDateTime dataRetirada) throws BoException {
		try {
			return daoReserva.totalReservaDataRetirada(categoriaVeiculo,dataRetirada);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
