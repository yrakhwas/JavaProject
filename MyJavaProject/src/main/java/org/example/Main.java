package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            //simpleArray()
            //inputData()
            Person p = new Person();
            p.setLastName("Yurii");
            p.setFirstName("Murchik");
            System.out.println(p);

        }
        public static int getRandom(int min, int max)
        {
            Random random = new Random();
            return random.nextInt(max-min)+min;
        }
        private static void simpleArray()
        {
            //inputData();
            int n = 10;//const final
            int [] mas = new int[n];

            Random rand = new Random();
            for (int i=0; i<n; i++)
            {
                int value = getRandom(20,50);
                System.out.println(value);
                mas[i] = getRandom(-10,10);
            }
            System.out.println("Array: ");
            for(var item:mas)
            {
                System.out.printf("%d\t", item);
            }
            System.out.println("\nSorted Array: ");
            Arrays.sort(mas);
            for(var item:mas)
            {
                System.out.printf("%d\t", item);
            }
            int count = 0;
            for(var item:mas)
            {
                if(item>0)
                {
                    count++;
                }
            }
            System.out.printf("Count of add numbers : %s", count);
        }

        private static void inputData()
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What`s your name?");
            String name = scanner.nextLine();
            System.out.printf("Hello %s!\n", name);
            //sout
        }
    }