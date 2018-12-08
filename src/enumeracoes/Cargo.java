package enumeracoes;

public enum Cargo {
	ADM("administrador"),SU("gerente"),AT("atendente");
	private String value;
	
	private Cargo(String value) {
		this.value = value;
	}
	
	public static Cargo getCargo(String cargoString){
		for(Cargo element : values())
			if(element.value.equalsIgnoreCase(cargoString))
				return element;
		return null;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
