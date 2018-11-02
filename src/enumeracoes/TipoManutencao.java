package enumeracoes;

public enum TipoManutencao {
	LIMPEZA("Limpeza"),MANUTENCAO("Manuten��o");
	
	private String value;

	private TipoManutencao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
