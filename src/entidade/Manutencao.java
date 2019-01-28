package entidade;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import enumeracoes.EstadoManutencao;
import enumeracoes.TipoManutencao;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "manutencao_seq")
public class Manutencao extends Entidade{
	
	private static final long serialVersionUID = 1L;
	@Column(name = "data_hora_inicio")
	private LocalDateTime dataHoraInicio;
	@Column(name = "tipo",nullable = false)
	private TipoManutencao tipoManuntencao;
	@Column(name = "estado", nullable = false)
	private EstadoManutencao estadoManutencao;
	private Float custo;
	@Column(name = "custo_horas")
	private int custoHoras;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Veiculo veiculo;
	
	public Manutencao(LocalDateTime dataHoraInicio, TipoManutencao tipoManuntencao,
			EstadoManutencao estadoManutencao, Float custo, int custoHoras, Veiculo veiculo) {
		this.dataHoraInicio = dataHoraInicio;
		this.tipoManuntencao = tipoManuntencao;
		this.estadoManutencao = estadoManutencao;
		this.custo = custo;
		this.custoHoras = custoHoras;
		this.veiculo = veiculo;
	}

	public Manutencao() {}

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

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
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
		result = prime * result + custoHoras;
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
		if (getClass() != obj.getClass())
			return false;
		Manutencao other = (Manutencao) obj;
		if (custo == null) {
			if (other.custo != null)
				return false;
		} else if (!custo.equals(other.custo))
			return false;
		if (custoHoras != other.custoHoras)
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
		return "Tipo " + tipoManuntencao+" Data de Inicio " + dataHoraInicio 
				+ ", Estado " + estadoManutencao + ", Custo em Valor " + custo + ", Custo em Horas=" + custoHoras
				+ ", veiculo: " + veiculo;
	}
}
