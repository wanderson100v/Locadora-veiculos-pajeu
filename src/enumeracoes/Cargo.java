package enumeracoes;

public enum Cargo {
	ADM("ADMINITRADOR"),SU("SUPER USU�RIO"),AT("ATENDENTE");
	private String value;
	
	private Cargo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
