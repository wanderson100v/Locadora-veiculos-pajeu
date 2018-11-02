package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.TipoManutencao;

@Entity
public class Manutencao extends Entidade{
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_inicio")
	private Date dataHoraInicio;
	@Column(name = "tipo_manutencao")
	private TipoManutencao tipoManuntencao;
	private boolean finalizado;
	@ManyToOne
	private Veiculo veiculo;
	
	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
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
