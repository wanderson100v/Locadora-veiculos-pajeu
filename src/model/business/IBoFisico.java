package model.business;


import java.time.LocalDate;
import java.util.List;

import model.excecoes.BoException;
import model.excecoes.ValidarException;
import model.vo.Fisico;

public interface IBoFisico extends IBussines<Fisico> {
	
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException;
	
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws BoException;
	
	public void validarClienteMotoristaLocacao(Fisico motorista,LocalDate dataLimite, StringBuilder erroLocacao);
	
	public void validarMotorista(Fisico fisico) throws ValidarException;
	
	
}
