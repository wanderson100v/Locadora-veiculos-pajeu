package business;

import java.util.Date;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.BoException;

public interface IBoLocacao extends IBussines<Locacao>{

	public int totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,Date dataLimite)throws BoException;
	
}
