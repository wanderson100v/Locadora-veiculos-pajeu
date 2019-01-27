package dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.DaoException;

public interface IDaoLocacao extends IDao<Locacao>{
	String TOTAL_PREVISAO_ENTREGA = "locacao.totalPrevisaoEntrega";
	
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws DaoException;

	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate)throws DaoException;
	
}
