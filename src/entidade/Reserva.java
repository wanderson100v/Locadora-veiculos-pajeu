package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reserva extends Entidade {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_retirada;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_devolucao;
	@Column(name = "valor_pago")
	private Date valorPago;
	@ManyToOne
	private CategoriaVeiculo categoriaVeiculo;
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Filial filial;

	public Date getDate_retirada() {
		return date_retirada;
	}

	public void setDate_retirada(Date date_retirada) {
		this.date_retirada = date_retirada;
	}

	public Date getData_devolucao() {
		return data_devolucao;
	}

	public void setData_devolucao(Date data_devolucao) {
		this.data_devolucao = data_devolucao;
	}

	public Date getValorPago() {
		return valorPago;
	}

	public void setValorPago(Date valorPago) {
		this.valorPago = valorPago;
	}

	public CategoriaVeiculo getCategoriaVeiculo() {
		return categoriaVeiculo;
	}

	public void setCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) {
		this.categoriaVeiculo = categoriaVeiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	
	
	
	
}
