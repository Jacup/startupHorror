package main.helpers;

import java.util.Scanner;

public class UserActions {
    public static void pressEnterKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
