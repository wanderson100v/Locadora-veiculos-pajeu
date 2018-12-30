package business;


import java.time.LocalDate;
import java.util.List;

import entidade.Fisico;
import excecoes.BoException;
import excecoes.ValidarException;

public interface IBoFisico extends IBussines<Fisico> {
	
	public List<Fisico> buscaPorBusca(Fisico fisico) throws BoException;
	
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException;
	
	public void validarMotorista(Fisico fisico) throws ValidarException;
	
}
