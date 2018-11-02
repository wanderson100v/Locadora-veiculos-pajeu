package enumeracoes;

public enum TipoAcionamentoEmbreagem {
	MANUAL("Manual"),HIDRAULICO("HidrŠulico");
	
	private String value;
	
	private TipoAcionamentoEmbreagem(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
