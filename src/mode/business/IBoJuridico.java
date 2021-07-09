package mode.business;

import java.util.List;

import model.excecoes.BoException;
import model.entidade.Juridico;

public interface IBoJuridico extends IBussines<Juridico>{
	
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws BoException;
}