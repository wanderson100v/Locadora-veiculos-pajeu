package business;

import entidade.Endereco;
import excecoes.BoException;

public interface IBoEndereco {
	  
	public Endereco gerarEndereco(String cep) throws BoException;
	
}
