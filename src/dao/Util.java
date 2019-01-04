package dao;

import entidade.Automovel;
import entidade.Entidade;

import java.lang.reflect.Field;
import java.util.Collection;

public class Util {
	
	public static String gerarSqlBuscaAbrangente(Class<? extends Entidade> entidade, String alias) {
		StringBuilder capsula = new StringBuilder("select "+alias+" from "+entidade.getName()+" as "+alias+" ");
		StringBuilder conteudo = new StringBuilder(" where upper(concat('' ");
		StringBuilder joins = new StringBuilder();
		Class<?> current = entidade;
		while(current.getSuperclass()!=null){
		    for(Field  f : current.getDeclaredFields()) 
				if(!f.getName().equals("serialVersionUID") && !Collection.class.isAssignableFrom(f.getType()) ) {
					if(Entidade.class.isAssignableFrom(f.getType())) {
						joins.append(" inner join "+alias+"."+f.getName()+" as "+f.getType().getSimpleName().toLowerCase()+" ");
						conteudo.append(gerarSqlBuscaParcil(f.getType(), f.getType().getSimpleName().toLowerCase()));
					}else
						conteudo.append(","+alias+"."+f.getName());
				}
		    current = current.getSuperclass();
		}
		return capsula.append(joins).append(conteudo).append(")) like upper(:busca)").toString();
	}
	
	public static String gerarSqlBusca(Class<? extends Entidade> entidade, String alias) {
		StringBuilder sql = new StringBuilder("select "+alias+" from "+entidade.getName()+" as "+alias+" where upper(concat(''\n\t");
		sql.append(gerarSqlBuscaParcil(entidade, alias));
		return sql.append(")) like upper(:busca)").toString();
	}
	
	
	public static String gerarSqlBuscaParcil(Class<?> entidade, String alias) {
		StringBuilder sqlParcil = new StringBuilder();
		    for(Field  f : entidade.getDeclaredFields()) 
				if(!f.getName().equals("serialVersionUID") && !Collection.class.isAssignableFrom(f.getType()) 
						&&!Entidade.class.isAssignableFrom(f.getType())) {
					sqlParcil.append(","+alias+"."+f.getName());
				}
		return sqlParcil.toString();
	}
	
}
