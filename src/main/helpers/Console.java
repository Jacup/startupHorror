package main.helpers;

import main.jobs.Project;

import java.util.List;

import static main.Game.TAB;

public class Console {
    public static void printList(List<String> options) {
        for (var option : options) {
            System.out.println(TAB + option);
        }
    }

    public static void printProjects(List<Project> projects, boolean showWorkLeftOnly) {
        for (int i = 1; i <= projects.size(); i++)
            System.out.println(TAB + i + ". " + projects.get(i - 1).toString(showWorkLeftOnly));
        System.out.println(TAB + 0 + ". Go back");
    }

    public static void printProjects(List<Project> projects) {
        printProjects(projects, false);
    }

}
