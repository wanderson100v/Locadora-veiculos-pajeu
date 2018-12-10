package business;

import java.util.List;

import entidade.Cliente;
import excecoes.BoException;

public interface IBoCliente {

	public List<Cliente> buscaPorBusca(String busca) throws BoException;
	
}
