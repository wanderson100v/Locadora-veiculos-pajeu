package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reserva extends Entidade {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_retirada")
	private Date dateRetirada;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_devolucao")
	private Date dataDevolucao;
	@Column(name = "valor")
	private Date valor;
	@ManyToOne
	@JoinColumn(nullable = false)
	private CategoriaVeiculo categoriaVeiculo;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	@ManyToOne
	private Filial filial;

	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDateRetirada() {
		return dateRetirada;
	}

	public void setDateRetirada(Date dateRetirada) {
		this.dateRetirada = dateRetirada;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getValor() {
		return valor;
	}

	public void setValor(Date valor) {
		this.valor = valor;
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
