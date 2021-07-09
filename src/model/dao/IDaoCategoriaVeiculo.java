package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.entidade.Automovel;
import model.entidade.CaminhonetaCarga;
import model.entidade.CategoriaVeiculo;

public interface IDaoCategoriaVeiculo extends IDao<CategoriaVeiculo>{
	String BUSCA_POR_BUSCA = "categoriaVeiculo.buscaPorBusca";
	
	/* caractere curinga '?' para ser trocando para '<' caso ceira achar categorias inferiores a determinado
	 * veículo , estrategia a qual é utilizada para obters-se a lista de possiveis categorias para certo veiculo . 
	 * Ao trocar para '>' pode-se obter categorias superiores a determinado veiculo , assim caso se utiliza o veiculo
	 * de exemplo de determinada categoria de veiculos, a partir desse pode-se obter todas as categorias superíores
	 *   
	 */
	String SELECIONAR_CATEGORIA_CAMINHONETA_CARGA = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
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
	String SELECIONAR_AUTOMOVEL_PEQUENO = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
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
	String SELECIONAR_CAMINHONETA_PASSAGEIRO  = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
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
	
	public List<CategoriaVeiculo> categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> categorizarCaminhonetaPassageiro(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> categorizarAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaPassageiro(Automovel automovel) throws DaoException;	
	
	public List<CategoriaVeiculo> categoriasSuperiorAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscaPorBusca(CategoriaVeiculo categoriaVeiculo) throws DaoException;

}