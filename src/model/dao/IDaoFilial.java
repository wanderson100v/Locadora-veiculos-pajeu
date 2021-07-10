package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.Filial;

public interface IDaoFilial extends IDao<Filial>{
	
	String BUSCA_POR_BUSCA  = "select f from "+CAMINHO_CLASSE+"Filial as f"
			+ " inner join f.endereco as e"
			+ " where f.ativo = true and ("
			+ " upper(f.nome) like upper(:nome)"
			+ " or upper(e.rua) like upper(:rua)"
			+ " or upper(e.bairro) like upper(:bairro)"
			+ " or upper(e.cidade) like upper(:cidade)"
			+ " or upper(e.numero) like upper(:numero)"
			+ " or upper(e.cep) like upper(:cep))";
	
	public List<Filial> buscaPorBusca(Filial filial) throws DaoException;
	
}
