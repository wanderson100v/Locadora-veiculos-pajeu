package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.Sexo;

@Entity
// @PrimaryKeyJoinColumn(name = "id_cliente") // utilizado afim de atribbuir um nome a chave estrangeira a cliente : default = nome da pk da genarização
public class Fisico extends Cliente {
	
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
	
}
