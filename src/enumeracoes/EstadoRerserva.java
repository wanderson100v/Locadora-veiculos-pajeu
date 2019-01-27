package enumeracoes;

public enum EstadoRerserva {
	CANCELADO("Cancelada"),PENDENTE("Pendente"),EFETIVADA("Efetivada"),EXPIRADA("Expirada");
	
	private String value;

	private EstadoRerserva(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static EstadoRerserva getEstadoReserva(String estadoNome) {
		estadoNome = estadoNome.toLowerCase();
		for(EstadoRerserva e : values())
			if(estadoNome.equals(e.toString().toLowerCase()))
				return e;
		return null;
	}
	
}
