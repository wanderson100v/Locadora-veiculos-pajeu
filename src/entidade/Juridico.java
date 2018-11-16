package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Juridico extends Cliente {
	
	private static final long serialVersionUID = 1L;
	@Column(unique = true, nullable = false, length = 20)
	private String cnpj;
	@Column(name = "inscricao_estadual",unique = true,nullable = false, length = 20)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((inscricaoEstadual == null) ? 0 : inscricaoEstadual.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Juridico))
			return false;
		Juridico other = (Juridico) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (inscricaoEstadual == null) {
			if (other.inscricaoEstadual != null)
				return false;
		} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Juridico [cnpj=" + cnpj + ", inscricaoEstadual=" + inscricaoEstadual + "]";
	}
	
}
