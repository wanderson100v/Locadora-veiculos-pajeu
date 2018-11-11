package entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoAirBag;
import enumeracoes.TipoAutomovel;
import enumeracoes.TipoCambio;

@Entity
public class Automovel extends Veiculo {
		
	@Column(name = "tipo_cambio", nullable = false)
	private TipoCambio tipoCambio;
	@Column(name = "tipo", nullable = false)
	private TipoAutomovel tipoAutomovel;
	@Column(name = "tipo_airbag", nullable = false)
	private TipoAirBag tipoAirBag;
	@Column(name = "tipo_tamanho" , nullable = false)
	private TamanhoVeiculo tamanhoVeiculo;
	
	
	public TipoAutomovel getTipoAutomovel() {
		return tipoAutomovel;
	}

	public void setTipoAutomovel(TipoAutomovel tipoAutomovel) {
		this.tipoAutomovel = tipoAutomovel;
	}

	public TipoAirBag getTipoAirBag() {
		return tipoAirBag;
	}

	public void setTipoAirBag(TipoAirBag tipoAirBag) {
		this.tipoAirBag = tipoAirBag;
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
