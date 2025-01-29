package com.limmon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Menu options
        String menu = """
                --- Coffee Menu ---
                1. Espresso - 50.0 PHP
                2. Latte - 70.0 PHP
                3. Cappuccino - 65.0 PHP
                4. Mocha - 80.0 PHP
                0. Finish Order""";

        Scanner input = new Scanner(System.in);
        int userOrder, quantity;
        boolean hasOrdered = false; // tracker if any coffee was ordered

        //Arrays for menu items and their prices
        String[] menuCoffee = {"Espresso", "Latte", "Cappuccino", "Mocha"};
        double[] price = {50.0, 70.0, 65.0, 80.0};
        int[] coffeeQuantities = new int[4]; //To store quantity of each coffee ordered

        //Main loop to take orders
        while (true) {
            System.out.println(menu); // Display the menu

            //Loop for selecting a coffee
            while (true) {
                System.out.print("Choose your coffee (1-4, or 0 to finish): ");
                if (input.hasNextInt()) {
                    userOrder = input.nextInt();
                    input.nextLine();
                    if (userOrder >= 0 && userOrder <= 4) {
                        break; //Exit loop if valid input
                    } else {
                        System.out.println("Invalid choice! Please select a number between 0 and 4.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            // If user presses 0 to finish
            if (userOrder == 0) {
                if (!hasOrdered) {
                    System.out.println("No Coffee Ordered, please try again."); // If no order was placed
                    continue;
                }
                break; // Exit main order loop if finished
            }

            // Set tracker to true once an order is placed
            hasOrdered = true;

            //Loop for entering quantity
            while (true) {
                System.out.print("Enter quantity: ");
                if (input.hasNextInt()) {
                    quantity = input.nextInt();
                    input.nextLine();
                    if (quantity > 0) {
                        break; //Exit loop if quantity is valid
                    } else {
                        System.out.println("Quantity must be greater than 0.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            //Update the quantity for the selected coffee
            coffeeQuantities[userOrder - 1] += quantity;
            System.out.printf("Added %d %s(s) to your order.%n", quantity, menuCoffee[userOrder - 1]);

            //Ask if the user wants to order again
            while (true) {
                System.out.print("Would you like to order again? (1 = Yes, 0 = No): ");
                if (input.hasNextInt()) {
                    int userDecision = input.nextInt();
                    input.nextLine();
                    if (userDecision == 1) {
                        break; //Continue ordering
                    } else if (userDecision == 0) {
                        userOrder = 0; //Set to 0 to finish
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter 1 to continue ordering or 0 to finish.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter 1 to continue ordering or 0 to finish.");
                    input.nextLine();
                }
            }

            //Exit if user chooses to finish
            if (userOrder == 0) {
                break;
            }
        }

        input.close();

        //Display final order message
        System.out.println("\nAlright! Your order(s) will be processed soon! :) Here's your receipt!");
        System.out.println("---- Coffee Order Receipt ----");

        //Calculate and format receipt
        double itemGrandTotal = 0;
        String receipt = "";
        for (int i = 0; i < coffeeQuantities.length; i++) {
            if (coffeeQuantities[i] > 0) {
                double subtotal = coffeeQuantities[i] * price[i]; //Calculate subtotal for each coffee type
                itemGrandTotal += subtotal; //Add to grand total
                receipt += String.format("\n%d x %s @ %.2f PHP each = %.2f", coffeeQuantities[i], menuCoffee[i], price[i], subtotal);
            }
        }
        double VAT = itemGrandTotal * 0.12; //Calculate VAT (12%)
        double grandTotal = itemGrandTotal + VAT; //Calculate grand total

        //Add the totals to the receipt
        receipt += String.format("""
        
        ------------------------------
        Subtotal: %.2f PHP
        VAT(12%%): %.2f PHP
        Grand Total: %.2f PHP
        ------------------------------""", itemGrandTotal, VAT, grandTotal);

        System.out.println(receipt); //Display receipt

        //Try to save receipt to a file
        try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
            writer.write(receipt); //Save the receipt
            System.out.println("Receipt saved to CoffeeReceipt.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage()); //Handle errors while saving
        }
    }
}
