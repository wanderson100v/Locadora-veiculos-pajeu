package business;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import dao.DaoLocacao;
import dao.IDaoLocacao;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Fisico;
import entidade.Locacao;
import excecoes.BoException;
import excecoes.DaoException;

public class BoLocacao implements IBoLocacao {
	private IDaoLocacao daoLocacao = new DaoLocacao();
	private IBoVeiculo boVeiculo = BoVeiculo.getInstance();
	private static IBoLocacao instance;
	
	private BoLocacao() {}
	
	public static IBoLocacao getInstance() {
		if(instance == null)
			instance = new BoLocacao();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Locacao entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoLocacao.editar(entidade);
			}else {
				validarLocacacao(entidade);
				calcularValorLocacao(entidade);
				daoLocacao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Locacao entidade) throws BoException {
		try {
			daoLocacao.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Locacao buscarID(Long id) throws BoException {
		try {
			return daoLocacao.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Locacao> buscarAll() throws BoException {
		try {
			return daoLocacao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscarPorExemplo(Locacao exemploEntidade) throws BoException {
		try {
			return daoLocacao.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}


	@Override
	public int totalLocacoePrevisaoEntrega(Filial filialEntrega, CategoriaVeiculo categoriaVeiculo, LocalDateTime dataLimite) throws BoException {
		try {
			return daoLocacao.totalLocacoePrevisaoEntrega(filialEntrega, categoriaVeiculo, dataLimite);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void calcularValorLocacao(Locacao locacao) {
		Period period = Period.between(locacao.getDataRetirada(),locacao.getDataDevolucao());
		
		Float valorDiaria;
		if(locacao.getReservaOrigem() != null)
			valorDiaria = locacao.getReservaOrigem().getValor();
		else
			valorDiaria = locacao.getVeiculo().getCategoriaVeiculo().getValorDiaria();
		Float valorLocacao = valorDiaria * period.getDays();
		Long horasAposTerminoPrevistoDiaria = Duration.between(locacao.getDataRetirada(),locacao.getDataDevolucao()).toHours() % 24;
		
		if(horasAposTerminoPrevistoDiaria <=4) {
			valorLocacao += valorDiaria/4; 
		}else
			valorDiaria += valorDiaria;
		locacao.setValorDiaria(valorDiaria);
	}
	
	private void validarLocacacao(Locacao locacao) throws BoException{
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(Period.between(locacao.getDataRetirada(),locacao.getDataDevolucao()).getDays()<1)
			stringBuilder.append("O período de locação é inferior a uma diaria./n");
		
		if(locacao.getMotorista() != null)
			validarMotoristaLocacao(locacao.getMotorista(),locacao.getDataDevolucao(),stringBuilder);
		else
			validarMotoristaLocacao((Fisico)locacao.getMotorista(),locacao.getDataDevolucao(),stringBuilder);
		
		if(boVeiculo.totalManutencoesPententes(locacao.getVeiculo()) != 0)
			stringBuilder.append("Há pendências de manutenções no veiculo");
		
		if(stringBuilder.length()>0)
			throw new BoException("Erro(s) ao cadastrar locação"+ stringBuilder.toString());
	}
	
	private void validarMotoristaLocacao(Fisico motorista,LocalDate dataLimite, StringBuilder stringBuilder){
		
		if(Period.between(motorista.getDataNascimento(), LocalDate.now()).getYears() <= 21)
			stringBuilder.append("Idade do motoriste é inferior a 21 anos./n");
		
		if(dataLimite.isAfter(motorista.getDataValidadeHabilitacao()))
			stringBuilder.append("A validade da habilitação é inferior,/n ao prazo estipulado para o termino da locação./n");
	}
	
}
