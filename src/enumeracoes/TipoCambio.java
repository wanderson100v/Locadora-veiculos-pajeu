package enumeracoes;

public enum TipoCambio {
	AUTOMATICO("Autom�tico"), MANUAL("Manuel");
	private String value;
	
	private TipoCambio(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
