package enumeracoes;

public enum TipoAirBag {
	SIMPLES_DIANTEIRA("Simples Dianteira"),DUPLO_DIANTEIRA("Duplo Dianteira"),TOTAL("Total");
	private String value;
	
	private TipoAirBag(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
