package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enumeracoes.TipoManutencao;

@Entity
public class Manutencao extends Entidade{
	
	private Boolean estado;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_inicio")
	private Date dataHoraInicio;
	@Column(name = "tipo_manutencao")
	private TipoManutencao tipoManuntencao;
	private boolean finalizado;
	
	

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
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
