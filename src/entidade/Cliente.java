package entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "cliente_seq")
@NamedQuery(name = "cliente.buscaPorBusca" , 
		query = "select c from Cliente as c"
				+ " where c.ativo = true and ("
				+ " upper(c.nome) like upper(:nome)"
				+ " or upper(c.codigo) like upper(:codigo)"
				+ " or upper(c.email) like upper(:email)"
				+ " or upper(c.telefone) like upper(:telefone))")
public class Cliente extends Entidade{
	
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private Boolean ativo;
	@Column(length = 100 , nullable = false)
	private String nome;
	@Column(length = 50, unique = true)
	private String codigo;
	@Column(length = 100)
	private String email;
	@Column(length = 30)
	private String telefone;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Endereco endereco;
	
	public Cliente(Long id, boolean ativo, String nome, String codigo, String email, String telefone,
			Endereco endereco) {
		super(id);
		this.ativo = ativo;
		this.nome = nome;
		this.codigo = codigo;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public Cliente() {}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean isAtivo() {
		return ativo;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		Cliente other = (Cliente) obj;
		if (ativo != other.ativo)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nome " + nome + ", Código " + codigo;
	}
	
}
