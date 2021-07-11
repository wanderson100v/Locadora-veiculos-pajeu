package model.enumeracoes;

public enum TipoAcionamentoEmbreagem {
	MANUAL("Manual"),HIDRAULICO("Hidráulico");
	
	private String value;
	
	private TipoAcionamentoEmbreagem(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
