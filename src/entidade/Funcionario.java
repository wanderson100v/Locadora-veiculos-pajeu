package entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enumeracoes.Cargo;

@Entity
public class Funcionario extends Entidade{
	
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
	
	
}
