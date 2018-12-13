

public class EndingPair {

    private final String from;
    private final String to;

    private final int fromLength;
    private final int toLength;

    public EndingPair(String from, String to) {
        this.from = from;
        this.to = to;
        fromLength = from.length();
        toLength = to.length();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getFromLength() {
        return fromLength;
    }
    
    public int getToLength() {
        return toLength;
    }

}
