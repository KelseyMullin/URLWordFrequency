public class Word implements Comparable<Word>{
    private String word;
    private int frequency;

    public Word(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getWord() {
        return word;
    }

    public void increase() {
        this.frequency++;
    }

    @Override
    public int compareTo(Word w) {
        if (this.frequency > w.frequency) {
            return -1;
        } else if (this.frequency < w.frequency) {
            return 1;
        }
        else
            return 0;
    }
}
