package model.business;

import model.excecoes.BoException;
import model.vo.Endereco;

public interface IBoEndereco extends IBussines<Endereco>{
	  
	public Endereco gerarEndereco(String cep) throws BoException;
	
}
