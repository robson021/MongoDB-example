package example.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 30.05.16.
 */
@Entity
public class Item {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "items")
    private List<Order> inOrders = new ArrayList<>();
    private String name;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Order> getInOrders() {
        return inOrders;
    }

    public void setInOrders(List<Order> inOrders) {
        this.inOrders = inOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Item() {
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", inOrders=" + inOrders +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}