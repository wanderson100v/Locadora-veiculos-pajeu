package business;

import java.util.List;

import dao.DaoJuridico;
import dao.IDaoJuridico;
import entidade.Juridico;
import excecoes.BoException;
import excecoes.DaoException;

public class BoJuridico implements IBoJuridico {
	private IDaoJuridico daoJuridico = new DaoJuridico();
	
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
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Juridico buscarID(Long id) throws BoException {
		try {
			return daoJuridico.buscarId(id);
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
	
	private void atribuirCodigo(Juridico entidade){
		entidade.setCodigo("PJ"+entidade.getCnpj());
	}

}
