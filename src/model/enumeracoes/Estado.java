package model.enumeracoes;

public enum Estado {

	ACRE("Acre","AC"), ALAGOAS("Alagoas","AL"), AMAPA("Amap�","AP"), 
	AMAZONAS("Amazonas","AM"), BAHIA("Bahia","BA"), CEARA("Cear�","CE"),
	DISTRITO_FERERAL("Distrito Federal","DF"), ESPIRITO_SANTO("Esp�rito Santo","ES"), GOIAS("Goi�s","GO"),
	MARANAO("Maranh�o","MA"), MATO_GROSSO("Mato_Grosso","MT"), MATO_GROSSO_DO_SUL("Mato Grosso do Sul","MS"),
	MINAS_GERAIS("Minas Gerais","MG"), PARA("Par�","PA"), PARAIBA("Para�ba","PB"),
	PARANA("Paran�","PR"), PERNAMBUCO("Pernambuco","PE"), PIAUL("Piau�","PI"),
	RORAIMA("Roraima","RR"), RONDONIA("Rond�nia","RO"), RIO_DE_JANEIRO("Rio de Janeiro","RJ"),
	RIO_GRANDE_DO_NORTE("Rio Grande do Norte","RN"), RIO_GRANDE_DO_SUL("Rio Grande do Sul","RS"), SANTA_CATARINA("Santa Catarina","SC"), 
	SAO_PAULO("S�o Paulo","SP"), SERGIPE("Sergipe","SE"), TONANTINS("Tocantins","TO");
	private String nome, sigla;
	Estado(String nome, String sigla)
	{
		this.nome = nome;
		this.sigla = sigla;	
	}
	
	public static Estado getEstado(String estado)
	{
		for(Estado e : values())
			if(e.toString().equalsIgnoreCase(estado) || e.toString().substring(e.toString().indexOf("-")).toUpperCase().contains(estado.toUpperCase()))
				return e;
		return null;
	}
	
	@Override
	public String toString() {
		return nome+" - "+sigla;
	}
}
