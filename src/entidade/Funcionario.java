package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.Cargo;

@Entity
public class Funcionario {
	
	@Column(length = 100)
	private String nome;
	@Column(unique = true, nullable = false , length = 30)
	private String login;
	@Column(nullable = false)
	private Cargo cargo;
	private boolean ativo;

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
