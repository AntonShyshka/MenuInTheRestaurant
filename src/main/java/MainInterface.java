import com.sun.jdi.event.ExceptionEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class MainInterface {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean whenClose = true;

        try {
            emf = Persistence.createEntityManagerFactory("MenuInTheRestaurant");
            em = emf.createEntityManager();

            try {
                while (whenClose) {
                    System.out.println("Enter you choose:");
                    System.out.println("\t1: add new dish");
                    System.out.println("\t2: select dishes with price FROM-TO:");
                    System.out.println("\t3: select dishes only with discount");
                    System.out.println("\t4: make a random dishes a set with a weight of less than 1 kilogram");
                    System.out.println("\tEnter 'E' for exit");
                    System.out.print("->");

                    String s = scanner.nextLine();
                    switch (s) {
                        case "1" -> Methods.addNewDish(scanner);
                        case "2" -> Methods.priceFromTo(scanner);
                        case "3" -> Methods.dishesOnlyWithDiscount();
                        case "4" -> Methods.selectDishesLessOneKilograms();
                        case "E" -> whenClose = false;
                    }
                }
            } finally {
                scanner.close();
                emf.close();
                em.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}