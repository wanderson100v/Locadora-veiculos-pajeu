package business;

import java.util.List;

import dao.DaoReserva;
import dao.IDaoReserva;
import entidade.Reserva;
import excecoes.BoException;
import excecoes.DaoException;

public class BoReserva implements IBoReserva {
	private IDaoReserva daoReserva = new DaoReserva();
	
	@Override
	public void cadastrarEditar(Reserva entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoReserva.editar(entidade);
			}else {
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

}
