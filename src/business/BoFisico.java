package business;

import java.util.List;

import dao.DaoFisico;
import dao.IDaoFisico;
import entidade.Fisico;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

public class BoFisico implements IBoFisico{
	private IDaoFisico daoFisico = new DaoFisico();
	
	private static IBoFisico instance;
	
	
	private BoFisico() {}
	
	public static IBoFisico getInstance() {
		if(instance == null)
			instance = new BoFisico();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Fisico entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFisico.editar(entidade);
			}else {
				atribuirCodigo(entidade);
				daoFisico.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Fisico entidade) throws BoException {
		try {
			daoFisico.excluir(entidade);
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
	public Fisico buscarID(Long id) throws BoException {
		try {
			return daoFisico.buscarID(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Fisico> buscarAll() throws BoException {
		try {
			return daoFisico.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Fisico> buscarPorExemplo(Fisico exemploEntidade) throws BoException {
		try {
			return daoFisico.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Fisico> buscaPorBusca(Fisico fisico) throws BoException {
		try {
			return daoFisico.buscaPorBusca(fisico);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void atribuirCodigo(Fisico entidade){
		entidade.setCodigo("PJ"+entidade.getCpf());
	}

}
