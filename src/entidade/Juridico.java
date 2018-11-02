package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Juridico extends Cliente {
	
	@Column(unique = true, nullable = false, length = 20)
	private String cnpj;
	@Column(nullable = false, length = 20)
	private String inscricaoEstadual;
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	
}
