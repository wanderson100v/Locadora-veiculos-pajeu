package model.business;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.dao.DaoLocacao;
import model.dao.IDaoLocacao;
import model.enumeracoes.TipoLocacao;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Locacao;

public class BoLocacao extends BoAdapter<Locacao> implements IBoLocacao {
	
	private IDaoLocacao daoLocacao;
	
	public BoLocacao() {
		super(new DaoLocacao());
		this.daoLocacao = (IDaoLocacao) daoEntidade;
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
						  "- Locação feita a partir de reserva. Valor"
						+ "\npor diaria e taxas por tipo de locação"
						+ "\nserão definidos pela categoria reservada:"
						+ "\n\t- Valor por Diaria = "+valorDiaria);
				
				if(locacao.getTipoLocacao() == TipoLocacao.KM_LIVRE) {
					valorKm = locacao.getReservaOrigem().getCategoriaVeiculo().getValorLivre();
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
						+ "\nvalor por diaria e taxas por tipo de locação"
						+ "\nserão definidos pela categoria do veículo locado:"
						+ "\n\t- Valor por Diaria = "+valorDiaria);
				if(locacao.getTipoLocacao() == TipoLocacao.KM_LIVRE) {
					valorKm = locacao.getVeiculo().getCategoriaVeiculo().getValorLivre();
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
			notaFiscal.append("\n- Perído de locacao de "+dias+" dias e "+horasAposTerminoPrevistoDiaria+" horas"
							 +"\n\t- Valor por dias de diaria = "+valorTotalDiarias);
			if(horasAposTerminoPrevistoDiaria >0)
				if(horasAposTerminoPrevistoDiaria <=4) {
					notaFiscal.append(
							  "\n\t- Valor horas restantes é 1/4 do valor de"
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
	public void validarLocacao(Locacao locacao, StringBuilder erroLocacao) throws BoException{
		if(locacao.getCliente() != null && locacao.getVeiculo() != null && locacao.getFuncionario()!= null
				&& locacao.getTipoLocacao() !=null &&  locacao.getDataRetirada() != null && locacao.getDataDevolucao() != null) 
		{
			if(locacao.getFilialEntrega() == null)
				locacao.setFilialEntrega(locacao.getFilialRetirada());
			if(Duration.between(locacao.getDataRetirada(),locacao.getDataDevolucao()).toDays()<1)
				erroLocacao.append("\tO período de locacao é inferior a uma diaria.\n");
		}
		else
			erroLocacao.append("Um ou mais campos obrigatórios vazios");
	}

	@Override
	public List<Locacao> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoLocacao.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate) throws BoException {
		try {
			return daoLocacao.buscaPorBuscaAbrangente(busca, locacao, de , ate);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> buscarLocacoesFinalizadas(LocalDate de, LocalDate ate, String agruparPor)throws BoException {
		try {
			return daoLocacao.buscarLocacoesFinalizadas(de , ate,agruparPor);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
}
