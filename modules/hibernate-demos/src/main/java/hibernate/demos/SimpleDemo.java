package hibernate.demos;

import hibernate.entity.FamilyMember;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SimpleDemo {
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		FamilyMember familyMember = session.get(FamilyMember.class, 1);

		session.getTransaction().commit();

		System.out.println(familyMember);

		session.close();
	}
}
