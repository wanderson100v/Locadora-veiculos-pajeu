package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reserva extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_retirada")
	private Date dateRetirada;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_devolucao")
	private Date dataDevolucao;
	@Column(name = "valor")
	private Date valor;
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

	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDateRetirada() {
		return dateRetirada;
	}

	public void setDateRetirada(Date dateRetirada) {
		this.dateRetirada = dateRetirada;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getValor() {
		return valor;
	}

	public void setValor(Date valor) {
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
		result = prime * result + ((dateRetirada == null) ? 0 : dateRetirada.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Reserva))
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
		if (dateRetirada == null) {
			if (other.dateRetirada != null)
				return false;
		} else if (!dateRetirada.equals(other.dateRetirada))
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
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reserva [dateRetirada=" + dateRetirada + ", dataDevolucao=" + dataDevolucao + ", valor=" + valor
				+ ", categoriaVeiculo=" + categoriaVeiculo + ", cliente=" + cliente + ", funcionario=" + funcionario
				+ ", filial=" + filial + "]";
	}
	
	
}
