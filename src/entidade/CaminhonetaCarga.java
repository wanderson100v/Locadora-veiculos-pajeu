package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.TipoAcionamentoEmbreagem;

@Entity(name = "caminhoneta_carga")
public class CaminhonetaCarga extends Veiculo {
	
	@Column(nullable = false)
	private Float desenpenho;
	@Column(name = "potencia_motor", nullable = false)
	private Float potencia;
	@Column(name = "distancia_eixos",  nullable = false)
	private Float distanciaEixos;
	@Column(name = "tipo_acionamento_e", nullable = false)
	private TipoAcionamentoEmbreagem tipoAcionamentoEmbreagem;
	@Column(name = "capacidade_carga", nullable = false)
	private Integer capacidadeCarga;
	@Column(name = "capacidade_combustivel", nullable = false)
	private Integer capacidadeCombustivel;
	

	public Float getDesenpenho() {
		return desenpenho;
	}

	public void setDesenpenho(Float desenpenho) {
		this.desenpenho = desenpenho;
	}

	public Float getPotencia() {
		return potencia;
	}

	public Integer getCapacidadeCombustivel() {
		return capacidadeCombustivel;
	}

	public void setCapacidadeCombustivel(Integer capacidadeCombustivel) {
		this.capacidadeCombustivel = capacidadeCombustivel;
	}

	public void setPotencia(Float potencia) {
		this.potencia = potencia;
	}

	public Float getDistanciaEixos() {
		return distanciaEixos;
	}

	public void setDistanciaEixos(Float distanciaEixos) {
		this.distanciaEixos = distanciaEixos;
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
