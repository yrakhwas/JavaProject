package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            //simpleArray()
            //inputData()
            //sortPerson();
            String userName = "root";
            String password = "";
            String host = "localhost";
            String port = "3306";
            String database = "java_vpd111";
            String strConn = "jdbc:mariadb://"+host+":"+port+"/"+database;
            //createNewTable(strConn, userName, password);
            insertCategory(strConn, userName, password);
        }
        private static void insertCategory(String strConn, String userName, String password)
        {
        Scanner input = new Scanner(System.in);
        CategoryCreate categoryCreate = new CategoryCreate();
        System.out.println("Вкажіть назву категорії:");
        categoryCreate.setName(input.nextLine());
        System.out.println("Вкажіть опис категорії:");
        categoryCreate.setDescription(input.nextLine());

        try(Connection conn = DriverManager.getConnection(strConn,userName,password)) {
            Statement statement = conn.createStatement();
            String insertQuery = "INSERT INTO categories (name, description) VALUES (?, ?)";
            // Create a PreparedStatement
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            // Set values for the placeholders
            preparedStatement.setString(1, categoryCreate.getName());
            preparedStatement.setString(2, categoryCreate.getDescription());
            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            // Close the resources
            preparedStatement.close();
            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Category inserted successfully.");
        }
        catch(Exception ex) {
            System.out.println("Помилка створення категорії: "+ex.getMessage());
        }
    }
        private static void createNewTable(String strConn, String userName, String password)
        {
            try(Connection conn = DriverManager.getConnection(strConn, userName, password))
            {
                Statement statement = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS categories ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "name VARCHAR(255) NOT NULL,"
                        + "description TEXT"
                        + ")";

                statement.execute(sql);
                statement.close();
                System.out.println("----Table was successfully created----");
            }
            catch(Exception ex)
            {
                System.out.println("Connection failed : "+ex.getMessage());
            }
        }
        public static int getRandom(int min, int max)
        {
            Random random = new Random();
            return random.nextInt(max-min)+min;
        }
        private static void sortPerson()
        {
            Person p = new Person();
            p.setLastName("Yurii");
            p.setFirstName("Murchik");
            System.out.println(p);
            Person p1 = new Person("Kapitanov", "Mudryk");
            System.out.println(p1);

            Person [] list = {
                    new Person("Kapitanov", "Mudryk"),
                    new Person("Andrii", "Julik"),
                    new Person("Petro", "Rogatka"),
                    new Person("Mikola", "Pidkabluchnik"),
            };
            System.out.println("Normal list");
            for (var item: list)
            {
                System.out.println(item);
            }
            Arrays.sort(list, new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    return o1.getLastName().compareTo(o2.getLastName());
                }
            });
            System.out.println("Sorted list");
            for (var item: list)
            {
                System.out.println(item);
            }
            Arrays.sort(list);
            System.out.println("Sorted list");
            for(var item:list)
            {
                System.out.println(item);
            }
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