package mode.enumeracoes;

public enum TipoAutomovel {
	CONVENCIONAL("Concencional"),CAMINHONETA_PASSAGEIRO("Caminhoneta Passageiro");
	private String value;
	
	private TipoAutomovel(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
