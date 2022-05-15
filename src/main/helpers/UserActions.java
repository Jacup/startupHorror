package main.helpers;

import java.util.Scanner;

public class UserActions {

    private static final Scanner s = new Scanner(System.in);

    public static void pressEnterKeyToContinue() {
        System.out.println("Press Enter key to continue...");

        s.nextLine();
    }

    public static byte getUserInputByte() {
        return getUserInputByte(127);
    }


    public static byte getUserInputByte(int maxValue) {
        int minValue = 0;
        boolean inputWasGood = false;
        byte value = 0;

        while (!inputWasGood) {
            try {
                System.out.print("Your choice: ");
                value = s.nextByte();
                inputWasGood = value >= minValue && value <= maxValue;
                if (!inputWasGood)
                    System.out.println("Your input is invalid. Choose value between "
                            + minValue + " and " + maxValue + "\n");
            } catch (Exception e) {
                System.out.println("Your input is invalid.\n");
            } finally {
                s.nextLine();
            }
        }
        return value;
    }

}
