package business;

import java.util.List;

import dao.DaoFilial;
import dao.IDaoFilial;
import entidade.Endereco;
import entidade.Filial;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

public class BoFilial implements IBoFilial{
	private IDaoFilial daoFilial = new DaoFilial();
	private static IBoFilial instance;
	
	private BoFilial() {}
	
	public static IBoFilial getInstance() {
		if(instance == null)
			instance = new BoFilial();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Filial entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFilial.editar(entidade);
			}else {
				daoFilial.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Filial entidade) throws BoException {
		try {
			daoFilial.excluir(entidade);
		} catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setAtivo(false);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ REGISTROS DEPENDENTES, FILIAL FOI INATIVADA");
			}
		}
	}

	@Override
	public Filial buscarID(Long id) throws BoException {
		try {
			return daoFilial.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}
	
	@Override
	public List<Filial> buscaPorBusca(String busca) throws BoException {
		try {
			Filial filial = new Filial();
			filial.setNome(busca);
			Endereco endereco = new Endereco();
			endereco.setRua(busca);
			endereco.setCep(busca);
			endereco.setNumero(busca);
			endereco.setCidade(busca);
			endereco.setBairro(busca);
			filial.setEndereco(endereco);
			
			return daoFilial.buscaPorBusca(filial);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}


	@Override
	public List<Filial> buscarAll() throws BoException {
		try {
			return daoFilial.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Filial> buscarPorExemplo(Filial exemploEntidade) throws BoException {
		try {
			return daoFilial.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
