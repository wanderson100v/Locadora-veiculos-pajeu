package model.banco;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Immutable;

import mode.enumeracoes.EstadoRerserva;
import model.entidade.Entidade;

@Entity(name = "reserva_hoje")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "reserva_hoje_seq")
@Immutable
@NamedQuery(name = "reservaHoje.buscarTudo" , query = "select r from banco.ReservaHoje as r")
public class ReservaHoje extends Entidade{
	
	private static final long serialVersionUID = 1L;
	private Integer hora;
	private String tipo;
	@Column(name = "estado_reserva")
	private EstadoRerserva estadoReserva;
	@Column(name = "nome_cliente")
	private String nomeCliente;
	@Column(name = "nome_filial")
	private String nomeFilial;
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public EstadoRerserva getEstadoReserva() {
		return estadoReserva;
	}
	public void setEstadoReserva(EstadoRerserva estadoReserva) {
		this.estadoReserva = estadoReserva;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeFilial() {
		return nomeFilial;
	}
	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}
	
}
