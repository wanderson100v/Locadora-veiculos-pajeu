package model.enumeracoes;

public enum Tabela {
	ACESSORIO("Acess�rio","acessorio"),AUTOMOVEL("Automovel","automovel"),
	CAMINHONETA_CARGA("Caminhoneta de Carga","caminhoneta_carga"),
	CATEGORIA_VEICULO("Categoria de ve�culo","categoria_veiculo"),
	CLIENTE("Cliente","cliente"),ENDERECO("Endere�o","endereco"),
	FILIAL("Filial","filial"),FISICO("F�sico","fisico"),
	FUNCIONARIO("Funcion�rio","funcionario"),
	JURIDICO("Jur�dico","juridico"),LOCACAO("Loca��o","locacao"),
	MANUTENCAO("Manuten��o","manutencao"),
	RESERVA("Reserva","reserva"),VEICULO("Ve�culo","veiculo");
	
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
