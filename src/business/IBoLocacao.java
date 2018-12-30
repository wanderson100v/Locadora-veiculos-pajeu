package business;

import java.time.LocalDateTime;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.BoException;

public interface IBoLocacao extends IBussines<Locacao>{

	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws BoException;
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao) throws BoException;
}
