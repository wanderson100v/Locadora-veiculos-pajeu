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
	public List<Cliente> buscaPorBusca(String busca) throws BoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(busca);
			cliente.setCodigo(busca);
			cliente.setEmail(busca);
			cliente.setTelefone(busca);
			return daoCliente.buscaPorBusca(cliente);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
}
