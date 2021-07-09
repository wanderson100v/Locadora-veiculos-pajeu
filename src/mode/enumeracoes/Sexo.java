package mode.enumeracoes;

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
	
	public static Sexo getSexo(String sexoStr) {
		for(Sexo sexo : values())
			if(sexoStr.toLowerCase().equals(sexo.toString().toLowerCase()))
				return sexo;
		return null;
	}
}
