package model.enumeracoes;

public enum TipoLocacao {
	KM_LIVRE("Quilômetro livre"),KM_CONTROLE("Quilômetro Controlado");
	
	private String value;

	private TipoLocacao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
