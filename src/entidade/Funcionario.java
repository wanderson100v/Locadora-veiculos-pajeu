package entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enumeracoes.Cargo;

@Entity
public class Funcionario extends Entidade{
	
	private static final long serialVersionUID = 1L;
	@Column(length = 100)
	private String nome;
	@Column(unique = true, nullable = false , length = 30)
	private String login;
	@Column(nullable = false)
	private Cargo cargo;
	private boolean ativo;
	@ManyToOne
	private Filial filial;
	@OneToMany(mappedBy = "funcionario", targetEntity = Locacao.class)
	private Set<Locacao> locacoes;
	@OneToMany(mappedBy = "funcionario", targetEntity = Reserva.class)
	private Set<Reserva> reservas;

	public Funcionario(String nome, String login, Cargo cargo) {
		super();
		this.nome = nome;
		this.login = login;
		this.cargo = cargo;
		this.ativo = true;
	}
	
	public Funcionario() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cargo getCargo() {
		return cargo;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(Set<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((locacoes == null) ? 0 : locacoes.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Funcionario))
			return false;
		Funcionario other = (Funcionario) obj;
		if (ativo != other.ativo)
			return false;
		if (cargo != other.cargo)
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (locacoes == null) {
			if (other.locacoes != null)
				return false;
		} else if (!locacoes.equals(other.locacoes))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
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
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [nome=" + nome + ", login=" + login + ", cargo=" + cargo + ", ativo=" + ativo + ", filial="
				+ filial + ", locacoes=" + locacoes + ", reservas=" + reservas + "]";
	}
	
	
}
