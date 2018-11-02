package enumeracoes;

public enum TipoCombustivel {
	GASOLINA("Gasolina"),ETANOL("Etanol"),DISEL("Disel"),
	GASOLINA_ETANOL("Gasolina e Etanol"), GASOLINA_DISEL("Gasolina e Disel"),
	ETANOL_DISEL("Etanol e Disel");
	
	private String value;
	
	private TipoCombustivel(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
