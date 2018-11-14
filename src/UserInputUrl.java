import java.util.Scanner;

public class UserInputUrl {
    private static String acceptUrl() {
        System.out.println("Enter a URL");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine();
        if(!url.startsWith("http://", 0) && !url.startsWith("https://", 0)){
            if(!url.startsWith("www.", 0)){
                System.out.println("Do you want your URL to include \"www.\"? Y/N");
                boolean tryAgain = true;
                while (tryAgain) {
                    String answer = input.next();
                    if (answer.equalsIgnoreCase("Y")) {
                        url = "www." + url;
                        tryAgain = false;
                    }
                    else if (answer.equalsIgnoreCase("N")) {
                        tryAgain = false;
                    }
                    else {
                        System.out.println(answer + " is not a valid response.");
                        System.out.println("Do you want your URL to include \"www.\"? Y/N");
                    }
                }
            }
            url = "http://" + url;
        }
        return url;
    }

    private static boolean checkUrl(String url) {
        String[] schemes = {"http", "https"};
        org.apache.commons.validator.routines.UrlValidator validator = new org.apache.commons.validator.routines.UrlValidator(schemes, org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS);
        return validator.isValid(url);
    }

    public static void main(String[] args) {
        String url = acceptUrl();
        if (checkUrl(url)) {
            WordList words = new WordList(url);
            words.printSortedList(25);
        }
        else {
            System.out.println("\"" + url + "\" is not a valid URL.");
        }
    }
}
