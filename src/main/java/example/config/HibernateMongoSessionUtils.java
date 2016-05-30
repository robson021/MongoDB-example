package example.config;

import example.entities.Item;
import example.entities.Order;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.boot.OgmSessionFactoryBuilder;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by robert on 30.05.16.
 */
public class HibernateMongoSessionUtils {
    private static SessionFactory SESSION_FACTORY;
    private static ServiceRegistry SERVICE_REGISTRY;

    private HibernateMongoSessionUtils() {
    }

    public static SessionFactory getInstance() {
        if (SESSION_FACTORY == null) {
            synchronized (SessionFactory.class) {
                if (SESSION_FACTORY == null) {
                    Configuration config = new OgmConfiguration().configure("hibernate.cfg.xml");
                    SERVICE_REGISTRY = new StandardServiceRegistryBuilder()
                            .applySettings(config.getProperties())
                            .build();
                    SESSION_FACTORY = new MetadataSources(SERVICE_REGISTRY)
                            .addAnnotatedClass(Item.class)
                            .addAnnotatedClass(Order.class)
                            .buildMetadata()
                            .getSessionFactoryBuilder()
                            .unwrap(OgmSessionFactoryBuilder.class)
                            .build();
                }
            }
        }
        return SESSION_FACTORY;
    }
}
