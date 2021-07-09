package mode.business;

import java.util.List;

import model.dao.DaoCliente;
import model.dao.IDaoCliente;
import model.entidade.Cliente;
import model.excecoes.BoException;
import model.excecoes.DaoException;

public class BoCliente implements IBoCliente{
	private static IBoCliente instance;
	private IDaoCliente  daoCliente = new DaoCliente();
	
	private BoCliente() {}
	
	public static IBoCliente getInstance() {
		if(instance == null)
			instance = new BoCliente();
		return instance;
	}

	@Override
	public List<Cliente> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoCliente.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
