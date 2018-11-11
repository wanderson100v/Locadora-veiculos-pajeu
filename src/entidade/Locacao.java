package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.TipoLocacao;

@Entity
public class Locacao extends Entidade {
	
	@Column(name = "tipo_locacao")
	private TipoLocacao tipoLocacao;
	
	@Column(name = "data_retirada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRetirada;
	
	@Column(name = "data_devolucao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDevolucacao;
	
	@Column(name = "valor_diaria")
	private Float valorDiaria;
	
	@Column(name = "valor_pago")
	private Float valorPago;
	
	private boolean finalizado;
	@OneToOne
	private Reserva reservaOrigem;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Veiculo veiculo;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Filial fifialRetirada;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	@ManyToOne
	private Filial fifialEntrega;
	@ManyToOne
	private Fisico motorista;
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Fisico getMotorista() {
		return motorista;
	}

	public Reserva getReservaOrigem() {
		return reservaOrigem;
	}

	public void setReservaOrigem(Reserva reservaOrigem) {
		this.reservaOrigem = reservaOrigem;
	}

	public void setMotorista(Fisico motorista) {
		this.motorista = motorista;
	}

	public boolean isFinalizado() {
		return finalizado;
	}
	
	
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Filial getFifialRetirada() {
		return fifialRetirada;
	}

	public void setFifialRetirada(Filial fifialRetirada) {
		this.fifialRetirada = fifialRetirada;
	}

	public Filial getFifialEntrega() {
		return fifialEntrega;
	}

	public void setFifialEntrega(Filial fifialEntrega) {
		this.fifialEntrega = fifialEntrega;
	}

	public TipoLocacao getTipoLocacao() {
		return tipoLocacao;
	}

	public void setTipoLocacao(TipoLocacao tipoLocacao) {
		this.tipoLocacao = tipoLocacao;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Date getDataDevolucacao() {
		return dataDevolucacao;
	}

	public void setDataDevolucacao(Date dataDevolucacao) {
		this.dataDevolucacao = dataDevolucacao;
	}

	public Float getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(Float valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public Float getValorPago() {
		return valorPago;
	}

	public void setValorPago(Float valorPago) {
		this.valorPago = valorPago;
	}
	
	
	
	
	
}
