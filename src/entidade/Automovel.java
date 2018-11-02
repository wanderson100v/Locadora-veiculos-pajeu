package entidade;

import javax.persistence.Column;

import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoCambio;

public class Automovel extends Veiculo {
	
	private Boolean mp3,dvd,radio;
	
	@Column(name = "direcao_hidraulica")
	private Boolean direcaoHidraulica;
	
	@Column(name = "ar_condicionado")
	private Boolean arCondicionado;
	
	@Column(name = "camera-re")
	private Boolean cameraRe;
	
	@Column(name = "tipo_cambio")
	private TipoCambio tipoCambio;
	
	@Column(name = "tipo_tamanho")
	private TamanhoVeiculo tamanhoVeiculo;

	public Boolean getMp3() {
		return mp3;
	}

	public void setMp3(Boolean mp3) {
		this.mp3 = mp3;
	}

	public Boolean getDvd() {
		return dvd;
	}

	public void setDvd(Boolean dvd) {
		this.dvd = dvd;
	}

	public Boolean getRadio() {
		return radio;
	}

	public void setRadio(Boolean radio) {
		this.radio = radio;
	}

	public Boolean getDirecaoHidraulica() {
		return direcaoHidraulica;
	}

	public void setDirecaoHidraulica(Boolean direcaoHidraulica) {
		this.direcaoHidraulica = direcaoHidraulica;
	}

	public Boolean getArCondicionado() {
		return arCondicionado;
	}

	public void setArCondicionado(Boolean arCondicionado) {
		this.arCondicionado = arCondicionado;
	}

	public Boolean getCameraRe() {
		return cameraRe;
	}

	public void setCameraRe(Boolean cameraRe) {
		this.cameraRe = cameraRe;
	}

	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public TamanhoVeiculo getTamanhoVeiculo() {
		return tamanhoVeiculo;
	}

	public void setTamanhoVeiculo(TamanhoVeiculo tamanhoVeiculo) {
		this.tamanhoVeiculo = tamanhoVeiculo;
	}
	
	
}
