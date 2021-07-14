package model.enumeracoes;

public enum TipoCambio {
	MANUAL("Manual"), AUTOMATICO("Automático");
	private String value;
	
	private TipoCambio(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
