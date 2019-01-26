package entidade;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import enumeracoes.EstadoBackup;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "bakcup_seq", allocationSize = 1)
public class Backup extends Entidade{
	
	private static final long serialVersionUID = -6110244291911613584L;
	@Column(name = "data_ocorrencia", nullable = false)
	private LocalDateTime dataOcorrencia;
	@Column(length = 20, nullable = false)
	private String autor;
	@Column(nullable = false)
	private EstadoBackup estado;
	@Column(length = 255, nullable = false)
	private String descricao;
	
	public LocalDateTime getDataOcorrencia() {
		return dataOcorrencia;
	}
	
	public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public EstadoBackup getEstado() {
		return estado;
	}

	public void setEstado(EstadoBackup estado) {
		this.estado = estado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((dataOcorrencia == null) ? 0 : dataOcorrencia.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		Backup other = (Backup) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (dataOcorrencia == null) {
			if (other.dataOcorrencia != null)
				return false;
		} else if (!dataOcorrencia.equals(other.dataOcorrencia))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (estado != other.estado)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Backup [dataOcorrencia=" + dataOcorrencia + ", autor=" + autor + ", estado=" + estado + ", descricao="
				+ descricao + "]";
	}
	
}
