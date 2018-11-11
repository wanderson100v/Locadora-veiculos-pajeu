package entidade;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "automovel_acessorio")
public class AutovelAcessorio extends Entidade {
	@ManyToOne
	private Automovel automovel;
	@ManyToOne
	private Acessorio acessorio;
}
