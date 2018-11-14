package entidade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "automovel_acessorio")
public class AutomovelAcessorio extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Automovel automovel;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Acessorio acessorio;
	
	public Automovel getAutomovel() {
		return automovel;
	}
	
	public void setAutomovel(Automovel automovel) {
		this.automovel = automovel;
	}
	
	public Acessorio getAcessorio() {
		return acessorio;
	}
	
	public void setAcessorio(Acessorio acessorio) {
		this.acessorio = acessorio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acessorio == null) ? 0 : acessorio.hashCode());
		result = prime * result + ((automovel == null) ? 0 : automovel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AutomovelAcessorio))
			return false;
		AutomovelAcessorio other = (AutomovelAcessorio) obj;
		if (acessorio == null) {
			if (other.acessorio != null)
				return false;
		} else if (!acessorio.equals(other.acessorio))
			return false;
		if (automovel == null) {
			if (other.automovel != null)
				return false;
		} else if (!automovel.equals(other.automovel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AutomovelAcessorio [automovel=" + automovel + ", acessorio=" + acessorio + "]";
	}
	
	
	
}
