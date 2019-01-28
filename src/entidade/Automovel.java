package entidade;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoAirBag;
import enumeracoes.TipoAutomovel;
import enumeracoes.TipoCambio;

@Entity
public class Automovel extends Veiculo {

	private static final long serialVersionUID = 1L;
	@Column(name = "tipo_cambio", nullable = false)
	private TipoCambio tipoCambio;
	@Column(name = "tipo", nullable = false)
	private TipoAutomovel tipoAutomovel;
	@Column(name = "tipo_airbag")
	private TipoAirBag tipoAirBag;
	@Column(name = "tipo_tamanho" , nullable = false)
	private TamanhoVeiculo tamanhoVeiculo;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Acessorio> acessorios; 
	
	public Automovel() {}
	
	/**
	 * Construtor para Automovel de exemplo para automoveis comums
	 * @param tipoCambio
	 * @param tipoAutomovel
	 * @param tamanhoVeiculo
	 */
	public Automovel(TipoCambio tipoCambio, TipoAutomovel tipoAutomovel, TamanhoVeiculo tamanhoVeiculo) {
		super();
		this.tipoCambio = tipoCambio;
		this.tipoAutomovel = tipoAutomovel;
		this.tamanhoVeiculo = tamanhoVeiculo;
	}

	/**
	 * Construtor para Automovel de exemplo para Caminhonetas de passageiros
	 * @param tipoCambio
	 * @param tipoAutomovel
	 * @param tipoAirBag
	 * @param tamanhoVeiculo
	 */
	public Automovel(TipoCambio tipoCambio, TipoAutomovel tipoAutomovel, TipoAirBag tipoAirBag,
			TamanhoVeiculo tamanhoVeiculo) {
		super();
		this.tipoCambio = tipoCambio;
		this.tipoAutomovel = tipoAutomovel;
		this.tipoAirBag = tipoAirBag;
		this.tamanhoVeiculo = tamanhoVeiculo;
	}


	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tamanhoVeiculo == null) ? 0 : tamanhoVeiculo.hashCode());
		result = prime * result + ((tipoAirBag == null) ? 0 : tipoAirBag.hashCode());
		result = prime * result + ((tipoAutomovel == null) ? 0 : tipoAutomovel.hashCode());
		result = prime * result + ((tipoCambio == null) ? 0 : tipoCambio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Automovel))
			return false;
		Automovel other = (Automovel) obj;
		if (tamanhoVeiculo != other.tamanhoVeiculo)
			return false;
		if (tipoAirBag != other.tipoAirBag)
			return false;
		if (tipoAutomovel != other.tipoAutomovel)
			return false;
		if (tipoCambio != other.tipoCambio)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Automovel: Tipo "+tipoAutomovel+", "+super.toString();
	}	
	
}
