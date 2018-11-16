package sql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory{
	private static Map<String, String> propriedades;
	private static EntityManagerFactory entityManagerFactory;
	
	public static EntityManager getConnection() {
		if(entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("banco",propriedades);
		return entityManagerFactory.createEntityManager();
	}
	
	public static void setUser(String login, String senha) {
		if(propriedades == null) {
			propriedades = new HashMap<>();
			propriedades.put("javax.persistence.jdbc.user",login);
			propriedades.put("javax.persistence.jdbc.password",senha);
		}else {
			propriedades.replace("javax.persistence.jdbc.user",login);
			propriedades.replace("javax.persistence.jdbc.password",senha);
		}
		
	}
	public static void stop() {
		entityManagerFactory.close();
	}
	
}