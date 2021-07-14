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
	 * ve�culo , estrategia a qual � utilizada para obters-se a lista de possiveis categorias para certo veiculo . 
	 * Ao trocar para '>' pode-se obter categorias superiores a determinado veiculo , assim caso se utiliza o veiculo
	 * de exemplo de determinada categoria de veiculos, a partir desse pode-se obter todas as categorias super�ores.
	 *   
	 */
	String SELECIONAR_CATEGORIA_CAMINHONETA_CARGA = "SELECT categoria_veiculo.id, categoria_veiculo.tipo, categoria_veiculo.quilometragem_revisao,"
			+ " categoria_veiculo.horas_revisao, categoria_veiculo.horas_limpesa,  categoria_veiculo.valor_diaria, categoria_veiculo.descricao,"
			+ " categoria_veiculo.valor_livre, categoria_veiculo.valor_km, categoria_veiculo.veiculoExemplo_id  FROM categoria_veiculo"
			+ " INNER JOIN veiculo AS veiculo_exemplo ON(categoria_veiculo.veiculoexemplo_id = veiculo_exemplo.id)" 
			+ " INNER JOIN caminhoneta_carga AS caminhoneta_carga_exemplo ON (veiculo_exemplo.id = caminhoneta_carga_exemplo.id)"
				+ " WHERE caminhoneta_carga_exemplo.potencia_motor ?= :potencia"
				+ " AND caminhoneta_carga_exemplo.desenpenho ?= :desenpenho"
				+ " AND caminhoneta_carga_exemplo.capacidade_carga ?= :capacidadeCarga"
				+ " AND caminhoneta_carga_exemplo.tipo_acionamento_e  ?= :tipoAcionamentoEmbreagem"
				+ " AND caminhoneta_carga_exemplo.distancia_eixos ?= :distanciaEixos"
				+ " AND caminhoneta_carga_exemplo.capacidade_combustivel ?= :capacidadeCombustivel"
				+ " AND veiculo_exemplo.torque_Motor ?= :torqueMotor"
					+ " ORDER BY(caminhoneta_carga_exemplo.capacidade_carga, caminhoneta_carga_exemplo.potencia_motor, veiculo_exemplo.torque_motor,"
						+ " caminhoneta_carga_exemplo.desenpenho, caminhoneta_carga_exemplo.distancia_eixos, caminhoneta_carga_exemplo.tipo_acionamento_e,"
						+ " veiculo_exemplo.tipo_combustivel, caminhoneta_carga_exemplo.capacidade_combustivel) DESC";
	String SELECIONAR_AUTOMOVEL_PEQUENO = "SELECT categoria_veiculo.id, categoria_veiculo.tipo, categoria_veiculo.quilometragem_revisao, "
			+"categoria_veiculo.horas_revisao, categoria_veiculo.horas_limpesa,  categoria_veiculo.valor_diaria, categoria_veiculo.descricao, "
			+"categoria_veiculo.valor_livre, categoria_veiculo.valor_km, categoria_veiculo.veiculoexemplo_id FROM categoria_veiculo "
			+"INNER JOIN veiculo AS veiculo_exemplo ON(categoria_veiculo.veiculoexemplo_id = veiculo_exemplo.id) " 
			+"INNER JOIN automovel AS automovel_exemplo ON (veiculo_exemplo.id = automovel_exemplo.id) "
				+"WHERE automovel_exemplo.tipo = 0 "
					+"AND automovel_exemplo.tipo_cambio ?= :tipoCambio "
					+"AND automovel_exemplo.tipo_tamanho ?= :tamanhoVeiculo "
					+"AND veiculo_exemplo.qtd_porta ?= :quantidadePortas "
					+"AND veiculo_exemplo.qtd_passageiro ?= :quantidadePassageiro "
					+"AND veiculo_exemplo.tipo_combustivel ?= :tipoCombustivel "
						+"ORDER BY (automovel_exemplo.tipo_cambio, automovel_exemplo.tipo_tamanho, "
							+"veiculo_exemplo.qtd_porta, veiculo_exemplo.qtd_passageiro, "
							+"veiculo_exemplo.tipo_combustivel) DESC ";
	String SELECIONAR_CAMINHONETA_PASSAGEIRO  = "SELECT categoria_veiculo.id, categoria_veiculo.tipo, categoria_veiculo.quilometragem_revisao, "
			+"categoria_veiculo.horas_revisao, categoria_veiculo.horas_limpesa,  categoria_veiculo.valor_diaria, categoria_veiculo.descricao, "
			+"categoria_veiculo.valor_livre, categoria_veiculo.valor_km, categoria_veiculo.veiculoExemplo_id FROM categoria_veiculo "
			+"INNER JOIN veiculo AS veiculo_exemplo ON(categoria_veiculo.veiculoexemplo_id = veiculo_exemplo.id) " 
			+"INNER JOIN automovel AS automovel_exemplo ON (veiculo_exemplo.id = automovel_exemplo.id) "
				+"WHERE automovel_exemplo.tipo = 1 "
					+"AND automovel_exemplo.tipo_airbag ?= :tipoAirBag "
					+"AND automovel_exemplo.tipo_cambio ?= :tipoCambio "
					+"AND automovel_exemplo.tipo_tamanho ?= :tamanhoVeiculo "
					+"AND veiculo_exemplo.qtd_porta ?= :quantidadePortas "
					+"AND veiculo_exemplo.qtd_passageiro ?= :quantidadePassageiro "
					+"AND veiculo_exemplo.tipo_combustivel ?= :tipoCombustivel "
						+"ORDER BY (automovel_exemplo.tipo_airbag, automovel_exemplo.tipo_cambio, automovel_exemplo.tipo_tamanho, "
							+"veiculo_exemplo.qtd_porta, veiculo_exemplo.qtd_passageiro, "
							+"veiculo_exemplo.tipo_combustivel) DESC ";
	
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaPassageiro(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriaAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaPassageiro(Automovel automovel) throws DaoException;	
	
	public List<CategoriaVeiculo> buscarCategoriasSuperiorAutomovelPequeno(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> buscaPorBusca(CategoriaVeiculo categoriaVeiculo) throws DaoException;

}