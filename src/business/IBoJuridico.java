package business;

import java.util.List;

import entidade.Juridico;
import excecoes.BoException;

public interface IBoJuridico extends IBussines<Juridico>{
	
	public List<Juridico> buscaPorBusca(Juridico juridico) throws BoException;

}