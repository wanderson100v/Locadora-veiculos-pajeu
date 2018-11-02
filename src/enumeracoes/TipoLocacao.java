package enumeracoes;

public enum TipoLocacao {
	KM_LIVRE("Kilometro livre"),KM_CONTROLE("Kilometro Controlado");
	
	private String value;

	private TipoLocacao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
