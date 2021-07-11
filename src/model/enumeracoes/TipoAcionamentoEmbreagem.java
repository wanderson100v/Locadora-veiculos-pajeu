package model.enumeracoes;

public enum TipoAcionamentoEmbreagem {
	MANUAL("Manual"),HIDRAULICO("Hidr�ulico");
	
	private String value;
	
	private TipoAcionamentoEmbreagem(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
