package model.dao;

import model.excecoes.DaoException;
import model.vo.Endereco;

public interface IDaoEndereco extends IDao<Endereco> {
	
	 public String buscarCep(String cep) throws DaoException;
}
