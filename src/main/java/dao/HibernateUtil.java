package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Cargar configuración de hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			// Construir la sesión de fábrica
			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Error al inicializar la SessionFactory: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Cerrar las conexiones de Hibernate al cerrar la aplicación
		getSessionFactory().close();
	}
}
