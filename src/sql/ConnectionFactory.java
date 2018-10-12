package sql;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory{
	public static EntityManagerFactory getConnection() {
		return Persistence.createEntityManagerFactory("banco");
	}
	
}