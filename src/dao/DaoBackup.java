package dao;

import entidade.Backup;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoBackup extends Dao<Backup> implements IDaoBackup{

	public DaoBackup() {
		super(Backup.class);
	}
	
	public Backup checarBackup() throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			return (Backup) em.createStoredProcedureQuery("checar_backup",Backup.class)
					.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CHCAR BACKUP");
		}finally {
			em.close();
		}
	}
}
