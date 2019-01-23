package dao;

import entidade.Entidade;
import entidade.Juridico;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		StringBuilder capsula = new StringBuilder("select "+alias+" from "+entidade.getName()+" as "+alias+" ");
		StringBuilder conteudo = new StringBuilder(" where ");
		StringBuilder joins = new StringBuilder();
		Class<?> current = entidade;
		
		for(Object restricao : restricoes.keySet()) 
			conteudo.append(restricao + restricoes.get(restricao) + " and ");
		conteudo.append(" upper(concat('' ");
		
		while(current.getSuperclass()!=null){
			alias = entidade.getSimpleName().toLowerCase();
		    for(Field  f : current.getDeclaredFields()) 
				if(restricoes.get(alias + "." + f.getName()) == null && !f.getName().equals("serialVersionUID") && 
					!Collection.class.isAssignableFrom(f.getType()) ) 
				{
					if(Entidade.class.isAssignableFrom(f.getType())) 
					{
						joins.append(" inner join "+alias+"."+f.getName()+" as "+f.getName()+" ");
						if(restricoes.get(f.getName()+".id") == null)
							conteudo.append(gerarHqlBuscaParcil(f.getType(),f.getName()));
					}else
						conteudo.append(","+alias+"."+f.getName());
				}
		    current = current.getSuperclass();
		}
		capsula.append(joins).append(conteudo).append(")) like upper(:busca) ");
		
		return capsula.toString();
	}
	
	public static void main(String[] args) {
		Map<String,String> restricoes = new HashMap<>();
		restricoes.put("juridico.ativo","= true");
		restricoes.put("endereco.id","= 1");
		
		System.out.println(gerarHqlBuscaAbrangente(Juridico.class,restricoes));
	}
	
	public static String gerarHqlBuscaAbrangente(Class<? extends Entidade> entidade, String alias) {
		StringBuilder capsula = new StringBuilder("select "+alias+" from "+entidade.getName()+" as "+alias+" ");
		StringBuilder conteudo = new StringBuilder(" where upper(concat('' ");
		StringBuilder joins = new StringBuilder();
		Class<?> current = entidade;
		while(current.getSuperclass()!=null){
		    for(Field  f : current.getDeclaredFields()) 
				if(!f.getName().equals("serialVersionUID") && !Collection.class.isAssignableFrom(f.getType()) ) {
					if(Entidade.class.isAssignableFrom(f.getType())) {
						joins.append(" inner join "+alias+"."+f.getName()+" as "+f.getType().getSimpleName().toLowerCase()+" ");
						conteudo.append(" or ( "+alias+"."+f.getName()+ "!= null and upper(concat(''"+gerarHqlBuscaParcil(f.getType(), 
								f.getType().getSimpleName().toLowerCase())+")) like upper(:busca))");
					}else
						conteudo.append(","+alias+"."+f.getName());
				}
		    current = current.getSuperclass();
		}
		return capsula.append(joins).append(conteudo).append(")) like upper(:busca)").toString();
	}
	
	public static String gerarHqlBusca(Class<? extends Entidade> entidade, String alias) {
		StringBuilder sql = new StringBuilder("select "+alias+" from "+entidade.getName()+" as "+alias+" where upper(concat(''\n\t");
		sql.append(gerarHqlBuscaParcil(entidade, alias));
		return sql.append(")) like upper(:busca)").toString();
	}
	
	
	private static String gerarHqlBuscaParcil(Class<?> entidade, String alias) {
		StringBuilder sqlParcil = new StringBuilder();
		    for(Field  f : entidade.getDeclaredFields()) 
				if(!f.getName().equals("serialVersionUID") && !Collection.class.isAssignableFrom(f.getType()) 
						&&!Entidade.class.isAssignableFrom(f.getType())) {
					sqlParcil.append(","+alias+"."+f.getName());
				}
		return sqlParcil.toString();
	}
	
}
