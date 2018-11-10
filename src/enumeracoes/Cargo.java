package enumeracoes;

public enum Cargo {
	ADM("ADMINITRADOR"),SU("SUPER USUÁRIO"),AT("ATENDENTE");
	private String value;
	
	private Cargo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
