package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.TipoAcionamentoEmbreagem;

@Entity
public class CaminhonetaCarga extends Veiculo {
	
	private Float desenpenho,potencia;
	
	@Column(name = "distancia_eichos")
	private Float distanciaEichos;
	
	@Column(name = "tipo_acionamento_embreagem")
	private TipoAcionamentoEmbreagem tipoAcionamentoEmbreagem;
	
	@Column(name = "capacidade_carga")
	private Integer capacidadeCarga;

	public Float getDesenpenho() {
		return desenpenho;
	}

	public void setDesenpenho(Float desenpenho) {
		this.desenpenho = desenpenho;
	}

	public Float getPotencia() {
		return potencia;
	}

	public void setPotencia(Float potencia) {
		this.potencia = potencia;
	}

	public Float getDistanciaEichos() {
		return distanciaEichos;
	}

	public void setDistanciaEichos(Float distanciaEichos) {
		this.distanciaEichos = distanciaEichos;
	}

	public TipoAcionamentoEmbreagem getTipoAcionamentoEmbreagem() {
		return tipoAcionamentoEmbreagem;
	}

	public void setTipoAcionamentoEmbreagem(TipoAcionamentoEmbreagem tipoAcionamentoEmbreagem) {
		this.tipoAcionamentoEmbreagem = tipoAcionamentoEmbreagem;
	}

	public Integer getCapacidadeCarga() {
		return capacidadeCarga;
	}

	public void setCapacidadeCarga(Integer capacidadeCarga) {
		this.capacidadeCarga = capacidadeCarga;
	}
	
	
		
}
