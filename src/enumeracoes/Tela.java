package enumeracoes;

public enum Tela {
	LOGIN("LoginPane"),HOME("HomePane"),CARREGAR("CarregarPane");
	
	private String value;
	
	private Tela(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
