package dao;

import entidade.ConfiguracoesDefault;
import excecoes.DaoException;

public interface IDaoConfiguracaoDefault {
	
	public void salvar(ConfiguracoesDefault configuracoesDefault) throws DaoException;
	
	public ConfiguracoesDefault carregar() throws DaoException;
	
}
