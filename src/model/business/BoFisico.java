package model.business;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import model.dao.DaoFisico;
import model.dao.IDaoFisico;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.excecoes.ValidarException;
import model.vo.Fisico;
 
public class BoFisico extends Bo<Fisico> implements IBoFisico{
	private IDaoFisico daoFisico;
	
	public BoFisico() {
		super(new DaoFisico());
		this.daoFisico = (IDaoFisico) daoEntidade;
	}
	
	@Override
	public void cadastrar(Fisico entidade) throws BoException, DaoException {
		atribuirCodigo(entidade);
		super.cadastrar(entidade);
	}
	
	@Override
	public void inativarRegistro(Fisico entidade) {
		entidade.setAtivo(false);
	}
	
	public void validarClienteMotoristaLocacao(Fisico motorista,LocalDate dataLimite, StringBuilder erroLocacao){
		StringBuilder erroMotoristaLocacao = new StringBuilder();
		if(Period.between(motorista.getDataNascimento(), LocalDate.now()).getYears() <= 21)
			erroMotoristaLocacao.append("\tIdade do motoriste é inferior a 21 anos.\n");
		if(motorista.getDataValidadeHabilitacao() != null && dataLimite.isAfter(motorista.getDataValidadeHabilitacao()))
			erroMotoristaLocacao.append("\tA validade da habilitação é inferior,\n ao prazo estipulado para o termino da locação.\n");
		if(erroMotoristaLocacao.length() >0)
			erroLocacao.append("Erros em motorista da locação:\n"+erroMotoristaLocacao.toString());
		try {
			validarMotorista(motorista);
		} catch (ValidarException e) {
			erroLocacao.append(e.getMessage());
		}
	}

	@Override
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException {
		try {
			Fisico fisico = new Fisico();
			fisico.setCpf(dadoMotorista);
			fisico.setNome(dadoMotorista);
			fisico.setEmail(dadoMotorista);
			fisico.setCpf(dadoMotorista);
			fisico.setIdentificacaoMotorista(dadoMotorista);
			fisico.setNumeroHabilitacao(dadoMotorista);
			return daoFisico.buscarMotoristasValidos(fisico, dataSuperior);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public void validarMotorista(Fisico fisico) throws ValidarException {
		StringBuilder erro = new StringBuilder();
		if(fisico.getIdentificacaoMotorista() == null)
			erro.append("\tSem identificação\n");
		if(fisico.getDataValidadeHabilitacao() == null)
			erro.append("\tSem data de validade de habilitação\n");
		else if (fisico.getDataValidadeHabilitacao().isBefore(LocalDate.now()))
			erro.append("\tHabilitação vencida\n");
		if(fisico.getNumeroHabilitacao() == null)
			erro.append("\tSem numero de habilitação");
		if(erro.length() >0)
			throw new ValidarException("Foram encontrado os seguintes erros no motorista:\n "+erro.toString());
		
	}

	private void atribuirCodigo(Fisico entidade){
		entidade.setCodigo("PJ"+entidade.getCpf());
	}

	@Override
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws BoException {
		try {
			return daoFisico.buscaPorBuscaAbrangente(busca,fisico);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
