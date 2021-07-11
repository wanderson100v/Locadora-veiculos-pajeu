package model.enumeracoes;

public enum TipoLocacao {
	KM_LIVRE("Quil�metro"),KM_CONTROLE("Quil�metro Controlado");
	
	private String value;

	private TipoLocacao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
