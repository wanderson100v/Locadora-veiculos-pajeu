package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Revisao extends Entidade {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_retirada;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_devolucao;
	
	@Column(name = "valor_pago")

	private Date valorPago;
}
