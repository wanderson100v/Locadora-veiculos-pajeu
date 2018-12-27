package adapter;

public class ReservaDisponibilidade {
	private String tipoCategoria;
	private Integer reservado;
	private Integer receber;
	private Integer reservavel;
	private Integer previsto;
	
	public ReservaDisponibilidade(String tipoCategoria, Integer reservado, Integer receber, Integer reservavel) {
		this.tipoCategoria = tipoCategoria;
		this.reservado = reservado;
		this.receber = receber;
		this.reservavel = reservavel;
	}
	
	public ReservaDisponibilidade() {
	}

	public Integer getReservado() {
		return reservado;
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
		return "ReservaDisponibilidade [tipoCategoria=" + tipoCategoria + ", reservado=" + reservado + ", aReceber="
				+ receber + ", reservavel=" + reservavel + ", previsto=" + previsto + "]";
	}

}
