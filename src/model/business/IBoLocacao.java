package model.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Locacao;

public interface IBoLocacao extends IBussines<Locacao>{

	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws BoException;
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao) throws BoException;
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao, int novaQuilometragem, LocalDateTime dataDevulucaoAtt, 
			Boolean abastecer , Boolean limpeza) throws BoException;

	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate) throws BoException ;
	
	public List<Map<String, Object>> buscarLocacoesFinalizadas(LocalDate de , LocalDate ate, String agruparPor) throws BoException ;
	
	public void validarLocacao(Locacao locacao, StringBuilder erroLocacao) throws BoException;
}
