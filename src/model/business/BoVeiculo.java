package model.business;

import java.util.List;

import model.dao.DaoVeiculo;
import model.dao.IDaoVeiculo;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Veiculo;

public class BoVeiculo extends BoAdapter<Veiculo> implements IBoVeiculo {

	private IDaoVeiculo daoVeiculo;
	
	public BoVeiculo() {
		super(new DaoVeiculo());
		this.daoVeiculo = (IDaoVeiculo) daoEntidade;
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
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId, String dadoVeiculo) throws BoException {
		try {
			Veiculo veiculo = new Veiculo();
			veiculo.setCor(dadoVeiculo);
			veiculo.setFabricante(dadoVeiculo);
			veiculo.setModelo(dadoVeiculo);
			veiculo.setNumeroChassi(dadoVeiculo);
			veiculo.setNumeroMotor(dadoVeiculo);
			veiculo.setPlaca(dadoVeiculo);
			return daoVeiculo.buscarVeiculosDisponivel(filialId, categoriaVeiculoId, veiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, String dadoVeiculo) throws BoException {
		try {
			Veiculo veiculo = new Veiculo();
			veiculo.setCor(dadoVeiculo);
			veiculo.setFabricante(dadoVeiculo);
			veiculo.setModelo(dadoVeiculo);
			veiculo.setNumeroChassi(dadoVeiculo);
			veiculo.setNumeroMotor(dadoVeiculo);
			veiculo.setPlaca(dadoVeiculo);
			return daoVeiculo.buscarVeiculosDisponivel(filialId,veiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Veiculo> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoVeiculo.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
