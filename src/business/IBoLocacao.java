package business;

import java.time.LocalDateTime;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.BoException;

public interface IBoLocacao extends IBussines<Locacao>{

	public int totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws BoException;
	
}
