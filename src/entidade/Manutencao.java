package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.EstadoManutencao;
import enumeracoes.TipoManutencao;

@Entity
public class Manutencao extends Entidade{
	
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_inicio")
	private Date dataHoraInicio;
	@Column(name = "tipo")
	private TipoManutencao tipoManuntencao;
	@Column(name = "estado")
	private EstadoManutencao estadoManutencao;
	private Float custo;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Veiculo veiculo;
	

	public EstadoManutencao getEstadoManutencao() {
		return estadoManutencao;
	}

	public void setEstadoManutencao(EstadoManutencao estadoManutencao) {
		this.estadoManutencao = estadoManutencao;
	}

	public Float getCusto() {
		return custo;
	}

	public void setCusto(Float custo) {
		this.custo = custo;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public TipoManutencao getTipoManuntencao() {
		return tipoManuntencao;
	}

	public void setTipoManuntencao(TipoManutencao tipoManuntencao) {
		this.tipoManuntencao = tipoManuntencao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((custo == null) ? 0 : custo.hashCode());
		result = prime * result + ((dataHoraInicio == null) ? 0 : dataHoraInicio.hashCode());
		result = prime * result + ((estadoManutencao == null) ? 0 : estadoManutencao.hashCode());
		result = prime * result + ((tipoManuntencao == null) ? 0 : tipoManuntencao.hashCode());
		result = prime * result + ((veiculo == null) ? 0 : veiculo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Manutencao))
			return false;
		Manutencao other = (Manutencao) obj;
		if (custo == null) {
			if (other.custo != null)
				return false;
		} else if (!custo.equals(other.custo))
			return false;
		if (dataHoraInicio == null) {
			if (other.dataHoraInicio != null)
				return false;
		} else if (!dataHoraInicio.equals(other.dataHoraInicio))
			return false;
		if (estadoManutencao != other.estadoManutencao)
			return false;
		if (tipoManuntencao != other.tipoManuntencao)
			return false;
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Manutencao [dataHoraInicio=" + dataHoraInicio + ", tipoManuntencao=" + tipoManuntencao
				+ ", estadoManutencao=" + estadoManutencao + ", custo=" + custo + ", veiculo=" + veiculo + "]";
	}
	
}
