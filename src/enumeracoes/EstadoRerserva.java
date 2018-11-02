package enumeracoes;

public enum EstadoRerserva {
	CANCELADO("Cancelada"),PENDENTE("Pendente"),CONCLUIDO("Conclu�do");
	
	private String value;

	private EstadoRerserva(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
