package hibernate.util;

import hibernate.entity.FamilyMember;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	public static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();

			// Create Properties, can be read from property files too
			Properties props = new Properties();
			props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
			props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/testdb");
			props.put("hibernate.connection.username", "cuong.ntt");
			props.put("hibernate.connection.password", "cuong.ntt");
			props.put("hibernate.current_session_context_class", "thread");
			props.put("hibernate.show_sql", "true");

			configuration.setProperties(props);

			// Add entity
			configuration.addAnnotatedClass(FamilyMember.class);

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			System.out.println("Hibernate Java Config serviceRegistry created");

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;
		}
		catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
