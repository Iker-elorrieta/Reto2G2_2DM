package modelo;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Environment env;

    public HibernateUtil(Environment environment) {
        HibernateUtil.env = environment;
        HibernateUtil.sessionFactory = buildSessionFactory();
        System.out.println("--- SessionFactory inicializada correctamente ---");
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            cfg.configure(); // hibernate.cfg.xml

            
            cfg.addResource("modelo/Ciclos.hbm.xml");
            cfg.addResource("modelo/Users.hbm.xml");
            cfg.addResource("modelo/Modulos.hbm.xml");
            cfg.addResource("modelo/Tipos.hbm.xml");
            cfg.addResource("modelo/Matriculaciones.hbm.xml"); 
            cfg.addResource("modelo/Horarios.hbm.xml");
            cfg.addResource("modelo/Reuniones.hbm.xml");
            
            Properties props = new Properties();
            props.put("hibernate.connection.url", env.getProperty("db.url"));
            props.put("hibernate.connection.username", env.getProperty("db.username"));

            String password = env.getProperty("db.password");
            if (password != null && !password.isEmpty()) {
                props.put("hibernate.connection.password", password);
            }

            cfg.addProperties(props);

            return cfg.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build()
            );

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
