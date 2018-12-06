package business;

import java.util.List;

import dao.DaoVeiculo;
import dao.IDaoVeiculo;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.BoException;
import excecoes.DaoException;

public class BoVeiculo implements IBoVeiculo {
	private IDaoVeiculo daoVeiculo = new DaoVeiculo();
	private static IBoVeiculo instance;
	
	private BoVeiculo() {}
	
	public static IBoVeiculo getInstance() {
		if(instance == null)
			instance = new BoVeiculo();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Veiculo entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoVeiculo.editar(entidade);
			}else {
				daoVeiculo.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public void exluir(Veiculo entidade) throws BoException{
		try {
			daoVeiculo.excluir(entidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria) throws BoException {
		try {
			return daoVeiculo.totalVeiculoDisponivel(filial, categoria);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public long totalManutencoesPententes(Veiculo veiculo) throws BoException {
		try {
			return daoVeiculo.totalManutencaoPendente(veiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Veiculo> buscarPorExemplo(Veiculo veiculo) throws BoException {
		try {
			return daoVeiculo.buscarPorExemplo(veiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
