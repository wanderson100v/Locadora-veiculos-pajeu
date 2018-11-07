package bussines;

import java.util.List;

import dao.Dao;
import entidade.Cliente;
import entidade.Endereco;
import entidade.Fisico;
import entidade.Juridico;
import excecoes.BoException;
import excecoes.DaoException;

public class ClienteBo implements IClienteBo {
	public static IClienteBo instance;
	private Dao<Cliente> clienteDao;
	
	public static IClienteBo getInstance() {
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
			// antes de escluir verificar se o mesmo tem locaçoes ou reservas , para esse caso deve-se
			// dar um set no campo de "ativo" do cliente para "false"
			clienteDao.excluir(entidade);
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
