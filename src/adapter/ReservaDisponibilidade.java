package adapter;

public class ReservaDisponibilidade {
	private Long idCategoria;
	private String tipoCategoria,descricaoCategoria;
	private Float valorDiariaCategoria;
	private Integer previsaoLocacaoAcumulada, previsaoManutencaoAcumulada, 
			previsaoReservaAcumulada,totalLocado,totalManter,totalReserva,
			totalVeiculo,disponivel;
	
	public ReservaDisponibilidade(Long idCategoria, String tipoCategoria, String descricaoCategoria,
			Float valorDiariaCategoria, Integer previsaoLocacaoAcumulada, Integer previsaoManutencaoAcumulada,
			Integer previsaoReservaAcumulada, Integer totalLocado, Integer totalManter, Integer totalReserva,
			Integer totalVeiculo) {
		this.idCategoria = idCategoria;
		this.tipoCategoria = tipoCategoria;
		this.descricaoCategoria = descricaoCategoria;
		this.valorDiariaCategoria = valorDiariaCategoria;
		this.previsaoLocacaoAcumulada = previsaoLocacaoAcumulada;
		this.previsaoManutencaoAcumulada = previsaoManutencaoAcumulada;
		this.previsaoReservaAcumulada = previsaoReservaAcumulada;
		this.totalLocado = totalLocado;
		this.totalManter = totalManter;
		this.totalReserva = totalReserva;
		this.totalVeiculo = totalVeiculo;
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

	public Float getValorDiariaCategoria() {
		return valorDiariaCategoria;
	}

	public void setValorDiariaCategoria(Float valorDiariaCategoria) {
		this.valorDiariaCategoria = valorDiariaCategoria;
	}

	public Integer getPrevisaoLocacaoAcumulada() {
		return previsaoLocacaoAcumulada;
	}

	public void setPrevisaoLocacaoAcumulada(Integer previsaoLocacaoAcumulada) {
		this.previsaoLocacaoAcumulada = previsaoLocacaoAcumulada;
	}

	public Integer getPrevisaoManutencaoAcumulada() {
		return previsaoManutencaoAcumulada;
	}

	public void setPrevisaoManutencaoAcumulada(Integer previsaoManutencaoAcumulada) {
		this.previsaoManutencaoAcumulada = previsaoManutencaoAcumulada;
	}

	public Integer getPrevisaoReservaAcumulada() {
		return previsaoReservaAcumulada;
	}

	public void setPrevisaoReservaAcumulada(Integer previsaoReservaAcumulada) {
		this.previsaoReservaAcumulada = previsaoReservaAcumulada;
	}

	public Integer getTotalLocado() {
		return totalLocado;
	}

	public void setTotalLocado(Integer totalLocado) {
		this.totalLocado = totalLocado;
	}

	public Integer getTotalManter() {
		return totalManter;
	}

	public void setTotalManter(Integer totalManter) {
		this.totalManter = totalManter;
	}

	public Integer getTotalReserva() {
		return totalReserva;
	}

	public void setTotalReserva(Integer totalReserva) {
		this.totalReserva = totalReserva;
	}

	public Integer getTotalVeiculo() {
		return totalVeiculo;
	}

	public void setTotalVeiculo(Integer totalVeiculo) {
		this.totalVeiculo = totalVeiculo;
	}

	public Integer getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Integer disponivel) {
		this.disponivel = disponivel;
	}
	
}
