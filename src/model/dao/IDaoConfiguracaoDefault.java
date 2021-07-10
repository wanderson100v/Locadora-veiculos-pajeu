package model.dao;

import model.excecoes.DaoException;
import model.vo.ConfiguracoesDefault;

public interface IDaoConfiguracaoDefault {
	
	public void salvar(ConfiguracoesDefault configuracoesDefault) throws DaoException;
	
	public ConfiguracoesDefault carregar() throws DaoException;
	
}
