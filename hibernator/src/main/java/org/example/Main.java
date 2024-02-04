package org.example;


import org.example.models.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //AddCategory();

    }
    public static void getList()
    {
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession())
        {
            context.beginTransaction();
            List<Category> list = context.createQuery("from Category", Category.class).getResultList();

            for(Category category : list)
            {
                System.out.println("Category : " + category);
            }
            context.getTransaction().commit();
        }
        catch (Exception ex)
        {
            System.out.println("Session can`t start " + ex.getMessage());
        }
    }
    public static void AddCategory()
    {
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
    }
}