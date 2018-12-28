package adapter;

public class ReservaDisponibilidade {
	private String tipoCategoria,descricaoCategoria;
	private Integer reservado, receber, reservavel, previsto;
	private Float valorDiariaCategoria;
	
	public ReservaDisponibilidade(String tipoCategoria, String descricaoCategoria, Integer reservado, Integer receber,
			Integer reservavel, Float valorDiariaCategoria) {
		this.tipoCategoria = tipoCategoria;
		this.descricaoCategoria = descricaoCategoria;
		this.reservado = reservado;
		this.receber = receber;
		this.reservavel = reservavel;
		this.valorDiariaCategoria = valorDiariaCategoria;
	}

	public ReservaDisponibilidade() {}

	public Integer getReservado() {
		return reservado;
	}
	
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
	public Float getValorDiariaCategoria() {
		return valorDiariaCategoria;
	}

	public void setValorDiariaCategoria(Float valorDiariaCategoria) {
		this.valorDiariaCategoria = valorDiariaCategoria;
	}

	public void setReservado(Integer reservado) {
		this.reservado = reservado;
	}

	public String getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(String tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}


	public Integer getReceber() {
		return receber;
	}

	public void setReceber(Integer receber) {
		this.receber = receber;
	}

	public Integer getReservavel() {
		return reservavel;
	}

	public void setReservavel(Integer reservavel) {
		this.reservavel = reservavel;
	}

	public Integer getPrevisto() {
		return previsto;
	}

	public void setPrevisto(Integer previsto) {
		this.previsto = previsto;
	}

	@Override
	public String toString() {
		return "ReservaDisponibilidade [tipoCategoria=" + tipoCategoria + ", descricaoCategoria=" + descricaoCategoria
				+ ", reservado=" + reservado + ", receber=" + receber + ", reservavel=" + reservavel + ", previsto="
				+ previsto + ", valorDiariaCategoria=" + valorDiariaCategoria + "]";
	}

}
