package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;

public interface IDaoCategoriaVeiculo extends IDao<CategoriaVeiculo>{
	
	String BUSCA_POR_BUSCA = "select c from "+CAMINHO_CLASSE+"CategoriaVeiculo as c"
		+ " where upper(c.tipo) like upper(:tipo)"
		+ " or upper(c.descricao) like upper(:descricao)"
		+ " or c.quilometragemRevisao = :quilometragemRevisao"
		+ " or c.horasRevisao = :horasRevisao"
		+ " or c.horasLimpesa = :horasLimpesa"
		+ " or c.valorDiaria = :valorDia";
	
	/* caractere curinga '?' para ser trocando para '<' caso queira achar categorias inferiores a determinado
	 * veículo , estrategia a qual é© utilizada para obters-se a lista de possiveis categorias para certo veiculo . 
	 * Ao trocar para '>' pode-se obter categorias superiores a determinado veiculo , assim caso se utiliza o veiculo
	 * de exemplo de determinada categoria de veiculos, a partir desse pode-se obter todas as categorias superíores.
	 *   
	 */
	String SELECIONAR_CATEGORIA_CAMINHONETA_CARGA = "select categoriaVeiculo from "+CAMINHO_CLASSE+"CategoriaVeiculo as categoriaVeiculo "
		+ "inner join categoriaVeiculo.veiculoExemplo as caminhonetaCarga "
		+ "where caminhonetaCarga.potencia ?= :potencia "
		+ "and caminhonetaCarga.desenpenho ?= :desenpenho "
		+ "and caminhonetaCarga.capacidadeCarga ?= :capacidadeCarga "
		+ "and caminhonetaCarga.tipoAcionamentoEmbreagem ?= :tipoAcionamentoEmbreagem "
		+ "and caminhonetaCarga.distanciaEixos ?= :distanciaEixos "
		+ "and caminhonetaCarga.capacidadeCombustivel <= :capacidadeCombustivel "
		+ "and caminhonetaCarga.torqueMotor ?= :torqueMotor "
		+ "order by(caminhonetaCarga.capacidadeCarga, caminhonetaCarga.potencia, caminhonetaCarga.torqueMotor,"
		+ "caminhonetaCarga.desenpenho, caminhonetaCarga.distanciaEixos, caminhonetaCarga.tipoAcionamentoEmbreagem, "
		+ "caminhonetaCarga.tipoCombustivel, caminhonetaCarga.capacidadeCombustivel) desc";
	String SELECIONAR_AUTOMOVEL_PEQUENO = "select categoriaVeiculo from "+CAMINHO_CLASSE+"CategoriaVeiculo as categoriaVeiculo "
		+ "inner join categoriaVeiculo.veiculoExemplo as automovel "
		+ "where automovel.tipoAutomovel = 0  "
		+ "and automovel.tipoCambio ?= :tipoCambio "
		+ "and automovel.tamanhoVeiculo ?= :tamanhoVeiculo "
		+ "and automovel.quantidadePortas ?= :quantidadePortas "
		+ "and automovel.quantidadePassageiro ?= :quantidadePassageiro "
		+ "and automovel.tipoCombustivel ?= :tipoCombustivel "
		+ "order by(automovel.tipoCambio ,automovel.tamanhoVeiculo, "
		+ "automovel.quantidadePortas, automovel.quantidadePassageiro, "
		+ "automovel.tipoCombustivel) desc";
	String SELECIONAR_CAMINHONETA_PASSAGEIRO  = "select categoriaVeiculo from "+CAMINHO_CLASSE+"CategoriaVeiculo as categoriaVeiculo "
		+ "inner join categoriaVeiculo.veiculoExemplo as automovel "
		+ "where automovel.tipoAutomovel = 1 "
		+ "and automovel.tipoAirBag ?= :tipoAirBag "
		+ "and automovel.tipoCambio ?= :tipoCambio "
		+ "and automovel.tamanhoVeiculo ?= :tamanhoVeiculo "
		+ "and automovel.quantidadePortas ?= :quantidadePortas "
		+ "and automovel.quantidadePassageiro ?= :quantidadePassageiro "
		+ "and automovel.tipoCombustivel ?= :tipoCombustivel "
		+ "order by(automovel.tipoAirBag, automovel.tipoCambio ,automovel.tamanhoVeiculo, "
		+ "automovel.quantidadePortas, automovel.quantidadePassageiro, automovel.tipoCombustivel) desc";
	
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaPassageiro(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriaAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaPassageiro(Automovel automovel) throws DaoException;	
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscaPorBusca(CategoriaVeiculo categoriaVeiculo) throws DaoException;

}