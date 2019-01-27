package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.BoException;

public interface IBoLocacao extends IBussines<Locacao>{

	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws BoException;
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao) throws BoException;
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao, int novaQuilometragem, LocalDateTime dataDevulucaoAtt, 
			Boolean abastecer , Boolean limpeza) throws BoException;

	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate) throws BoException ;
}
