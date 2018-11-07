package sql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory{
	private static Map<String, String> propriedades;
	
	public static EntityManagerFactory getConnection() {
		return Persistence.createEntityManagerFactory("banco",propriedades);
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
	
}