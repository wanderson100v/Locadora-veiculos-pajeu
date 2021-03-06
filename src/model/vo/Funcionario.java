package model.vo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 2, name = "idgen", sequenceName = "funcionario_seq", allocationSize =1)
public class Funcionario extends Entidade{
	
	private static final long serialVersionUID = 1L;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(unique = true, nullable = false, length = 30)
	private String cpf;
	private boolean ativo;
	@ManyToOne
	private Filial filial;
	
	public Funcionario(String nome, String cpf, boolean ativo, Filial filial) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.ativo = ativo;
		this.filial = filial;
	}

	public Funcionario() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
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
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (ativo != other.ativo)
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nome " + nome + ", CPF " + cpf + ", Ativo " + ((ativo)? "Sim": "N??o");
	}
}
