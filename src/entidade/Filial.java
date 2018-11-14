package entidade;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Filial extends Entidade {
	
	private static final long serialVersionUID = 3891969678671342443L;
	private boolean ativo;
	@Column(length =20)
	private String nome;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Endereco endereco;
	@OneToMany(mappedBy = "filialEntrega", targetEntity = Locacao.class)
	private Set<Locacao> locacoesEntrega;
	@OneToMany(mappedBy = "filialRetirada", targetEntity = Locacao.class)
	private Set<Locacao> locacoesRetirada;
	@OneToMany(mappedBy = "filial", targetEntity = Reserva.class)
	private Set<Reserva> reservas;
	@OneToMany(mappedBy = "filial", targetEntity = Funcionario.class)
	private Set<Funcionario> funcionarios;
	@OneToMany(mappedBy = "filial", targetEntity = Veiculo.class)
	private Set<Veiculo> veiculos;
	
	
	public Set<Locacao> getLocacoesEntrega() {
		return locacoesEntrega;
	}

	public void setLocacoesEntrega(Set<Locacao> locacoesEntrega) {
		this.locacoesEntrega = locacoesEntrega;
	}

	public Set<Locacao> getLocacoesRetirada() {
		return locacoesRetirada;
	}

	public void setLocacoesRetirada(Set<Locacao> locacoesRetirada) {
		this.locacoesRetirada = locacoesRetirada;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((funcionarios == null) ? 0 : funcionarios.hashCode());
		result = prime * result + ((locacoesEntrega == null) ? 0 : locacoesEntrega.hashCode());
		result = prime * result + ((locacoesRetirada == null) ? 0 : locacoesRetirada.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
		result = prime * result + ((veiculos == null) ? 0 : veiculos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Filial))
			return false;
		Filial other = (Filial) obj;
		if (ativo != other.ativo)
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (funcionarios == null) {
			if (other.funcionarios != null)
				return false;
		} else if (!funcionarios.equals(other.funcionarios))
			return false;
		if (locacoesEntrega == null) {
			if (other.locacoesEntrega != null)
				return false;
		} else if (!locacoesEntrega.equals(other.locacoesEntrega))
			return false;
		if (locacoesRetirada == null) {
			if (other.locacoesRetirada != null)
				return false;
		} else if (!locacoesRetirada.equals(other.locacoesRetirada))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (reservas == null) {
			if (other.reservas != null)
				return false;
		} else if (!reservas.equals(other.reservas))
			return false;
		if (veiculos == null) {
			if (other.veiculos != null)
				return false;
		} else if (!veiculos.equals(other.veiculos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Filial [ativo=" + ativo + ", nome=" + nome + ", endereco=" + endereco + ", locacoesEntrega="
				+ locacoesEntrega + ", locacoesRetirada=" + locacoesRetirada + ", reservas=" + reservas
				+ ", funcionarios=" + funcionarios + ", veiculos=" + veiculos + "]";
	}	
	
	
	
	
	
}
