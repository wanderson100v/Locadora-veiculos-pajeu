package enumeracoes;

public enum TipoCombustivel {
	GASOLINA("Gasolina"),ETANOL("Etanol"),DIESEL("Diesel"),
	GASOLINA_ETANOL("Gasolina e Etanol"), GASOLINA_DIESEL("Gasolina e Diesel"),
	ETANOL_DIESEL("Etanol e Disel");
	
	private String value;
	
	private TipoCombustivel(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
