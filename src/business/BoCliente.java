package business;

import java.util.List;

import dao.DaoCliente;
import dao.IDaoCliente;
import entidade.Cliente;
import excecoes.BoException;
import excecoes.DaoException;

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
