package org.example;

import org.mariadb.jdbc.client.result.Result;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
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
            //insertCategory(strConn, userName, password);
            //showListOfCategories(strConn, userName, password);
            //editCategory(strConn, userName, password);
            //deleteCategory(strConn, userName, password);
            //showListOfCategories(strConn, userName, password);
            int choice;
            do
            {
                Scanner input = new Scanner(System.in);
                System.out.println("Select operation you want : ");
                System.out.println("[1] - Show all of categories");
                System.out.println("[2] - Create new category");
                System.out.println("[3] - Edit category by id");
                System.out.println("[4] - Delete category by id");
                System.out.println("[0] - Exit");
                choice = input.nextInt();
                switch (choice)
                {
                    case 1: showListOfCategories(strConn, userName,password);break;
                    case 2: insertCategory(strConn, userName,password);break;
                    case 3: editCategory(strConn, userName,password);break;
                    case 4: deleteCategory(strConn, userName,password);break;
                    case 0: break;
                    default:
                        System.out.println("Uncorrect input. Try one more time");
                        break;
                }
            }
            while(choice !=0);
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
        private static void showListOfCategories(String strConn, String userName, String password)
        {

            try(Connection conn = DriverManager.getConnection(strConn, userName, password))
            {
                String selectQuery = "SELECT * FROM categories";
                try(Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(selectQuery))
                {
                    while(resultSet.next()) {
                        int categoryId = resultSet.getInt("Id");
                        String categoryName = resultSet.getString("name");
                        String categoryDescription = resultSet.getString("description");

                        System.out.println("Category ID : " + categoryId);
                        System.out.println("Name : " + categoryName);
                        System.out.println("Description : " + categoryDescription);
                        System.out.println("---------------------");
                    }
                }
                catch(SQLException ex)
                {
                    System.out.println("Error executing query : " + ex.getMessage());
                }
            }
            catch (SQLException ex)
            {
                System.out.println("Connection error : " + ex.getMessage());
            }
        }
        public static void editCategory(String strConn, String userName, String userPassword)
        {
            Scanner input = new Scanner(System.in);

            System.out.println("Enter ID of category you want to edit : ");
            int categoryId = input.nextInt();
            try(Connection conn = DriverManager.getConnection(strConn,userName,userPassword))
            {
                String selectQuery = "SELECT * FROM categories WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                    preparedStatement.setInt(1, categoryId);
                    try (ResultSet resultset = preparedStatement.executeQuery()) {
                        if (resultset.next()) {
                            System.out.println("Current data of catery : ");
                            System.out.println("Name : " + resultset.getString("name"));
                            System.out.println("Description : " + resultset.getString("description"));

                            System.out.println("Enter new data of category : ");
                            System.out.println("Enter new name of category : ");
                            String newName = input.next();
                            System.out.println("Enter new description of category : ");
                            String newDescription = input.next();


                            String updateQuery = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
                            try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, newName);
                                updateStatement.setString(2, newDescription);
                                updateStatement.setInt(3, categoryId);
                                int rowsAffected = updateStatement.executeUpdate();
                                System.out.println("Rows affected : " + rowsAffected);
                                System.out.println("Category was updated successfully");
                            } catch (SQLException ex) {
                                System.out.println("Error with executing of query!" + ex.getMessage());
                            }
                        } else {
                            System.out.println("Category with id " + categoryId + " not found");
                        }
                    }
                }
                catch(SQLException ex)
                {
                    System.out.println("Incorrect ID Category : " + ex.getMessage());
                }
            }
            catch(SQLException ex)
            {
                System.out.println("Connection error : " + ex.getMessage());
            }
        }
        private static void deleteCategory(String strConn, String userName, String userPassword)
        {
            Scanner input = new Scanner(System.in);

            System.out.println("Enter category ID you want to delete : ");
            int categoryId = input.nextInt();

            try(Connection conn = DriverManager.getConnection(strConn, userName, userPassword))
            {
                String deleteQuery = "DELETE FROM categories WHERE ID = ?";
                try(PreparedStatement preparedStatement= conn.prepareStatement(deleteQuery))
                {
                    preparedStatement.setInt(1,categoryId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected >0)
                    {
                        System.out.println("Rows affected : " + rowsAffected);
                        System.out.println("Category was deleted successfully");
                    }
                    else
                    {
                        System.out.println("Category with id " + categoryId +" was not found");
                    }
                }
                catch(Exception ex)
                {
                    System.out.println("Error ith executing your query : " + ex.getMessage());
                }
            }
            catch (SQLException ex)
            {
                System.out.println("Connection error : " + ex.getMessage());
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