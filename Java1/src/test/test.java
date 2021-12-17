package test;

import javax.management.StringValueExp;
import java.util.Random;

public class test {
    public static void main(String[] args) {

        Random randomInt = new Random();

        int a = 5;

        for (int i=0; i<10; i++) {
            System.out.println(randomInt.nextInt(10) + 1);
        }
    }
}
