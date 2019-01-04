package business;

import java.util.List;

import entidade.Cliente;
import excecoes.BoException;

public interface IBoCliente {
	
	List<Cliente> buscaPorBuscaAbrangente(String busca) throws BoException;
	
}
