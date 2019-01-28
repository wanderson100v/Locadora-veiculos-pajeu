package entidade;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import enumeracoes.Sexo;

@Entity
//@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "fisico_seq")
@NamedQuery(name = "fisico.buscaPorBusca" , 
query = "select f from Fisico as f "
		+ "where upper(f.codigo) like upper(:codigo)"
		+ " or upper(f.nome) like upper(:nome)"
		+ " or upper(f.identificacaoMotorista) like upper(:identificacaoMotorista)"
		+ " or upper(f.numeroHabilitacao) like upper(:numeroHabilitacao)"
		+ " or upper(f.email) like upper(:email)"
		+ " or upper(f.telefone) like upper(:telefone)"
		+ " or upper(f.cpf) like upper(:cpf)")
public class Fisico extends Cliente {

	private static final long serialVersionUID = 1L;
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	@Column(unique = true, nullable = false, length = 20)
	private String cpf;
	@Column(name = "data_validade_habilitacao")
	private LocalDate dataValidadeHabilitacao;
	@Column(name = "identificacao_motorista",unique = true, length = 20)
	private String identificacaoMotorista;
	@Column(name = "numero_habilitacao",unique = true,length = 20)
	private String numeroHabilitacao;
	@Column(nullable = false)
	private Sexo sexo;
	
	public Fisico(Long id, boolean ativo, String nome, String codigo, String email, String telefone, Endereco endereco,
			LocalDate dataNascimento, String cpf, LocalDate dataValidadeHabilitacao, String identificacaoMotorista,
			String numeroHabilitacao, Sexo sexo) {
		super(id, ativo, nome, codigo, email, telefone, endereco);
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.dataValidadeHabilitacao = dataValidadeHabilitacao;
		this.identificacaoMotorista = identificacaoMotorista;
		this.numeroHabilitacao = numeroHabilitacao;
		this.sexo = sexo;
	}
	
	public Fisico() {}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
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

	public LocalDate getDataValidadeHabilitacao() {
		return dataValidadeHabilitacao;
	}

	public void setDataValidadeHabilitacao(LocalDate dataValidadeHabilitacao) {
		this.dataValidadeHabilitacao = dataValidadeHabilitacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((dataValidadeHabilitacao == null) ? 0 : dataValidadeHabilitacao.hashCode());
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
		if (getClass() != obj.getClass())
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
		if (dataValidadeHabilitacao == null) {
			if (other.dataValidadeHabilitacao != null)
				return false;
		} else if (!dataValidadeHabilitacao.equals(other.dataValidadeHabilitacao))
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
		return super.toString()+ ", CPF "+cpf;
	}
	
}
