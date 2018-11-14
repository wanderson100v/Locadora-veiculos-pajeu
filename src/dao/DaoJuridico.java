package dao;

import entidade.Juridico;

public class DaoJuridico extends Dao<Juridico> implements IDaoJuridico{

	public DaoJuridico() {
		super(Juridico.class);
	}
	
	/*public static void main(String[] args) {
		ConnectionFactory.setUser("postgres","admin");
		
		
		   EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		   for(Object object : em.createQuery("select j.nome, e.cep from "
											+ "Juridico j inner join Endereco e "
											+ "on e = j.endereco ").getResultList())
		{
			System.out.println();
			Object[] regristro = (Object[]) object;
			for(Object e: regristro)
				System.out.println(e);
		}
			
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		TypedQuery<Juridico> consulta = em.createNamedQuery("buscarPorTudo",Juridico.class);
		
		Juridico juridico = new Juridico();	
		juridico.setNome("Funeraria leva e não traz");
		consulta.setParameter(1,juridico);
	}*/

}
