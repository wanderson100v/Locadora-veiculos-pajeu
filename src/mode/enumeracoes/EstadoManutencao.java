package mode.enumeracoes;

public enum EstadoManutencao {
	PENDENTE("Pendente"),EM_PROGRESSO("Em Progresso"),FINALIZADO("Finalizado");
	
	private String value;

	private EstadoManutencao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
