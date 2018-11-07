package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoCambio;

@Entity
public class Automovel extends Veiculo {
	
	private boolean mp3,dvd,radio;
	
	@Column(name = "direcao_hidraulica")
	private boolean direcaoHidraulica;
	
	@Column(name = "ar_condicionado")
	private boolean arCondicionado;
	
	@Column(name = "camera_re")
	private boolean cameraRe;
	
	@Column(name = "tipo_cambio", nullable = false)
	private TipoCambio tipoCambio;
	
	@Column(name = "tipo_tamanho" , nullable = false)
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
