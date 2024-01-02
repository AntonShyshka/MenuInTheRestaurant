import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Methods {
    static EntityManager em = MainInterface.em;

    protected static void addNewDish(Scanner scanner) {
        System.out.println("Enter dish name:");
        String dishName = scanner.nextLine();
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        System.out.println("Enter weight:");
        int weight = scanner.nextInt();
        System.out.println("Enter discount in percentages:");
        int discount = scanner.nextInt();

        em.getTransaction().begin();
        try {
            MenuInTheRestaurantTable menuInTheRestaurantTable =
                    new MenuInTheRestaurantTable(dishName, price, weight, discount);
            em.persist(menuInTheRestaurantTable);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    protected static void priceFromTo(Scanner scanner) {
        System.out.println("Enter price FROM:");
        double priceFrom = scanner.nextInt();
        System.out.println("Enter price TO:");
        double priceTo = scanner.nextInt();

        try {
            Query query = em.createQuery("SELECT m FROM MenuInTheRestaurantTable m " +
                            "WHERE m.price >= :priceFrom AND m.price <= :priceTo",
                    MenuInTheRestaurantTable.class);
            query.setParameter("priceFrom", priceFrom);
            query.setParameter("priceTo", priceTo);

            ArrayList<MenuInTheRestaurantTable> menuList =
                    (ArrayList<MenuInTheRestaurantTable>) query.getResultList();

            if (menuList.size() == 0)
                System.out.println("No dishes found");

            for (MenuInTheRestaurantTable m : menuList)
                System.out.println("DISH NAME: " + m.getDishName() + " | " +
                        "PRICE: " + m.getPrice() + " | " +
                        "WEIGHT: " + m.getWeight() + " | " +
                        "DISCOUNT: " + m.getDiscountPercentage());
        } catch (NoResultException e) {
            System.out.println("No dishes found");
        }
    }

    protected static void dishesOnlyWithDiscount() {
        try {
            Query query = em.createQuery("SELECT d FROM MenuInTheRestaurantTable d " +
                    "WHERE d.discountPercentage > 0", MenuInTheRestaurantTable.class);

            ArrayList<MenuInTheRestaurantTable> menuList =
                    (ArrayList<MenuInTheRestaurantTable>) query.getResultList();

            if (menuList.size() == 0)
                System.out.println("No dishes found");

            for (MenuInTheRestaurantTable m : menuList)
                System.out.println("DISH NAME: " + m.getDishName() + " | " +
                        "PRICE: " + m.getPrice() + " | " +
                        "WEIGHT: " + m.getWeight() + " | " +
                        "DISCOUNT: " + m.getDiscountPercentage());
        } catch (NoResultException e) {
            System.out.println("No dishes found");
        }
    }

    protected static void selectDishesLessOneKilograms() {
        Random random = new Random();
        double totalWeight = 0;
        double w = 0;
        boolean stop = true;

        try {
            Query query = em.createQuery("SELECT w FROM MenuInTheRestaurantTable w",
                    MenuInTheRestaurantTable.class);
            ArrayList<MenuInTheRestaurantTable> menuList =
                    (ArrayList<MenuInTheRestaurantTable>) query.getResultList();
            MenuInTheRestaurantTable m;

            while (stop) {
                int index = random.nextInt(menuList.size());
                m = menuList.get(index);

                totalWeight += m.getWeight();

                menuList.remove(index);

                if (totalWeight >= 1000) {
                    stop = false;
                    w = totalWeight - m.getWeight();
                } else {
                    System.out.println("DISH NAME: " + m.getDishName() + " | " +
                            "PRICE: " + m.getPrice() + " | " +
                            "WEIGHT: " + m.getWeight() + " | " +
                            "DISCOUNT: " + m.getDiscountPercentage());
                }
            }

            System.out.println("Total weight: " + w / 1000 + " kilograms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}