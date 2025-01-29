package com.limmon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

        String[] menuCoffee = {"Espresso", "Latte", "Cappuccino", "Mocha"};
        double[] price = {50.0, 70.0, 65.0, 80.0};
        int[] coffeeQuantities = new int[4];

        while (true) {
            System.out.println(menu);

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

            if (userOrder == 0) {
                if (!hasOrdered) {
                    System.out.println("No Coffee Ordered, please try again.");
                    continue;
                }
                break;
            }

            hasOrdered = true;

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

            coffeeQuantities[userOrder - 1] += quantity;
            System.out.printf("Added %d %s(s) to your order.%n", quantity, menuCoffee[userOrder - 1]);

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

            if (userOrder == 0) {
                break;
            }
        }

        input.close();

        System.out.println("\nAlright! Your order(s) will be processed soon! :) Here's your receipt!");
        System.out.println("---- Coffee Order Receipt ----");

        double itemGrandTotal = 0;
        String receipt = "";
        for (int i = 0; i < coffeeQuantities.length; i++) {
            if (coffeeQuantities[i] > 0) {
                double subtotal = coffeeQuantities[i] * price[i];
                itemGrandTotal += subtotal;
                receipt += String.format("\n%d x %s @ %.2f PHP each = %.2f", coffeeQuantities[i], menuCoffee[i], price[i], subtotal);
            }
        }
        double VAT = itemGrandTotal * 0.12;
        double grandTotal = itemGrandTotal + VAT;

        receipt += String.format("""
        
        ------------------------------
        Subtotal: %.2f PHP
        VAT(12%%): %.2f PHP
        Grand Total: %.2f PHP
        ------------------------------""", itemGrandTotal, VAT, grandTotal);

        System.out.println(receipt);

        try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
            writer.write(receipt);
            System.out.println("Receipt saved to CoffeeReceipt.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}
