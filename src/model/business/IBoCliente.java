package model.business;

import java.util.List;

import model.excecoes.BoException;
import model.vo.Cliente;

public interface IBoCliente {
	
	List<Cliente> buscaPorBuscaAbrangente(String busca) throws BoException;
	
	public void validar(Cliente cliente) throws BoException;
	
}
