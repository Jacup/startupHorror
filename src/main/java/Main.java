import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        welcomeMsg();

        System.out.println("            --- Press enter to start the game ---");
        var scanner = new Scanner(System.in);
        scanner.nextLine();

        var game = new main.Game();
        game.setup();
        game.play();
     }

    // todo: replace raw strings with generator
    private static void welcomeMsg() {
        System.out.println("|---------------------------------------------------------------|");
        System.out.println("|                      - Startup Horror -                       |");
        System.out.println("|                                                               |");
        System.out.println("|           Play the role of the owner of a software            |");
        System.out.println("|                house and develop the company.                 |");
        System.out.println("|                                                               |");
        System.out.println("|          Your goal is to get 3 full payments for the          |");
        System.out.println("|                     completed projects.                       |");
        System.out.println("|---------------------------------------------------------------|");
    }
}
