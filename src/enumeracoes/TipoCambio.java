package enumeracoes;

public enum TipoCambio {
	 MANUAL("Manual"), AUTOMATICO("Autom�tico");
	private String value;
	
	private TipoCambio(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
