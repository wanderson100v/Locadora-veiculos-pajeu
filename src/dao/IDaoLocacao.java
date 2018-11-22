package dao;

import java.util.Date;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.DaoException;

public interface IDaoLocacao extends IDao<Locacao>{
	String TOTAL_PREVISAO_ENTREGA = "totalPrevisaoEntrega";
	
	public int totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,Date dataLimite)throws DaoException;
	
}
