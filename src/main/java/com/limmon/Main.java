package com.limmon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Menu options
        String menu = """
                --- Coffee Menu ---
                1. Espresso - 50.0 PHP
                2. Latte - 70.0 PHP
                3. Cappuccino - 65.0 PHP
                4. Mocha - 80.0 PHP
                0. Finish Order""";

        Scanner input = new Scanner(System.in);
        int userOrder, quantity;
        boolean hasOrdered = false;

        //Arrays for menu items and their prices
        String[] menuCoffee = {"Espresso", "Latte", "Cappuccino", "Mocha"};
        double[] price = {50.0, 70.0, 65.0, 80.0};
        double[][] orders = new double[4][2]; // [][0] = quantity, [][1] = subtotal

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
                        break;
                    } else {
                        System.out.println("Invalid choice! Please select a number between 0 and 4.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            //If user presses 0 to finish
            if (userOrder == 0) {
                if (!hasOrdered) {
                    System.out.println("No Coffee Ordered, please try again.");
                    continue;
                }
                break;
            }

            //Set tracker to true once an order is placed
            hasOrdered = true;

            //Loop for entering quantity
            while (true) {
                System.out.print("Enter quantity: ");
                if (input.hasNextInt()) {
                    quantity = input.nextInt();
                    input.nextLine();
                    if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("Quantity must be greater than 0.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            //Update the 2D array with quantity and subtotal
            int index = userOrder - 1;
            orders[index][0] += quantity;
            orders[index][1] = orders[index][0] * price[index]; // Subtotal for that coffee
            System.out.printf("Added %d %s(s) to your order.%n", quantity, menuCoffee[index]);

            //Ask if the user wants to order again
            while (true) {
                System.out.print("Would you like to order again? (1 = Yes, 0 = No): ");
                if (input.hasNextInt()) {
                    int userDecision = input.nextInt();
                    input.nextLine();
                    if (userDecision == 1) {
                        break;
                    } else if (userDecision == 0) {
                        userOrder = 0;
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

        //Calculate totals
        double itemGrandTotal = 0;
        String receipt = "";
        for (int i = 0; i < orders.length; i++) {
            if (orders[i][0] > 0) {
                itemGrandTotal += orders[i][1];
                receipt += String.format("\n%.0f x %s @ %.2f PHP each = %.2f",
                        orders[i][0], menuCoffee[i], price[i], orders[i][1]);
            }
        }
        double VAT = itemGrandTotal * 0.12;
        double grandTotal = itemGrandTotal + VAT;

        //Payment & Change System
        double payment;
        while (true) {
            System.out.printf("\nYour total is: %.2f PHP%n", grandTotal);
            System.out.print("Enter payment amount: ");
            if (input.hasNextDouble()) {
                payment = input.nextDouble();
                input.nextLine();
                if (payment >= grandTotal) {
                    break; //Exit loop if payment is sufficient
                } else {
                    System.out.println("Insufficient payment. Please enter an amount greater than or equal to the total.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        }

        double change = payment - grandTotal;// Calculate change

        //Add the totals to the receipt
        receipt += String.format("""
        
        ------------------------------
        Subtotal: %.2f PHP
        VAT(12%%): %.2f PHP
        Grand Total: %.2f PHP
        Payment: %.2f PHP
        Change: %.2f PHP
        ------------------------------""", itemGrandTotal, VAT, grandTotal, payment, change);

        // Display final receipt
        System.out.println("\nAlright! Your order(s) will be processed soon! :) Here's your receipt!");
        System.out.println("---- Coffee Order Receipt ----");
        System.out.println(receipt);

        // Save receipt to a file
        try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
            writer.write(receipt);
            System.out.println("Receipt saved to CoffeeReceipt.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }

        input.close();
    }
}

