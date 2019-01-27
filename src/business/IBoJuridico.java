package business;

import java.util.List;

import entidade.Juridico;
import excecoes.BoException;

public interface IBoJuridico extends IBussines<Juridico>{
	
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws BoException;
}