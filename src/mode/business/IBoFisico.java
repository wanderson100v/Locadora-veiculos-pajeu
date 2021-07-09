package mode.business;


import java.time.LocalDate;
import java.util.List;

import model.excecoes.BoException;
import model.excecoes.ValidarException;
import model.entidade.Fisico;

public interface IBoFisico extends IBussines<Fisico> {
	
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException;
	
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws BoException;
	
	public void validarMotorista(Fisico fisico) throws ValidarException;
	
}
