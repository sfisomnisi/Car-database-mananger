/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dbtest;

/**
 *
 * @author Student
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.DriverManager;

public class DBTest {
    
    public static void main(String[] args) {
        // TODO code application logic here
        String dbUrl = "jdbc:derby://localhost:1527/CarDatabase";
        Connection connect = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        Scanner sc = new Scanner(System.in);
        
        try {
            connect = DriverManager.getConnection(dbUrl);
            stmt  = connect.createStatement();
            
            boolean running = true;
            
            while(running){
                System.out.println("Menu");
                System.out.println("1. Display all cars makes");
                System.out.println("2. Find oldest model");
                System.out.println("3. Total value of all cars");
                System.out.println("4. Most expensive car");
                System.out.println("5. Least expensive car");
                System.out.println("6. Exit");
                System.out.println("Choose an option");
                int option = sc.nextInt();
                
                rs = stmt.executeQuery("SELECT * FROM CARS");
                
                switch(option){
                    case 1:
                        displayAllCarMakes(rs);
                        break;
                    case 2:
                        findOldestModel(rs);
                        break;
                    case 3:
                        calculateTotatlValue(rs);
                        break;
                    case 4:
                        findMostExpensiveCar(rs);
                        break;
                    case 5:
                        findLeastexpensiveCar(rs);
                        break;
                    case 6:
                        running = false;
                        System.out.println("BYE");;
                        break;
                    default: 
                        System.out.println("Invalid option. please choose a valid option");
                }
                
            }    
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)rs.close();
                if(stmt != null)stmt.close();
                if(connect != null)connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
    
    private static void displayAllCarMakes(ResultSet rs) throws SQLException{
        System.out.println("List of Car Makes: ");
        
        while(rs.next()){
            String make = rs.getString("make");
            System.out.println(make);
        }
    }
    
    private static void findOldestModel(ResultSet rs) throws SQLException{
        int oldestYear = Integer.MAX_VALUE;
        String oldestCar = "";
        
        while(rs.next()){
            int year = rs.getInt("manufacture_year");
            if(year < oldestYear) {
                oldestYear = year;
                oldestCar = rs.getString("make") + " " + rs.getString("model");
            }
        }
        System.out.println("Oldest car: " + oldestCar + " (" + oldestYear + ")");
    }
    
    private static void calculateTotatlValue(ResultSet rs) throws SQLException{
        double totalValue = 0.0;
        
        while(rs.next()){
            totalValue+= rs.getDouble("price");
        }
        System.out.println("Total value of all cars: R" + totalValue);
    }
    
    private static void findMostExpensiveCar(ResultSet rs) throws SQLException{
        double maxPrice = Double.MIN_VALUE;
        String expensiveCar = "";
        
        while(rs.next()){
            double price = rs.getDouble("price");
            if(price > maxPrice){
                maxPrice = price;
                expensiveCar = rs.getString("make") + " " + rs.getString("model");
            }
            System.out.println("Most Expensive Car: " + expensiveCar + " (R " + maxPrice + ")");
        }
    }
    
    private static void findLeastexpensiveCar(ResultSet rs) throws SQLException{
       double minPrice = Double.MAX_VALUE;
       String cheapCar = "";
       
       while(rs.next()){
           double price = rs.getDouble("price");
           if(price > minPrice){
               minPrice = price;
               cheapCar = rs.getString("make" + " " + rs.getString("model"));
           }
       }
        System.out.println("Least expensive Car: " + cheapCar + " (R" + minPrice + ")");
    }
}
