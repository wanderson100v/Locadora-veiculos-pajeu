package mode.business;

import model.excecoes.BoException;
import model.entidade.Endereco;

public interface IBoEndereco {
	  
	public Endereco gerarEndereco(String cep) throws BoException;
	
}
