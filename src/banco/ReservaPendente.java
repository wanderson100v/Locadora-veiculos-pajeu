package banco;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Immutable;

import entidade.Entidade;

@Entity(name = "reserva_pendente")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "reserva_pendente_seq")
@Immutable
@NamedQuery(name = "reservaPendente.buscarPorCliente" , 
			query = "select r from banco.ReservaPendente as r "
					+ " where upper(r.nomeCliente) like upper(:nome) "
					+ " or upper(r.codigoCliente) like upper(:codigo)"
					+ " or upper(r.telefone) like upper(:telefone) "
					+ " or upper(r.email) like upper(:email)"
					+ " order by retirada")
public class ReservaPendente extends Entidade{
	
	private static final long serialVersionUID = 1L;
	private String tipo,telefone,email;
	@Column(name = "nome_filial")
	private String nomeFilial;
	@Column(name = "nome_cliente")
	private String nomeCliente;
	@Column(name = "codigo_cliente")
	private String codigoCliente;
	@Column(name = "nome_funcionario")
	private String nomeFuncionario;
	private LocalDateTime retirada, devolucao;
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public LocalDateTime getRetirada() {
		return retirada;
	}

	public void setRetirada(LocalDateTime retirada) {
		this.retirada = retirada;
	}

	public LocalDateTime getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(LocalDateTime devolucao) {
		this.devolucao = devolucao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigoCliente == null) ? 0 : codigoCliente.hashCode());
		result = prime * result + ((devolucao == null) ? 0 : devolucao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nomeCliente == null) ? 0 : nomeCliente.hashCode());
		result = prime * result + ((nomeFilial == null) ? 0 : nomeFilial.hashCode());
		result = prime * result + ((nomeFuncionario == null) ? 0 : nomeFuncionario.hashCode());
		result = prime * result + ((retirada == null) ? 0 : retirada.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		ReservaPendente other = (ReservaPendente) obj;
		if (codigoCliente == null) {
			if (other.codigoCliente != null)
				return false;
		} else if (!codigoCliente.equals(other.codigoCliente))
			return false;
		if (devolucao == null) {
			if (other.devolucao != null)
				return false;
		} else if (!devolucao.equals(other.devolucao))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nomeCliente == null) {
			if (other.nomeCliente != null)
				return false;
		} else if (!nomeCliente.equals(other.nomeCliente))
			return false;
		if (nomeFilial == null) {
			if (other.nomeFilial != null)
				return false;
		} else if (!nomeFilial.equals(other.nomeFilial))
			return false;
		if (nomeFuncionario == null) {
			if (other.nomeFuncionario != null)
				return false;
		} else if (!nomeFuncionario.equals(other.nomeFuncionario))
			return false;
		if (retirada == null) {
			if (other.retirada != null)
				return false;
		} else if (!retirada.equals(other.retirada))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReservaPendente [tipo=" + tipo + ", telefone=" + telefone + ", email=" + email + ", nomeFilial="
				+ nomeFilial + ", nomeCliente=" + nomeCliente + ", codigoCliente=" + codigoCliente
				+ ", nomeFuncionario=" + nomeFuncionario + ", retirada=" + retirada + ", devolucao=" + devolucao + "]";
	}
	
}
