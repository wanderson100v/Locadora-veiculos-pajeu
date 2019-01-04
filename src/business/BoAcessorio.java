package business;

import java.util.List;

import dao.DaoAcessorio;
import dao.IDaoAcessorio;
import entidade.Acessorio;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

public class BoAcessorio implements IBoAcessorio{
	private IDaoAcessorio daoAcessorio = new DaoAcessorio();
	private static IBoAcessorio instance;
	
	private BoAcessorio() {}
	
	public static IBoAcessorio getInstance() {
		if(instance == null)
			instance = new BoAcessorio();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Acessorio entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoAcessorio.editar(entidade);
			}else {
				daoAcessorio.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	/*public static void main(String[] args) {
		Acessorio entidade = new Acessorio();
		entidade.setValor(10f);
		entidade.setDepreciado(false);
		entidade.setNome("DVD");
		
		Acessorio entidade2 = new Acessorio();
		entidade2.setValor(11f);
		entidade2.setDepreciado(false);
		entidade2.setNome("Ar condicionado");
		
		Acessorio entidade3 = new Acessorio();
		entidade3.setValor(12f);
		entidade3.setDepreciado(false);
		entidade3.setNome("Radio");
		
		Acessorio entidade4 = new Acessorio();
		entidade4.setValor(13f);
		entidade4.setDepreciado(false);
		entidade4.setNome("Frigobar");
		
		Acessorio entidade5 = new Acessorio();
		entidade5.setValor(13f);
		entidade5.setDepreciado(false);
		entidade5.setNome("Camêra ré");
		try {
			sql.ConnectionFactory.setUser("postgres","admin");
			getInstance().cadastrarEditar(entidade);
			getInstance().cadastrarEditar(entidade2);
			getInstance().cadastrarEditar(entidade3);
			getInstance().cadastrarEditar(entidade4);
			getInstance().cadastrarEditar(entidade5);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	@Override
	public void excluir(Acessorio entidade) throws BoException {
		try {
			daoAcessorio.excluir(entidade);
		}catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setDepreciado(true);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ REGISTROS DEPENDENTES, ACESSÓRIO PASSOU A SER INATIVO");
			}
		} 
	}

	@Override
	public Acessorio buscarID(Long id) throws BoException {
		try {
			return daoAcessorio.buscarID(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscarAll() throws BoException {
		try {
			return daoAcessorio.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscarPorExemplo(Acessorio exemploEntidade) throws BoException {
		try {
			return daoAcessorio.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoAcessorio.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
