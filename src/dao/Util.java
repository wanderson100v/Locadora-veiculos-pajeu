package dao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import entidade.Entidade;
import entidade.Juridico;

public class Util {
	
	
	/**
	 * 
	 * @param entidade entidade principal da busca
	 * @param restricoes mapa contendo chave que identifica classe e atributo, como também 
	 * 		  conteúdo que representa restrição. Exemplo chave "juridico.ativo" conteúdo "= true"
	 * 		  new HashMap().put("juridico.ativo","= true")
	 * @return hql gerado
	 */
	public static String gerarHqlBuscaAbrangente(Class<? extends Entidade> entidade, Map<String,String> restricoes) {
		
		String alias = entidade.getSimpleName().toLowerCase();
		StringBuilder capsula = new StringBuilder(" SELECT  "+alias+".* ");
		StringBuilder joins = new StringBuilder("\n FROM " +getNomeTabela(entidade)+" AS "+alias);
		String entidadeBuscada = new String("\n ((UPPER(CONCAT('',"+alias+")) LIKE UPPER(:busca)) ");
		StringBuilder entidadesAtributo = new StringBuilder();
		StringBuilder restricoesBusca = new StringBuilder("\n WHERE ");
		
		for(Object restricao : restricoes.keySet()) 
			restricoesBusca.append(restricao + restricoes.get(restricao) + "\n AND ");
		
		Class<?> current = entidade;
		while(current.getSuperclass()!=null){
			for(Field  f : current.getDeclaredFields()) {
				if(Entidade.class.isAssignableFrom(f.getType())) {
					String aliasAtributo = f.getName();
					joins.append("\n LEFT JOIN "+getNomeTabela(f.getType())+" AS "+aliasAtributo
							+" ON ("+alias+"."+f.getName()+"_id ="+aliasAtributo+".id)");
					if(restricoes.get(f.getName()+".id") == null)
						entidadesAtributo.append("\n OR (UPPER(CONCAT('',"+aliasAtributo+")) LIKE UPPER(:busca))");
				}
			}
		    current = current.getSuperclass();
		    if(current == Entidade.class)
		    	break;
		    String generalizacao = getNomeTabela(current);
		    String aliasGene= generalizacao.toLowerCase();
		    capsula.append(", "+aliasGene+".*");
			joins.append("\n INNER JOIN "+generalizacao+" as "+aliasGene+ " ON ("+alias+".id ="+aliasGene+".id)");
			entidadesAtributo.append("\n OR (UPPER(CONCAT('',"+aliasGene+")) LIKE UPPER(:busca))");
			alias = aliasGene;
		}
		
		return capsula.append(joins)
				.append(restricoesBusca)
				.append(entidadeBuscada)
				.append(entidadesAtributo)
				.append(")").toString();
	}
	
	public static void main(String[] args) {
		Map<String,String> restricoes = new HashMap<>();
		restricoes.put("juridico.ativo","= true");
		restricoes.put("endereco.id","= 1");
		System.out.println(gerarHqlBuscaAbrangente(Juridico.class,restricoes));
	}
	
	public static String gerarHqlBuscaAbrangente(Class<? extends Entidade> entidade, String alias) {
		
		StringBuilder capsula = new StringBuilder(" SELECT  "+alias+".* ");
		StringBuilder joins = new StringBuilder("\n FROM " +getNomeTabela(entidade)+" AS "+alias);
		String entidadeBuscada = new String("\n WHERE ((UPPER(CONCAT('',"+alias+")) LIKE UPPER(:busca)) ");
		StringBuilder entidadesAtributo = new StringBuilder();
		
		Class<?> current = entidade;
		while(current.getSuperclass()!=null){
			for(Field  f : current.getDeclaredFields()) {
				if(Entidade.class.isAssignableFrom(f.getType())) {
					String aliasAtributo = f.getName();
					joins.append("\n LEFT JOIN "+getNomeTabela(f.getType())+" AS "+aliasAtributo
							+" ON ("+alias+"."+f.getName()+"_id ="+aliasAtributo+".id)");
					entidadesAtributo.append("\n OR (UPPER(CONCAT('',"+aliasAtributo+")) LIKE UPPER(:busca))");
				}
			}
		    current = current.getSuperclass();
		    if(current == Entidade.class)
		    	break;
		    String generalizacao = getNomeTabela(current);
		    String aliasGene= generalizacao.toLowerCase();
		    capsula.append(", "+aliasGene+".*");
			joins.append("\n INNER JOIN "+generalizacao+" as "+aliasGene+ " ON ("+alias+".id ="+aliasGene+".id)");
			entidadesAtributo.append("\n OR (UPPER(CONCAT('',"+aliasGene+")) LIKE UPPER(:busca))");
			alias = aliasGene;
		}
		
		return capsula.append(joins)
				.append(entidadeBuscada)
				.append(entidadesAtributo)
				.append(")").toString();
	}
	
	public static String gerarHqlBusca(Class<? extends Entidade> entidade, String alias) {
		StringBuilder sql = new StringBuilder("SELECT "+alias+" FROM "+entidade.getName()
				+" AS "+alias+" WHERE UPPER(CONCAT(''\n\t");
		sql.append(gerarHqlBuscaParcil(entidade, alias));
		return sql.append(")) LIKE UPPER(:busca)").toString();
	}
	
	private static String getNomeTabela(Class<?> entidade) {
		if(Entidade.class.isAssignableFrom(entidade)){
			String nomeTabela = entidade.getAnnotation(Entity.class).name();
			if(nomeTabela.isEmpty())
				nomeTabela = entidade.getSimpleName();
			return nomeTabela;
		}
		return null;
	}
	
	
	private static String gerarHqlBuscaParcil(Class<?> entidade, String alias) {
		return " OR (UPPER(CONCAT('',"+alias+")) LIKE UPPER(:busca))";
	}
	
}
