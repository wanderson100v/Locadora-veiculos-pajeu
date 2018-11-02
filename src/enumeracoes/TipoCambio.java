package enumeracoes;

public enum TipoCambio {
	AUTOMATICO("Automático"), MANUAL("Manuel");
	private String value;
	
	private TipoCambio(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
