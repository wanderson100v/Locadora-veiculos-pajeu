package model.business;

import java.util.List;
import model.dao.IDao;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.excecoes.ValidarException;
import model.vo.Entidade;

public abstract class Bo <T extends Entidade> implements IBussines<T> {
	
	protected IDao<T> daoEntidade;
	
	public Bo(IDao<T> daoEntidade) {
		this.daoEntidade = daoEntidade;
	}
	
	public void cadastrarEditar(T entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				cadastrar(entidade);
			}else {
				editar(entidade);
			}
		}catch (DaoException daoException) {
			verificarErroCadastarEditar(daoException);
		}
		
	}
	
	public void cadastrar(T entidade) throws BoException, DaoException {
		daoEntidade.editar(entidade);
	}

	public void editar(T entidade) throws DaoException {
		daoEntidade.cadastrar(entidade);
	}

	protected void verificarErroCadastarEditar(DaoException daoException) throws BoException {
		throw new BoException(daoException.getMessage());
	}

	public void excluir(T entidade) throws BoException {
		try {
			this.exluirEntidade(entidade);
		} catch (DaoException daoException) {
			verificarErroExclusao(daoException,entidade);
		}
	}
		
	protected void exluirEntidade(T entidade) throws DaoException {
		daoEntidade.excluir(entidade);
	}
	
	protected void verificarErroExclusao(DaoException daoException, T entidade) throws BoException {
		try {
			validarCausaInicial(daoException,"","not-null","violates foreign key");
			throw new BoException(daoException.getMessage());
		}catch (ValidarException ValidarException) {
			inativarRegistro(entidade);
			cadastrarEditar(entidade);
			throw new BoException("IMPOSSIBILIDADE DE EXCLUSÃO: HÁ REGISTROS DEPENDENTES, O REGISTRO PASSOU PARA O ESTADO DE INATIVO OU DEPRECIADO");
		}
	}
	
	public abstract void inativarRegistro(T entidade); 

	public T buscarID(Long id) throws BoException {
		try {
			return daoEntidade.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}
	
	public List<T> buscarAll() throws BoException {
		try {
			return daoEntidade.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	public List<T> buscarPorExemplo(T exemploEntidade) throws BoException {
		try {
			return daoEntidade.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public List<T> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoEntidade.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	
	public void validarCausaInicial(Throwable e, String msg, String... restricoes) throws ValidarException {
		while(e.getCause() != null)
			e = e.getCause();
		for(String restricao : restricoes) 
			if(e.getMessage().contains(restricao.toLowerCase())){
				throw new ValidarException(msg);
		}
	}
}
