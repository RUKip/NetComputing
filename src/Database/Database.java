package Database;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

import javax.jdo.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


/*
 * The database is now made for Employees - needs to be changed 
 * to connections.
 * 
 */
public class Database //implements RemoteDatabase{
	{
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public Database(){
		this.emf =  Persistence.createEntityManagerFactory("Database/people.odb");
		this.em = emf.createEntityManager();
//		this.setInitialValues();
	}

	public void setInitialValues(){
		Employee e1 = new Employee("Ana", "Roman");
		Employee e2 = new Employee("Ruben", "Kip");
		Employee e3 = new Employee("Teun");
		this.add(e1);
		this.add(e2);
		this.add(e3);
	}
	
	public void close(){
		em.close();
		emf.close();
	}
	
//	public PlayerScore getThresholdScore(){
//		TypedQuery query = em.createQuery("SELECT p FROM PlayerScore p", PlayerScore.class);
//		ArrayList<PlayerScore> scores = (ArrayList<PlayerScore>) query.getResultList();
//		Collections.sort(scores, (p1,p2) -> p2.getScore() - p1.getScore());
//
//		if (scores.size() < 10)
//			return new PlayerScore("", -1);
////		if (scores.size() < 10)
////			return scores.get(scores.size() - 1);
//		return scores.get(9);
//	 }
	
	
	//TODO: Have 
	public void add(Employee employee){
		TypedQuery query = em.createQuery("SELECT p FROM Employee p WHERE p.firstName='" + employee.getFirstName()+"'", Employee.class);
		ArrayList<Employee> employees = (ArrayList<Employee>) query.getResultList();

		if(employees.isEmpty()){
			System.out.println("employee added");
			em.getTransaction().begin();
			em.persist(employee);
			em.getTransaction().commit();
		}
		else {
			em.getTransaction().begin();
			em.remove(employees.get(0));
			em.getTransaction().commit();
			em.getTransaction().begin();
			em.persist(employee);
			em.getTransaction().commit();
		}
	}
	
	public long getTotalEntries(){
		Query q1 = (Query) em.createQuery("SELECT COUNT(p) FROM Employee p");
		long entries = (long) ((javax.persistence.Query) q1).getSingleResult();
		return entries;
	}
	
	public ArrayList<Employee> getAll(){
		TypedQuery query = em.createQuery("SELECT p FROM PlayerScore p", Employee.class);
		ArrayList<Employee> employees = (ArrayList<Employee>) query.getResultList();
		return employees;
	}
	
	public void removeAll(){
		em.getTransaction().begin();
		int deletedCount = em.createQuery("DELETE FROM Employee").executeUpdate();
		em.getTransaction().commit();
	}

	
}
