package model.enumeracoes;

public enum Tabela {
	ACESSORIO("Acessório","acessorio"),AUTOMOVEL("Automovel","automovel"),
	CAMINHONETA_CARGA("Caminhoneta de Carga","caminhoneta_carga"),
	CATEGORIA_VEICULO("Categoria de Véiculo","categoria_veiculo"),
	CLIENTE("Cliente","cliente"),ENDERECO("Endereço","endereco"),
	FILIAL("Filial","filial"),FISICO("Físico","fisico"),
	FUNCIONARIO("Funcionário","funcionario"),
	JURIDICO("Jurídico","juridico"),LOCACAO("Locação","locacao"),
	MANUTENCAO("Manutenção","manutencao"),
	RESERVA("Reserva","reserva"),VEICULO("Veículo","veiculo");
	
	private String frontEnd,backEnd;

	private Tabela(String frontEnd, String backEnd) {
		this.frontEnd = frontEnd;
		this.backEnd = backEnd;
	}
	
	public String getBackEnd() {
		return backEnd;
	}
	
	@Override
	public String toString() {
		return frontEnd;
	}
	
}
