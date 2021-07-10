package model.business;

import model.dao.IDao;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Entidade;

public class BoAdapter <T extends Entidade> extends Bo<T>{
	
	public BoAdapter(IDao<T> daoEntidade) {
		super(daoEntidade);
	}

	protected void verificarErroExclusao(DaoException daoException, T entidade) throws BoException {
		throw new BoException(daoException.getMessage());
	}
	
	@Override
	public void inativarRegistro(T entidade) {}

}
