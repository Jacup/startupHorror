package main.helpers;

import java.util.List;

import static main.Game.TAB;

public class Console {
    public static void printList(List<String> options) {
        for (var option : options) {
            System.out.println(TAB + option);
        }
    }
}
