package org.example;


import org.example.models.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession())
        {
            context.beginTransaction();
            Category category = new Category();
            System.out.println("Enter name of category: ");
            String temp = scanner.nextLine();

            category.setName(temp);
            System.out.println("Choose photo : ");
            temp = scanner.nextLine();
            category.setImage(temp);
            category.setDateCreated(calendar.getTime());
            context.save(category);

            context.getTransaction().commit();
        }
        System.out.println("Hello Java!");
    }
}