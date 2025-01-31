

import java.util.Scanner;

public class first{
    private static final double PI = 3.14;
    private final double radius;

    public first(double radius) {
        this.radius = radius;
    }

    public double getCircumference() {
        return 2 * PI * radius;
    }

    public double getArea() {
        return PI * radius * radius;
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------");
        System.out.println("CircleCalc v1.0");
        System.out.println();
        System.out.println("Calculates and prints information for a user-supplied radius");
        System.out.println("------------------------------------------------------------");

        System.out.print("Circle iin radiusiig oruulna uu: ");
        Scanner in = new Scanner(System.in);
        double radius = in.nextDouble();
        in.close();

        first c = new first(radius);

        System.out.printf("Circumference: %.2f\n", c.getCircumference());
        System.out.printf("Area: %.2f\n", c.getArea());
    }
}

