package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

import enumeracoes.TipoAirBag;

@Entity
public class CaminhonetaPassageiro extends Veiculo {
	
	@Column(name = "tipo_air_bag")
	private TipoAirBag tipoAirBag;
	
	@Column(name = "cintos_str")
	private Boolean cintosSTR;
	
	@Column(name = "diracao_assistida")
	private Boolean direcaoAssistida;
	
	@Column(name = "controle_pa")
	private Boolean controlePA;
	
	@Column(name = "rodas_ll")
	private Boolean rodasLL;

	
	public TipoAirBag getTipoAirBag() {
		return tipoAirBag;
	}

	public void setTipoAirBag(TipoAirBag tipoAirBag) {
		this.tipoAirBag = tipoAirBag;
	}

	public Boolean getCintosSTR() {
		return cintosSTR;
	}

	public void setCintosSTR(Boolean cintosSTR) {
		this.cintosSTR = cintosSTR;
	}

	public Boolean getDirecaoAssistida() {
		return direcaoAssistida;
	}

	public void setDirecaoAssistida(Boolean direcaoAssistida) {
		this.direcaoAssistida = direcaoAssistida;
	}

	public Boolean getControlePA() {
		return controlePA;
	}

	public void setControlePA(Boolean controlePA) {
		this.controlePA = controlePA;
	}

	public Boolean getRodasLL() {
		return rodasLL;
	}

	public void setRodasLL(Boolean rodasLL) {
		this.rodasLL = rodasLL;
	}

	
}
