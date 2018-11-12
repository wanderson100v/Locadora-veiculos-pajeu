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
	
	
	
}
