public class Word implements Comparable<Word>{
    private String word;
    private int frequency;

    protected Word(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }

    protected int getFrequency() {
        return frequency;
    }

    protected String getWord() {
        return word;
    }

    protected void increase() {
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
