package entidade;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import enumeracoes.EstadoRerserva;

@Entity
@NamedQuery(name = "reserva.totalDataRetirada", 
	query = "select count(*) from Reserva r "
			+ "where r.estadoReserva = 1 "
			+ "and r.categoriaVeiculo = :categoriaVeiculo ")
			//+ "and r.dataRetirada = :dataRetirada")
public class Reserva extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "data_retirada")
	private LocalDateTime dataRetirada;
	@Column(name = "data_devolucao")
	private LocalDateTime dataDevolucao;
	@Column(name = "valor")
	private float valor;
	@Column(name = "estado_reserva" ,nullable = false)
	private EstadoRerserva estadoReserva;
	@ManyToOne
	@JoinColumn(nullable = false)
	private CategoriaVeiculo categoriaVeiculo;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	@ManyToOne
	private Filial filial;

	public EstadoRerserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(EstadoRerserva estadoRerserva) {
		this.estadoReserva = estadoRerserva;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public CategoriaVeiculo getCategoriaVeiculo() {
		return categoriaVeiculo;
	}

	public void setCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) {
		this.categoriaVeiculo = categoriaVeiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((categoriaVeiculo == null) ? 0 : categoriaVeiculo.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result + ((dataRetirada == null) ? 0 : dataRetirada.hashCode());
		result = prime * result + ((estadoReserva == null) ? 0 : estadoReserva.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
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
		Reserva other = (Reserva) obj;
		if (categoriaVeiculo == null) {
			if (other.categoriaVeiculo != null)
				return false;
		} else if (!categoriaVeiculo.equals(other.categoriaVeiculo))
			return false;
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
		if (estadoReserva != other.estadoReserva)
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reserva [dataRetirada=" + dataRetirada + ", dataDevolucao=" + dataDevolucao + ", valor=" + valor
				+ ", estadoRerserva=" + estadoReserva + ", categoriaVeiculo=" + categoriaVeiculo + ", cliente="
				+ cliente + ", funcionario=" + funcionario + ", filial=" + filial + "]";
	}

}
