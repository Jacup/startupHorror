package helpers.console;

import gameplay.Game;

import java.util.ArrayList;
import java.util.List;

public class Console {
    public static void printList(List<String> options) {
        for (var option : options) {
            System.out.println(Game.TAB + option);
        }
    }

    public void printDayActivities() {
        System.out.println("What would you like to do today?");
        ArrayList<String> activities = new ArrayList<>(List.of("1. Sign a contract for a new project", "2. Try to find a new client", "3. Go programming!", "4. Go testing!", "5. Return the finished project to the client", "6. HR operations", "7. Fire an employee", "8. Go to tax office", "9. Go to sleep", "0. Exit game"));

        Console.printList(activities);
    }

}
