//in this project i had make car rental system using the pillar of oops and this project make my oops concept strong
package carRentalSystem;

import java.util.ArrayList;
import java.util.Scanner;

class car {

    private String carId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;

    public car(String carId, String brand, String model, double rentPerDay, boolean isAvailable) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.isAvailable = true;
        this.rentPerDay = rentPerDay;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Double rentPrice(int rentalDays) {
        return rentPerDay * rentalDays;
    }

    public void rentedCar() {
        isAvailable = false;
    }

    public void returnedCar() {
        isAvailable = true;
    }
}

class customer {

    private String name;
    private String id;
    private int mobile;

    public customer(String name, String id, int mobile) {
        this.name = name;
        this.id = id;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getMobile() {
        return mobile;
    }
}

class rental {

    private car car;
    private customer customer;
    private int days;

    public rental(car car, customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public car getCar() {
        return car;
    }

    public customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class carRentalSystem {

    private ArrayList<car> cars;
    private ArrayList<customer> customers;
    private ArrayList<rental> rentals;

    public carRentalSystem() {
        cars = new ArrayList<car>();
        customers = new ArrayList<customer>();
        rentals = new ArrayList<rental>();
    }

    public void addCar(car car) {
        cars.add(car);
    }

    public void addCustomers(customer customer) {
        customers.add(customer);
    }

    public void rentCar(car car, customer customer, int days) {
        if (car.isAvailable()) {
            car.rentedCar();
            rentals.add(new rental(car, customer, days));
        } else {
            System.out.println("car is not available");
        }
    }

    public void returnCar(car car) {
        car.returnedCar();
        rental rentalToRemove = null;
        for (rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("car returned successfully");
        } else {
            System.out.println("car was not rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=== WELCOME TO CAR RENTAL SYSTEM ==");
            System.out.println("1. rent a car");
            System.out.println("2. return a car");
            System.out.println("3. exit");
            System.out.println("enter your choice:");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.println("===== RENT A CAR ====");
                System.out.println("enter your name");
//                String ccustomerName=sc.nextLine();
                String customerName = sc.next();
                System.out.println("enter your mobile number");
                int mobileNo = sc.nextInt();
                System.out.println("available cars are:");
                for (car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " -  " + car.getBrand() + "  -  " + car.getModel());
                    }
                }
                System.out.println("enter the car id which you want to buy");
                String carIdd = sc.next();
                System.out.println("enter the number of rental days you want the car");
                int rentalDays = sc.nextInt();
                customer newCustomer = new customer(customerName, "CUS" + (customers.size() + 1), mobileNo);
                addCustomers(newCustomer);
                car selectedCar = null;
                for (car car : cars) {
                    if (car.getCarId().equals(carIdd) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.rentPrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.print("Total Price:" + totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.next();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }

            } else if (choice == 2) {
                System.out.println("=====   RETURN A CAR   =====");
                System.out.println("enter the car id which you want to return");
                String carIdd = sc.next();
                car carToReturn = null;
                for (car car : cars) {
                    if (car.getCarId().equals(carIdd) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    customer customer = null;
                    for (rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class main {

    public static void main(String[] args) {
        carRentalSystem rentalSystem = new carRentalSystem();
        car car1 = new car("car01", "zuzuki", "2022", 1000, true);
        car car2 = new car("car02", "tuziuzi", "2024", 2000, true);
        car car3 = new car("car03", "vuzuzi", "2023", 1900, true);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();
    }
}
