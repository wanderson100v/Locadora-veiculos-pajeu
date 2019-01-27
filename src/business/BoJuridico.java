package business;

import java.util.List;

import dao.DaoJuridico;
import dao.IDaoJuridico;
import entidade.Juridico;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

public class BoJuridico implements IBoJuridico {
	private IDaoJuridico daoJuridico = new DaoJuridico();
	private static IBoJuridico instance;
	
	private BoJuridico() {}
	
	public static IBoJuridico getInstance() {
		if(instance == null)
			instance = new BoJuridico();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Juridico entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoJuridico.editar(entidade);
			}else {
				atribuirCodigo(entidade);
				daoJuridico.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Juridico entidade) throws BoException {
		try {
			daoJuridico.excluir(entidade);
		}catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setAtivo(false);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ REGISTROS DEPENDENTES, CLIENTE INATIVADO");
			}
		}
	}

	@Override
	public Juridico buscarID(Long id) throws BoException {
		try {
			return daoJuridico.buscarID(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	@Override
	public List<Juridico> buscarAll() throws BoException {
		try {
			return daoJuridico.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Juridico> buscarPorExemplo(Juridico exemploEntidade) throws BoException {
		try {
			return daoJuridico.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void atribuirCodigo(Juridico entidade){
		entidade.setCodigo("PJ"+entidade.getCnpj());
	}

	@Override
	public List<Juridico> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoJuridico.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
}