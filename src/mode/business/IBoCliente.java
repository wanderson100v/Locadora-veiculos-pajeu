package mode.business;

import java.util.List;

import model.excecoes.BoException;
import model.entidade.Cliente;

public interface IBoCliente {
	
	List<Cliente> buscaPorBuscaAbrangente(String busca) throws BoException;
	
}
