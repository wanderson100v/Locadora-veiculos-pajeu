package dao;

import java.time.LocalDateTime;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.DaoException;

public interface IDaoLocacao extends IDao<Locacao>{
	String TOTAL_PREVISAO_ENTREGA = "totalPrevisaoEntrega";
	
	public int totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws DaoException;
	
}
