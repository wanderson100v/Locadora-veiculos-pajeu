package mode.enumeracoes;

public enum TipoCombustivel {
	ETANOL("Etanol"),DIESEL("Diesel"),GASOLINA("Gasolina"),ETANOL_DIESEL("Etanol e Disel"),
	GASOLINA_ETANOL("Gasolina e Etanol"),GASOLINA_DIESEL("Gasolina e Diesel");
	
	private String value;
	
	private TipoCombustivel(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
