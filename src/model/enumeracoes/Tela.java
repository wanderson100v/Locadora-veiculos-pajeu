package model.enumeracoes;

public enum Tela {
	LOGIN("LoginPane"),HOME("HomePane"),CONFIG("ConfiguracaoPane");
	
	private String value;
	
	private Tela(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
