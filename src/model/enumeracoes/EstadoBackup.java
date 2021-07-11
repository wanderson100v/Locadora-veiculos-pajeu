package model.enumeracoes;

public enum EstadoBackup {
	
	PENDENTE("Pendente"),ADIADO("Adiado"),NAO_RELIZADO("N�o Realizado"),REALIZADO("Realizado");
	
	private String value;

	private EstadoBackup(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
