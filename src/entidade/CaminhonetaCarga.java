package entidade;


import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.TipoAcionamentoEmbreagem;

@Entity(name = "caminhoneta_carga")
//@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "caminhoneta_carga_seq")
public class CaminhonetaCarga extends Veiculo {
	
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private Float desenpenho;
	@Column(name = "potencia_motor", nullable = false)
	private Float potencia;
	@Column(name = "distancia_eixos",  nullable = false)
	private Float distanciaEixos;
	@Column(name = "tipo_acionamento_e")
	private TipoAcionamentoEmbreagem tipoAcionamentoEmbreagem;
	@Column(name = "capacidade_carga", nullable = false)
	private Integer capacidadeCarga;
	@Column(name = "capacidade_combustivel", nullable = false)
	private Integer capacidadeCombustivel;
	
	public CaminhonetaCarga() {}

	/**
	 * Construtor para Caminhoneta de carga de exemplo
	 * @param torqueMotor
	 * @param desenpenho
	 * @param potencia
	 * @param distanciaEixos
	 * @param tipoAcionamentoEmbreagem
	 * @param capacidadeCarga
	 * @param capacidadeCombustivel
	 */
	public CaminhonetaCarga(Float torqueMotor, Float desenpenho, Float potencia, Float distanciaEixos,
			TipoAcionamentoEmbreagem tipoAcionamentoEmbreagem, Integer capacidadeCarga, Integer capacidadeCombustivel) {
		super(torqueMotor);
		this.desenpenho = desenpenho;
		this.potencia = potencia;
		this.distanciaEixos = distanciaEixos;
		this.tipoAcionamentoEmbreagem = tipoAcionamentoEmbreagem;
		this.capacidadeCarga = capacidadeCarga;
		this.capacidadeCombustivel = capacidadeCombustivel;
	}



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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacidadeCarga == null) ? 0 : capacidadeCarga.hashCode());
		result = prime * result + ((capacidadeCombustivel == null) ? 0 : capacidadeCombustivel.hashCode());
		result = prime * result + ((desenpenho == null) ? 0 : desenpenho.hashCode());
		result = prime * result + ((distanciaEixos == null) ? 0 : distanciaEixos.hashCode());
		result = prime * result + ((potencia == null) ? 0 : potencia.hashCode());
		result = prime * result + ((tipoAcionamentoEmbreagem == null) ? 0 : tipoAcionamentoEmbreagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CaminhonetaCarga))
			return false;
		CaminhonetaCarga other = (CaminhonetaCarga) obj;
		if (capacidadeCarga == null) {
			if (other.capacidadeCarga != null)
				return false;
		} else if (!capacidadeCarga.equals(other.capacidadeCarga))
			return false;
		if (capacidadeCombustivel == null) {
			if (other.capacidadeCombustivel != null)
				return false;
		} else if (!capacidadeCombustivel.equals(other.capacidadeCombustivel))
			return false;
		if (desenpenho == null) {
			if (other.desenpenho != null)
				return false;
		} else if (!desenpenho.equals(other.desenpenho))
			return false;
		if (distanciaEixos == null) {
			if (other.distanciaEixos != null)
				return false;
		} else if (!distanciaEixos.equals(other.distanciaEixos))
			return false;
		if (potencia == null) {
			if (other.potencia != null)
				return false;
		} else if (!potencia.equals(other.potencia))
			return false;
		if (tipoAcionamentoEmbreagem != other.tipoAcionamentoEmbreagem)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CaminhonetaCarga [desenpenho=" + desenpenho + ", potencia=" + potencia + ", distanciaEixos="
				+ distanciaEixos + ", tipoAcionamentoEmbreagem=" + tipoAcionamentoEmbreagem + ", capacidadeCarga="
				+ capacidadeCarga + ", capacidadeCombustivel=" + capacidadeCombustivel + "]";
	}
		
}
