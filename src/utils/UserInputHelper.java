package utils;

import java.util.Scanner;

public class UserInputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid option. Please enter a valid option.");
            return -1;
        }
    }

    public static String getStringInput() {
        return scanner.nextLine().trim();
    }

    public static void closeScanner() {
        scanner.close();
    }
}
