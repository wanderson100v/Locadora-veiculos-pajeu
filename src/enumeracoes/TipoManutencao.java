package enumeracoes;

public enum TipoManutencao {
	LIMPEZA("Limpeza"),MANUTENCAO("Manutenção");
	
	private String value;

	private TipoManutencao(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
