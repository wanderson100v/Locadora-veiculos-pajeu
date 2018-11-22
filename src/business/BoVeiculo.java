package business;

import dao.DaoVeiculo;
import dao.IDaoVeiculo;
import entidade.CategoriaVeiculo;
import entidade.Filial;
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
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria) throws BoException {
		try {
			return daoVeiculo.totalVeiculoDisponivel(filial, categoria);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
