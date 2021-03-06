import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserInputUrl inputUrl = new UserInputUrl();
        boolean runAgain = true;
        while (runAgain) {
            inputUrl.acceptUrl();
            if (inputUrl.checkUrl()) {
                WordList words = new WordList(inputUrl.getUrl());
                words.printSortedList(25);
            }
            else {
                System.out.println("\"" + inputUrl.getUrl() + "\" is not a valid URL.");
            }
            System.out.println("Do you want to try another URL? Y/N");
            Scanner input = new Scanner(System.in);
            String answer = input.next();
            if (!answer.equalsIgnoreCase("Y"))
                runAgain = false;
        }
    }
}
