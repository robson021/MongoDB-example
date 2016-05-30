import example.config.HibernateMongoSessionUtils;
import example.entities.Item;
import example.entities.Order;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by robert on 30.05.16.
 */
public class MainClass {

    public static Session getSession() throws HibernateException {
        return HibernateMongoSessionUtils.getInstance().openSession();
    }

    public static void add() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Item item1 = new Item("Książka 1", 10.0);
        Item item2 = new Item("Ksiązka 2", 14.99);
        Order order = new Order();
        order.setDate(new Date());
        order.getItems().add(item1);
        order.getItems().add(item2);
        session.save(order);
        tx.commit();
        session.close();
    }

    public static void changePriceOfBook() {
        // znajdz ksiazke
        final Session session = getSession();
        Query q = session.createQuery("FROM Item i where i.name = :name");
        q.setParameter("name", "Książka 1");
        List<Item> items = q.list();
        for (Item item : items) {
            System.out.printf("%s %f\n", item.getName(), item.getPrice());
        }
        // aktualizacja
        session.beginTransaction();
        Random random = new Random();
        Item book1 = items.get(0);
        double newPrice = random.nextDouble() * 20.0;
        System.out.println("Nowa cena: " + newPrice);
        book1.setPrice(newPrice);
        session.update(book1);
        session.getTransaction().commit();
    }

    private static void removeOrder() {
        // znajdz pierwsze zamowienie
        final Session session = getSession();
        Order order = (Order) session.createQuery("FROM Order order ORDER BY date").list().get(0);
        if (order != null) {
            session.beginTransaction();
            System.out.printf("%s %s", order.getDate(), order.getItems());
            session.delete(order);
            session.getTransaction().commit();
        }
        session.close();
    }
    public static void main(String[] args) {
        System.out.println("MongoDB example");

        add();
        changePriceOfBook();
        removeOrder();

        System.out.println("\n\n\tDONE");
    }

}
