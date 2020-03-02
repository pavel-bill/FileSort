public class Line implements Comparable<Line>{
    public String text;
    public int fileNumber;

    @Override
    public int compareTo(Line line) {
        return this.text.compareTo(line.text);
    }
}
