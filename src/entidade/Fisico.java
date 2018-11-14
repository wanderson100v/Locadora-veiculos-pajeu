package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.Sexo;

@Entity
public class Fisico extends Cliente {

	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;
	@Column(unique = true, nullable = false, length = 20)
	private String cpf;
	@Column(name = "identificacao_motorista",unique = true, nullable = false, length = 20)
	private String identificacaoMotorista;
	@Column(name = "numero_habilitacao",unique = true, nullable = false, length = 20)
	private String numeroHabilitacao;
	@Column(nullable = false)
	private Sexo sexo;
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getIdentificacaoMotorista() {
		return identificacaoMotorista;
	}
	
	public void setIdentificacaoMotorista(String identificacaoMotorista) {
		this.identificacaoMotorista = identificacaoMotorista;
	}
	
	public String getNumeroHabilitacao() {
		return numeroHabilitacao;
	}
	
	public void setNumeroHabilitacao(String numeroHabilitacao) {
		this.numeroHabilitacao = numeroHabilitacao;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((identificacaoMotorista == null) ? 0 : identificacaoMotorista.hashCode());
		result = prime * result + ((numeroHabilitacao == null) ? 0 : numeroHabilitacao.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Fisico))
			return false;
		Fisico other = (Fisico) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (identificacaoMotorista == null) {
			if (other.identificacaoMotorista != null)
				return false;
		} else if (!identificacaoMotorista.equals(other.identificacaoMotorista))
			return false;
		if (numeroHabilitacao == null) {
			if (other.numeroHabilitacao != null)
				return false;
		} else if (!numeroHabilitacao.equals(other.numeroHabilitacao))
			return false;
		if (sexo != other.sexo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fisico [dataNascimento=" + dataNascimento + ", cpf=" + cpf + ", identificacaoMotorista="
				+ identificacaoMotorista + ", numeroHabilitacao=" + numeroHabilitacao + ", sexo=" + sexo + "]";
	}
	
	
	
}
