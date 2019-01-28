package entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "filial_seq")
@NamedQuery(name = "filial.buscaPorBusca" , 
		query = "select f from Filial as f"
				+ " inner join f.endereco as e"
				+ " where f.ativo = true and ("
				+ " upper(f.nome) like upper(:nome)"
				+ " or upper(e.rua) like upper(:rua)"
				+ " or upper(e.bairro) like upper(:bairro)"
				+ " or upper(e.cidade) like upper(:cidade)"
				+ " or upper(e.numero) like upper(:numero)"
				+ " or upper(e.cep) like upper(:cep))")
public class Filial extends Entidade {
	
	private static final long serialVersionUID = 3891969678671342443L;
	private boolean ativo;
	@Column(length =20, nullable = false)
	private String nome;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Endereco endereco;
	

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
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nome " + nome + ", Endereco: " + endereco;
	}	
}
