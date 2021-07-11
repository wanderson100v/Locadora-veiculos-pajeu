package model.enumeracoes;

public enum TipoManutencao {
	LIMPEZA("Limpeza"),REVISAO("Revis�o"),ABASTECIMENTO("Abastecimento");
	
	private String value;

	private TipoManutencao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
