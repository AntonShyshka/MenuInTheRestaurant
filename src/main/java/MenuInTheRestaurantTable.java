import javax.persistence.*;

@Entity
@Table(name = "menu_in_the_restaurant_table")
public class MenuInTheRestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "price")
    private double price;

    @Column(name = "weight")
    private int weight;

    @Column(name = "discount_percentage")
    private int discountPercentage;

    public MenuInTheRestaurantTable() { }

    public MenuInTheRestaurantTable(String dishName, double price, int weight, int discountPercentage) {
        this.dishName = dishName;
        this.price = price;
        this.weight = weight;
        this.discountPercentage = discountPercentage;
    }

    public String getDishName() {
        return dishName;
    }

    public double getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }
}