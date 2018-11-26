package entidade;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import enumeracoes.TipoLocacao;

@Entity
@NamedQuery(name = "locacao.totalPrevisaoEntrega", 
	query = "select count(*) from Locacao l "
			+ "where l.finalizado = false "
			+ "and l.filialEntrega = :filialEntrega "
			+ "and l.veiculo.categoriaVeiculo = :categoria "
			+ "and l.dataDevolucao <= :dataLimite")
public class Locacao extends Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "tipo_locacao")
	private TipoLocacao tipoLocacao;
	
	@Column(name = "data_retirada")
	private LocalDateTime dataRetirada;
	
	@Column(name = "data_devolucao")
	private LocalDateTime dataDevolucao;
	
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
	private Filial filialRetirada;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	@ManyToOne
	private Filial filialEntrega;
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

	public Filial getFilialRetirada() {
		return filialRetirada;
	}

	public void setFilialRetirada(Filial filialRetirada) {
		this.filialRetirada = filialRetirada;
	}

	public Filial getFilialEntrega() {
		return filialEntrega;
	}

	public void setFilialEntrega(Filial filialEntrega) {
		this.filialEntrega = filialEntrega;
	}

	public TipoLocacao getTipoLocacao() {
		return tipoLocacao;
	}

	public void setTipoLocacao(TipoLocacao tipoLocacao) {
		this.tipoLocacao = tipoLocacao;
	}

	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result + ((dataRetirada == null) ? 0 : dataRetirada.hashCode());
		result = prime * result + ((filialEntrega == null) ? 0 : filialEntrega.hashCode());
		result = prime * result + ((filialRetirada == null) ? 0 : filialRetirada.hashCode());
		result = prime * result + (finalizado ? 1231 : 1237);
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		result = prime * result + ((reservaOrigem == null) ? 0 : reservaOrigem.hashCode());
		result = prime * result + ((tipoLocacao == null) ? 0 : tipoLocacao.hashCode());
		result = prime * result + ((valorDiaria == null) ? 0 : valorDiaria.hashCode());
		result = prime * result + ((valorPago == null) ? 0 : valorPago.hashCode());
		result = prime * result + ((veiculo == null) ? 0 : veiculo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataDevolucao == null) {
			if (other.dataDevolucao != null)
				return false;
		} else if (!dataDevolucao.equals(other.dataDevolucao))
			return false;
		if (dataRetirada == null) {
			if (other.dataRetirada != null)
				return false;
		} else if (!dataRetirada.equals(other.dataRetirada))
			return false;
		if (filialEntrega == null) {
			if (other.filialEntrega != null)
				return false;
		} else if (!filialEntrega.equals(other.filialEntrega))
			return false;
		if (filialRetirada == null) {
			if (other.filialRetirada != null)
				return false;
		} else if (!filialRetirada.equals(other.filialRetirada))
			return false;
		if (finalizado != other.finalizado)
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		if (reservaOrigem == null) {
			if (other.reservaOrigem != null)
				return false;
		} else if (!reservaOrigem.equals(other.reservaOrigem))
			return false;
		if (tipoLocacao != other.tipoLocacao)
			return false;
		if (valorDiaria == null) {
			if (other.valorDiaria != null)
				return false;
		} else if (!valorDiaria.equals(other.valorDiaria))
			return false;
		if (valorPago == null) {
			if (other.valorPago != null)
				return false;
		} else if (!valorPago.equals(other.valorPago))
			return false;
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Locacao [tipoLocacao=" + tipoLocacao + ", dataRetirada=" + dataRetirada + ", dataDevolucao="
				+ dataDevolucao + ", valorDiaria=" + valorDiaria + ", valorPago=" + valorPago + ", finalizado="
				+ finalizado + ", reservaOrigem=" + reservaOrigem + ", cliente=" + cliente + ", veiculo=" + veiculo
				+ ", filialRetirada=" + filialRetirada + ", funcionario=" + funcionario + ", filialEntrega="
				+ filialEntrega + ", motorista=" + motorista + "]";
	}

	
}
