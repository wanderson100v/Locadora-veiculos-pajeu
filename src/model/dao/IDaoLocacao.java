package model.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.excecoes.DaoException;
import model.entidade.CategoriaVeiculo;
import model.entidade.Filial;
import model.entidade.Locacao;

public interface IDaoLocacao extends IDao<Locacao>{
	String TOTAL_PREVISAO_ENTREGA = "locacao.totalPrevisaoEntrega";
	
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws DaoException;

	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate)throws DaoException;

	public List<Map<String, Object>> buscarLocacoesFinalizadas(LocalDate de , LocalDate ate, String agruparPor) throws DaoException;
}
