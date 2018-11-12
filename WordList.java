import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class WordList {
    private String url;
    private ArrayList<Word> wordList;

    public WordList(String u) {
        this.url = u;
        this.wordList = new ArrayList<Word>();
    }

    private void addWordsFromURL() {
        try{
            String[] currentLine;
            boolean match;
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3163.100 Safari/537.36")
                    .referrer("https://www.google.com")
                    .get();

            Elements elements = doc.select("body");
            if(!elements.hasText()){
                System.out.println("This URL contains no text.  You may not have permission to access it.");
            }
            else {
                for (Element textChunk : elements) {
                    String str = textChunk.text();
                    currentLine = str.split("[\\.\\s+\\r\\n]");
                    removePunctuation(currentLine);
                    for (int i = 0; i < currentLine.length; i++) {
                        if (isWord(currentLine[i])) {            //skip empty strings and words with no alphabet characters
                            match = false;
                            String wordInList = "";
                            ListIterator<Word> iter = wordList.listIterator();
                            while (iter.hasNext() && !match) {
                                Word currentWord = iter.next();
                                wordInList = currentWord.getWord();
                                if (currentLine[i].equalsIgnoreCase(wordInList)) {
                                    match = true;                                   //if current word is already in list,
                                    currentWord.increase();                         // increase frequency of that word
                                }
                            }
                            if (!match) {
                                Word newWord = new Word(currentLine[i], 1);
                                this.wordList.add(newWord);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ex){
            System.out.println("Could not connect to \"" + this.url + "\".  Make sure you entered the correct URL.");
            ex.printStackTrace();
        }
    }

    private static void removePunctuation(String[] currentLine) {
        String rgx1 = "\\W*\\w+['.!\\-]\\w+\\W*";                               //ex: roly-poly! or "roly-poly"
        String rgx2 = "\\W*[~!@#$%^&*()_+=\\[\\]\\\\{}|;':\",./<>?\\-\\d]+\\W*"; //ex. hello!! or 1234
        String rgx3 = "[~!@#$%^&*()_+=\\[\\]\\\\{}|;':\",./<>?\\-\\d]";         //single punctuation character or digit
        for(int i = 0; i < currentLine.length; i++) {
            if (Pattern.matches(rgx1, currentLine[i])) {        //if punctuation is part of the word:
                char[] array1 = currentLine[i].toCharArray();
                char[] array2;
                int beg = 0;
                String c = Character.toString(array1[beg]);
                while(Pattern.matches(rgx3, c) && (beg < array1.length - 2)) { //if punctuation is also at the beginning:
                    beg++;
                    array2 = Arrays.copyOfRange(array1, beg, array1.length);
                    c = Character.toString(array1[beg]);
                    currentLine[i] = new String(array2);
                }
                array1 = currentLine[i].toCharArray();
                int end = array1.length - 1;
                c = Character.toString(array1[end]);
                while(Pattern.matches(rgx3, c) && end > 1) {         //if punctuation is also at the end:
                    array2 = Arrays.copyOfRange(array1, 0, end);
                    end--;
                    c = Character.toString(array1[end]);
                    currentLine[i] = new String(array2);
                }
            }
            else
                currentLine[i] = currentLine[i].replaceAll(rgx2, ""); //remove #s and punctuation at beginnings
        }                                                                 //and ends of words, and with spaces both sides
    }

    private static boolean isWord(String s) {                   //optional: use this method in line 34 instead of isEmpty()
        if (s.isEmpty() || !s.matches(".*[a-zA-Z]+.*")) {   // in order to exclude words with no characters
            return false;                                        // from Roman alphabet
        }
        return true;
    }

    private void print(int num) {
        String w;
        int f;
        System.out.printf("%-30.30s  %-20.20s%n", "   WORDS", "# OF OCCURRENCES");
        ListIterator<Word> iter = wordList.listIterator();
        int i = 1;
        String s = "";
        while (iter.hasNext() && i <= num) {
            Word currentWord = iter.next();
            w = currentWord.getWord();
            f = currentWord.getFrequency();
            s = Integer.toString(i);
            s = s.concat(": " + w);
            System.out.printf("%-30.30s  %-20.20s%n", s, f);
            i++;
        }
    }

    public static boolean checkURL(String u) {
        String[] schemes = {"http", "https"};
        UrlValidator validator = new UrlValidator(schemes);
        if (!validator.isValid(u)) {
            System.out.println("Please enter a valid URL.");
            return false;
        }
        return true;
    }

    public void printSortedList(int num){
        if(checkURL(this.url)) {
            this.addWordsFromURL();
            if(!this.wordList.isEmpty()) {
                Collections.sort(wordList);
                this.print(num);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter a URL");
        Scanner input = new Scanner(System.in);
        String URL = input.nextLine();
        if (checkURL(URL)) {
            WordList words = new WordList(URL);
            words.printSortedList(25);
        }
        else
            System.out.println("\"" + URL + "\" is not a valid URL.");
    }
}
