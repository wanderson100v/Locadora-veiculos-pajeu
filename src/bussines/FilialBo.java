package bussines;

import java.util.List;

import dao.Dao;
import entidade.Filial;
import excecoes.BoException;
import excecoes.DaoException;

public class FilialBo implements IBussines<Filial> {
	public static IBussines<Filial> instance;
	private Dao<Filial> filialDao;
	
	public static IBussines<Filial> getInstance() {
		if(instance == null)
			instance = new FilialBo();
		return instance;
	}
	
	public FilialBo() {
		filialDao = new Dao<>(Filial.class);
	}

	@Override
	public void cadastrarEditar(Filial entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				filialDao.editar(entidade);
			}else {
				filialDao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Filial entidade) throws BoException {
		try {
			filialDao.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Filial buscarID(Long id) throws BoException {
		try {
			return filialDao.buscarId(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Filial> buscarAll() throws BoException {
		try {
			return filialDao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}


}
