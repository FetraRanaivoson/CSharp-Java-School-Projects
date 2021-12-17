package day2;

import java.util.Scanner;

public class escapeCharacters {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = scanner.next();
        System.out.println("Hello " + name);

        String name1 = scanner.next();
        System.out.println("Hello " + name1);
    }
}
