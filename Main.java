import java.util.*;

// Define a Car class
class Car {
    public String brand;
    public String model;
    public int year;

    public Car(String b, String m, int y) {
        brand = b;
        model = m;
        year = y;
    }
}

// Create a comparator
class SortByYear implements Comparator {
    public int compare(Object obj1, Object obj2) {
        // Make sure that the objects are Car objects
        Car a = (Car) obj1;
        Car b = (Car) obj2;

        // Compare the year of both objects
        if (a.year < b.year) return -1; // The first car has a smaller year
        if (a.year > b.year) return 1;  // The first car has a larger year
        return 0; // Both cars have the same year
    }
}

public class Main {
    public static void main(String[] args) {
        PriorityQueue<SortByYear> pq = new PriorityQueue<>((u, v) -> {
            return 1;
        });
    }
}
