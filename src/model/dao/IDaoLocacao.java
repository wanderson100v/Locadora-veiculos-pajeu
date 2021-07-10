package model.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.excecoes.DaoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Locacao;

public interface IDaoLocacao extends IDao<Locacao>{
	String TOTAL_PREVISAO_ENTREGA = "select count(*) from "+CAMINHO_CLASSE+"Locacao l "
		+ "where l.finalizado = false "
		+ "and l.filialEntrega = :filialEntrega "
		+ "and l.veiculo.categoriaVeiculo = :categoria "
		+ "and l.dataDevolucao <= :dataLimite";
	
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws DaoException;

	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate)throws DaoException;

	public List<Map<String, Object>> buscarLocacoesFinalizadas(LocalDate de , LocalDate ate, String agruparPor) throws DaoException;
}
