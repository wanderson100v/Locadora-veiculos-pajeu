package entidade;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Acessorio extends Entidade {
	@Column(length = 100)
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
	
}
