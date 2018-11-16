package business;

import java.util.List;

import dao.DaoFisico;
import dao.IDaoFisico;
import entidade.Fisico;
import excecoes.BoException;
import excecoes.DaoException;

public class BoFisico implements IBoFisico{
	private IDaoFisico daoFisico = new DaoFisico();
	
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
			try {
				daoFisico.excluir(entidade);
			} catch (DaoException e) {
				entidade.setAtivo(false);
				daoFisico.editar(entidade);
				throw new BoException(e.getMessage());
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
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

	private void atribuirCodigo(Fisico entidade){
		entidade.setCodigo("PJ"+entidade.getCpf());
	}
	
}
