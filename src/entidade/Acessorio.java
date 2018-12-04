package entidade;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Acessorio extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@Column(length = 100, unique = true)
	private String nome;
	private float valor;
	private boolean depreciado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public boolean isDepreciado() {
		return depreciado;
	}

	public void setDepreciado(boolean depreciado) {
		this.depreciado = depreciado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (depreciado ? 1231 : 1237);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acessorio other = (Acessorio) obj;
		if (depreciado != other.depreciado)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nome = " + nome + ", Valor = " + valor + ", Depreciado = " + depreciado;
	}
	
	
	
}
