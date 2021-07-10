package model.enumeracoes;

public enum TamanhoVeiculo {
	PEQUENO("Pequeno"),MEDIO("M�dio"),GRANDE("Grande");
	
	private String value;
	
	private TamanhoVeiculo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
