package enumeracoes;

public enum Sexo {
	MASCULINO("Masculino"), FEMININO("Feminino"), OUTRO("Outro");
	private String value;
	
	private Sexo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
