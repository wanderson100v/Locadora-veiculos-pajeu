package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Filial extends Entidade {
	
	@Column(length =20)
	private String nome;
	private Boolean estado;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Boolean getEstado() {
		return estado;
	}
	
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
