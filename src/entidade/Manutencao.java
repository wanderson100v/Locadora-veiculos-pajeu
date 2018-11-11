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
	
	
	
}
