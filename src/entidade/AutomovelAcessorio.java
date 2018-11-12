package entidade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "automovel_acessorio")
public class AutomovelAcessorio extends Entidade {
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
	
	
}
