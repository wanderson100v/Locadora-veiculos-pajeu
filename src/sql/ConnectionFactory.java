package sql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.postgresql.util.PSQLException;

import excecoes.BoException;

public class ConnectionFactory{
	private static Map<String, String> propriedades;
	private static EntityManagerFactory entityManagerFactory;
	
	public static EntityManager getConnection() {
		return entityManagerFactory.createEntityManager();
	}
	
	public static void setUser(String login, String senha) throws BoException {
		try {
			if(propriedades == null) {
				propriedades = new HashMap<>();
				propriedades.put("javax.persistence.jdbc.user",login);
				propriedades.put("javax.persistence.jdbc.password",senha);
			}else {
				propriedades.replace("javax.persistence.jdbc.user",login);
				propriedades.replace("javax.persistence.jdbc.password",senha);
			}
			entityManagerFactory = Persistence.createEntityManagerFactory("banco",propriedades);
		}catch(Exception e) {
			throw new BoException("Dados de acesso invalidos");
		}
	}
	public static String[] getUser() {
		String[] loginSenha = new String[2];
		loginSenha[0] = propriedades.get("javax.persistence.jdbc.user");
		loginSenha[1] =propriedades.get("javax.persistence.jdbc.password");
		return loginSenha;
	}
	
}