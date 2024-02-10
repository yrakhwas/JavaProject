package org.example;


import org.example.models.Category;
import org.example.models.Product;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.cfg.VerifyFetchProfileReferenceSecondPass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //addCategory();
        //getList();
        //addProduct();
        //editCategory();
        //editProduct();
        addProduct();
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
    public static void addCategory()
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
    public static void addProduct()
    {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession())
        {
            context.beginTransaction();
            Product product = new Product();
            System.out.println("Enter name of product: ");
            String temp = scanner.nextLine();
            product.setName(temp);

            System.out.println("Tape description : ");
            temp = scanner.nextLine();
            product.setDescription(temp);

            System.out.println("Tape price : ");
            product.setPrice(scanner.nextDouble());

            scanner.nextLine();

            Category category = new Category();
            System.out.println("Enter category id :");
            category.setId(scanner.nextInt());

            System.out.println("Enter path to the photo :");
            String photoPath = scanner.nextLine();
            byte[]photo = loadPhoto(photoPath);
            product.setPhoto(photo);

            product.setCategory(category);
            context.save(product);
            context.getTransaction().commit();
        }
    }
    public static void editCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter ID of category you want to edit : ");
            int categoryId = scanner.nextInt();
            Category category = context.get(Category.class, categoryId);
            if (category == null) {
                System.out.println("Category not found");
                return;
            }
            scanner.nextLine(); // Consume the newline character left in the buffer
            System.out.println("Enter new name of category : ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                category.setName(newName);
            }
            System.out.println("Enter new URL of category photo : ");
            String newPhoto = scanner.nextLine();
            if (!newPhoto.isEmpty()) {
                category.setImage(newPhoto);
            }
            category.setDateCreated(calendar.getTime());
            context.update(category);
            context.getTransaction().commit();
            System.out.println("Category was updated successfully");
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public static void editProduct() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter ID of product you want to edit : ");
            int productId = scanner.nextInt();
            Product product = context.get(Product.class, productId);
            if (product == null) {
                System.out.println("Product not found");
                return;
            }
            scanner.nextLine(); // Consume the newline character left in the buffer
            System.out.println("Enter new name of product : ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                product.setName(newName);
            }
            System.out.println("Enter new description of product : ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                product.setDescription(newDescription);
            }
            System.out.println("Enter new price of product : ");
            double newPrice = scanner.nextDouble();
            if (newPrice != 0) {
                product.setPrice(newPrice);
            }
            context.update(product);
            context.getTransaction().commit();
            System.out.println("Product was updated successfully");
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }
    private static byte[] loadPhoto(String photoPath)
    {
        try
        {
            Path path = Paths.get(photoPath);
            return Files.readAllBytes(path);
        }
        catch(IOException ex)
        {
            System.out.println("Error loading photo : " + ex.getMessage());
            return null;
        }
    }
}