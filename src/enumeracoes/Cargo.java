package enumeracoes;

public enum Cargo {
	ADM("administrador"),SU("gerente"),AT("atendente");
	private String value;
	
	private Cargo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
