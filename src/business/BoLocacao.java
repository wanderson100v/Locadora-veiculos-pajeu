package business;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Map;

import dao.DaoLocacao;
import dao.IDaoLocacao;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Fisico;
import entidade.Locacao;
import entidade.Veiculo;
import enumeracoes.EstadoRerserva;
import enumeracoes.TipoLocacao;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

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
	public void cadastrarEditar(Locacao locacao) throws BoException {
		try {
			if(locacao.getId() != null) {
				daoLocacao.editar(locacao);
			}else {
				validarLocacao(locacao);
				daoLocacao.cadastrar(locacao);
				locacao.getVeiculo().setLocado(true);
				boVeiculo.cadastrarEditar(locacao.getVeiculo());
				if(locacao.getReservaOrigem()!=null) {
					locacao.getReservaOrigem().setEstadoReserva(EstadoRerserva.EFETIVADA);
					BoReserva.getInstance().cadastrarEditar(locacao.getReservaOrigem());
				}
				
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
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega, CategoriaVeiculo categoriaVeiculo, LocalDateTime dataLimite) throws BoException {
		try {
			return daoLocacao.totalLocacoePrevisaoEntrega(filialEntrega, categoriaVeiculo, dataLimite);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao) throws BoException {
		return calcularValorLocacaoDetalhamento(locacao,0,locacao.getDataDevolucao(),false, false);
	}
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao, int novaQuilometragem, LocalDateTime dataDevulucaoAtt, Boolean abastecer , Boolean limpeza) throws BoException {
		try {
			StringBuilder notaFiscal = new StringBuilder();
			Float valorDiaria = 0f;
			Float valorLocacao = 0f;
			Float valorKm = 0f;
			long quilometrosRodados = novaQuilometragem - locacao.getVeiculo().getQuilometragem(); 
			
			if(locacao.getReservaOrigem()!= null) {
				valorDiaria =  locacao.getReservaOrigem().getCategoriaVeiculo().getValorDiaria();
				notaFiscal.append(
						  "- Locação feita a partir de reserva valor"
						+ "\npor diaria e taxas por tipo de Locação"
						+ "\nserão definidos pela categoria reservada:"
						+ "\n\t- Valor por Diaria = "+valorDiaria);
				
				if(locacao.getTipoLocacao() == TipoLocacao.KM_LIVRE) {
					valorKm = locacao.getReservaOrigem().getCategoriaVeiculo().getValorLivre();
					valorLocacao += valorKm;
					notaFiscal.append("\n\tTaxa Km Livre = "+valorKm);
				}else {
					if(quilometrosRodados > 0) {
						valorKm = locacao.getReservaOrigem().getCategoriaVeiculo().getValorKm();
						float valorKmRodados = quilometrosRodados * valorKm;
						notaFiscal.append(
								  "\n\t- Taxa Km Contole(Por unidade de quilometro"
								+ "\n\t  rodado) = "+valorKm+", foram rodados "+quilometrosRodados
								+ "\n\t  totalizando = "+valorKmRodados);
						valorKm = valorKmRodados;
					}
				}
			}else {
				valorDiaria =  locacao.getVeiculo().getCategoriaVeiculo().getValorDiaria();
				notaFiscal.append(  
						  "- Locação não iniciada a partir de reserva"
						+ "\nvalor por diaria e taxas por tipo de Locação"
						+ "\nserão definidos pela categoria do veículo locado:"
						+ "\n\t- Valor por Diaria = "+valorDiaria);
				if(locacao.getTipoLocacao() == TipoLocacao.KM_LIVRE) {
					valorKm = locacao.getVeiculo().getCategoriaVeiculo().getValorLivre();
					valorLocacao += valorKm;
					notaFiscal.append("\n\t- Taxa Km Livre = "+valorKm);
				}else{
					if(quilometrosRodados > 0) {
						valorKm = locacao.getVeiculo().getCategoriaVeiculo().getValorKm();
						float valorKmRodados = quilometrosRodados * valorKm;
						notaFiscal.append(
								  "\n\t- Taxa Km Contole(Por unidade de quilometro"
								+ "\n\t  rodado) = "+valorKm+", foram rodados "+quilometrosRodados
								+ "\n\t  totalizando = "+valorKmRodados);
						valorKm = valorKmRodados;
					}
				}
			}
			
			Long dias = Duration.between(locacao.getDataRetirada(),dataDevulucaoAtt).toDays();
			Long horasAposTerminoPrevistoDiaria = Duration.between(locacao.getDataRetirada(),dataDevulucaoAtt).toHours() % 24;
			Float valorTotalDiarias = valorDiaria * dias;
			Float valorHoras = 0f;
			notaFiscal.append("\n- Período de locacao de "+dias+" dias e "+horasAposTerminoPrevistoDiaria+" horas"
							 +"\n\t- Valor por dias de diaria = "+valorTotalDiarias);
			if(horasAposTerminoPrevistoDiaria >0)
				if(horasAposTerminoPrevistoDiaria <=4) {
					notaFiscal.append(
							  "\n\t- Valor horas restantes é 1/4 valor de"
							+ "\n\t  diaria = "+valorDiaria/4);
					valorHoras = valorDiaria/4; 
				}else {
					notaFiscal.append("\n\t- Valor horas restantes é mais uma diaria = "+valorDiaria);
					valorHoras = valorDiaria;
				}
			valorLocacao = valorHoras + valorTotalDiarias + valorKm;
			Float taxaLimpeza = 0f;
			Float taxaAbastecer = 0f;
			if(limpeza || abastecer) {
				notaFiscal.append("\n- Taxas referêntes a manutenção de veículo");
				if(limpeza) {
					taxaLimpeza = valorLocacao * 0.02f;
					notaFiscal.append("\n\t- Limpesa = "+taxaLimpeza);
				}if(abastecer) {
					taxaAbastecer = valorLocacao * 0.03f;
					notaFiscal.append("\n\t- Abastecimento = "+taxaAbastecer);
				}
			}
			valorLocacao += taxaLimpeza + taxaAbastecer;
			notaFiscal.append(
					 "\n- Valor total de locação ="+valorLocacao
					+"\n- Valor inicial pago ="+ locacao.getValorPago()
					+"\n- Valor Restante = "+(valorLocacao - locacao.getValorPago()));
			return new Object[]{valorLocacao,notaFiscal.toString()};
		}catch (NullPointerException e) {
			throw new BoException("É necessário tipo, hora de retirada/devolução da locação "
					+ "como também reserva ou veículo selecionado para calculo de valor de locação");
		}
	}
	private void validarLocacao(Locacao locacao) throws BoException{
		if(locacao.getCliente() != null && locacao.getVeiculo() != null && locacao.getFuncionario()!= null
				&& locacao.getTipoLocacao() !=null &&  locacao.getDataRetirada() != null && locacao.getDataDevolucao() != null) 
		{
			StringBuilder erroLocacao = new StringBuilder();
			if(locacao.getFilialEntrega() == null)
				locacao.setFilialEntrega(locacao.getFilialRetirada());
			if(Duration.between(locacao.getDataRetirada(),locacao.getDataDevolucao()).toDays()<1)
				erroLocacao.append("\tO período de locação é inferior a uma diaria.\n");
			if(locacao.getMotorista() == null) 
				if(locacao.getCliente() instanceof Fisico) {
					locacao.setMotorista((Fisico) locacao.getCliente());
					validarClienteMotoristaLocacao(locacao.getMotorista(),locacao.getDataDevolucao().toLocalDate(),erroLocacao);
				}else
					erroLocacao.append("\tO Motorista selecionado não é uma pessoa física\n");
			if(erroLocacao.length()>0) {
				throw new ValidarException("Erro(s) ao cadastrar locação : \n"+ erroLocacao.toString());
			}
		}
		else
			throw new ValidarException("Um ou mais campos obrigatórios vazios");
	}
	/**
	 * Só é mostrado em tela para seleção veiculos ativos não locados e sem manutenções pendentes
	 */
	@Deprecated 
	public void validarVeiculoLocacao(Veiculo veiculo, StringBuilder erroLocacao) throws BoException {
		StringBuilder erroVeiculoLocacao = new StringBuilder();
		if(veiculo.getLocado())
			erroVeiculoLocacao.append("\tJá esta locado.\n");
		if(veiculo.isAtivo())
			erroVeiculoLocacao.append("\tNão ativo.\n");
		if(BoVeiculo.getInstance().totalManutencoesPententes(veiculo) != 0)
			erroVeiculoLocacao.append("\tCom Manutenções pendentes.\n");
		if(erroVeiculoLocacao.length() >0)
			erroLocacao.append("Erros em veículo : \n"+erroVeiculoLocacao.toString());
		
	}
	/**
	 * Utilizado apenas para o caso do motorista ser o proprio cliente dado que em tela só é possiblitada
	 * a seleção de motoristas aptos para locação em questão
	 */
	public void validarClienteMotoristaLocacao(Fisico motorista,LocalDate dataLimite, StringBuilder erroLocacao){
		StringBuilder erroMotoristaLocacao = new StringBuilder();
		if(Period.between(motorista.getDataNascimento(), LocalDate.now()).getYears() <= 21)
			erroMotoristaLocacao.append("\tIdade do motoriste é inferior a 21 anos.\n");
		if(motorista.getDataValidadeHabilitacao() != null && dataLimite.isAfter(motorista.getDataValidadeHabilitacao()))
			erroMotoristaLocacao.append("\tA validade da habilitação é inferior,\n ao prazo estipulado para o termino da locação.\n");
		if(erroMotoristaLocacao.length() >0)
			erroLocacao.append("Erros em motorista da locação:\n"+erroMotoristaLocacao.toString());
		try {
			BoFisico.getInstance().validarMotorista(motorista);
		} catch (ValidarException e) {
			erroLocacao.append(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoLocacao.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	
	@Override
	public List<Locacao> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws BoException {
		try {
			return daoLocacao.buscaPorBuscaAbrangente(busca, restricoes);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
}
