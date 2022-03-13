package application;

import application.Classes.*; 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Data
{
    private final ArrayList<Car> cars;
    private final ArrayList<Customer> customers;
    private final ArrayList<Employee> employees;
    private final ArrayList<Manager> managers;
    private Admin admin;
    private final ArrayList<Order> orders;
    private final ArrayList<BlacklistedUser> blacklist;
    private Scanner scanner;

    public Data()
    {
        this.cars = new ArrayList<Car>();
        this.customers = new ArrayList<Customer>();
        this.employees = new ArrayList<Employee>();
        this.managers = new ArrayList<Manager>();
        this.orders = new ArrayList<Order>();
        this.blacklist = new ArrayList<BlacklistedUser>();
        scanner = null;
    }

    public void setupCars() {
    	String path  = "src/cars.txt";
        FileInputStream carsFile = null;
        try {
            carsFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (carsFile != null) {
            scanner = new Scanner(carsFile);

            while (scanner.hasNextLine()) {
                cars.add(new Car(scanner.next(), scanner.next(), scanner.nextInt(),
                		scanner.nextDouble(), scanner.next(), scanner.nextDouble()));
            }
        }
        scanner.close();
    }

    public void setupCustomers() {
    	String path  = "src/customers.txt";
        FileInputStream carsFile = null;
        try {
            carsFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (carsFile != null) {
            scanner = new Scanner(carsFile);
            
            while (scanner.hasNextLine()) {
                customers.add(new Customer(scanner.next(), scanner.next(), scanner.next(), Arrays.asList(scanner.next())));
            }
        }
        scanner.close();
    }

    public void setupOrders() {
        String path = "src/orders.txt";
        FileInputStream ordersFile = null;
        try {
            ordersFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {

        }
        if (ordersFile != null) {
            scanner = new Scanner(ordersFile);

            while (scanner.hasNextLine()) {
                orders.add(new Order(scanner.next(), scanner.nextInt(), scanner.next(), scanner.nextDouble(), scanner.next()));
            }
        }
        scanner.close();
    }
    
    public void setupEmployees() {
        String path = "src/employees.txt";
        FileInputStream employeesFile = null;
        try {
            employeesFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (employeesFile != null) {
            scanner = new Scanner(employeesFile);

            while (scanner.hasNextLine()) {
                employees.add(new Employee(scanner.next(), scanner.next(), scanner.next(), scanner.next()));
            }
        }
        scanner.close();
    }
    
    public void setupManagers() {
        String path = "src/managers.txt";
        FileInputStream managersFile = null;
        try {
            managersFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (managersFile != null) {
            scanner = new Scanner(managersFile);

            while (scanner.hasNextLine()) {
                managers.add(new Manager(scanner.next(), scanner.next(), scanner.next(), Arrays.asList(scanner.next())));
            }
        }
        scanner.close();
    }
    
    public void setupBlacklist() {
        String path = "src/blacklist.txt";
        FileInputStream blacklistFile = null;
        try {
            blacklistFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (blacklistFile != null) {
            scanner = new Scanner(blacklistFile);

            while (scanner.hasNextLine()) {
                blacklist.add(new BlacklistedUser(scanner.next(), scanner.next()));
            }
        }
        scanner.close();
    }
    
    public void setupAdmin() {
        String path = "src/admin.txt";
        FileInputStream adminFile = null;
        try {
            adminFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (adminFile != null) {
            scanner = new Scanner(adminFile);

            while (scanner.hasNextLine()) {
                this.admin = new Admin(scanner.next(), scanner.next(), scanner.next());
            }
        }
        scanner.close();
    }
    
   
    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }
    
    public Admin getAdmin() {
    	return admin;
    }
    
    public ArrayList<BlacklistedUser> getBlacklist() {
    	return blacklist;
    }

}

