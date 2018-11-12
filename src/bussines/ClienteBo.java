package bussines;

import java.util.List;

import dao.Dao;
import entidade.Cliente;
import entidade.Fisico;
import entidade.Juridico;
import excecoes.BoException;
import excecoes.DaoException;

public class ClienteBo implements IBussines<Cliente> {
	public static IBussines<Cliente> instance;
	private Dao<Cliente> clienteDao;
	
	public static IBussines<Cliente> getInstance() {
		if(instance == null)
			instance = new ClienteBo();
		return instance;
	}
	
	public ClienteBo() {
		clienteDao = new Dao<>(Cliente.class);
	}

	@Override
	public void cadastrarEditar(Cliente entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				clienteDao.editar(entidade);
			}else {
				atribuirCodigo(entidade);
				clienteDao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	

	@Override
	public void excluir(Cliente entidade) throws BoException {
		try {
			if(entidade.getReservas() != null) {
				// cancela loações
			}
			if(entidade.getLocacoes() != null) {
				entidade.setAtivo(false);
				clienteDao.editar(entidade);
			}else {
				clienteDao.excluir(entidade);
			}
		}catch (DaoException e) {
			System.out.println();
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Cliente buscarID(Long id) throws BoException {
		try {
			return clienteDao.buscarId(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Cliente> buscarAll() throws BoException {
		try {
			return clienteDao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void atribuirCodigo(Cliente cliente){
		if(cliente instanceof Juridico) {
			cliente.setCodigo("PJ"+((Juridico)(cliente)).getCnpj());
		}else if(cliente instanceof Fisico){
			cliente.setCodigo("PF"+((Fisico)(cliente)).getCpf());
		}
	}
}
