import java.util.Scanner;

public class UserInputUrl {
    private String url;

    protected UserInputUrl() {
        this.url = "";
    }

    protected UserInputUrl(String url) {
        this.url = url;
    }

    protected String getUrl() {
        return url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected void acceptUrl() {
        System.out.println("Enter a URL");
        Scanner input = new Scanner(System.in);
        String tempUrl = input.nextLine();
        if(!tempUrl.startsWith("http://", 0) && !tempUrl.startsWith("https://", 0)){
            if(!tempUrl.startsWith("www.", 0)){
                boolean keepGoing = true;
                while (keepGoing) {
                    System.out.println("Do you want your URL to include \"www.\"? Y/N");
                    int answer = acceptAnswer();
                    if (answer == 1){
                        tempUrl = "www." + tempUrl;
                        keepGoing = false;
                    }
                    else if (answer == -1)
                        keepGoing = false;
                    else
                        System.out.println("That is not a valid response.");
                }
            }
            tempUrl = "http://" + tempUrl;
        }
        this.url = tempUrl;
    }

    protected static int acceptAnswer() {
        Scanner input = new Scanner(System.in);
        String answer = input.next();
        if (answer.equalsIgnoreCase("Y"))
            return 1;
        else if (answer.equalsIgnoreCase("N"))
            return -1;
        else
            return 0;
    }

    protected boolean checkUrl() {
        String[] schemes = {"http", "https"};
        org.apache.commons.validator.routines.UrlValidator validator = new org.apache.commons.validator.routines.UrlValidator(schemes, org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS);
        return validator.isValid(url);
    }
}
