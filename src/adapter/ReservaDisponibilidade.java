package adapter;

public class ReservaDisponibilidade {
	private String tipoCategoria,descricaoCategoria;
	private Integer reservado, receber, emEstoque, disponivel;
	private Float valorDiariaCategoria;
	private Long idCategoria;

	public ReservaDisponibilidade(Long idCategoria,String tipoCategoria, String descricaoCategoria, Integer reservado, Integer receber,
			Integer emEstoque, Float valorDiariaCategoria) {
		super();
		this.idCategoria = idCategoria;
		this.tipoCategoria = tipoCategoria;
		this.descricaoCategoria = descricaoCategoria;
		this.reservado = reservado;
		this.receber = receber;
		this.emEstoque = emEstoque;
		this.valorDiariaCategoria = valorDiariaCategoria;
	}
	
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(String tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

	public Integer getReservado() {
		return reservado;
	}

	public void setReservado(Integer reservado) {
		this.reservado = reservado;
	}

	public Integer getReceber() {
		return receber;
	}

	public void setReceber(Integer receber) {
		this.receber = receber;
	}

	public Integer getEmEstoque() {
		return emEstoque;
	}

	public void setEmEstoque(Integer emEstoque) {
		this.emEstoque = emEstoque;
	}

	public Integer getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Integer disponivel) {
		this.disponivel = disponivel;
	}

	public Float getValorDiariaCategoria() {
		return valorDiariaCategoria;
	}

	public void setValorDiariaCategoria(Float valorDiariaCategoria) {
		this.valorDiariaCategoria = valorDiariaCategoria;
	}

	@Override
	public String toString() {
		return "ReservaDisponibilidade [tipoCategoria=" + tipoCategoria + ", descricaoCategoria=" + descricaoCategoria
				+ ", reservado=" + reservado + ", aReceber=" + receber + ", emEstoque=" + emEstoque + ", disponivel="
				+ disponivel + ", valorDiariaCategoria=" + valorDiariaCategoria + "]";
	}

}
