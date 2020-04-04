package hanabi.Model;

public class Card implements Comparable<Card>{
    private Color color;
    private int value;

    Card(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    Color getColor() { return color; }
    int getValue() { return value; }
    @Override
    public String toString(){
        return color.name() + " " + value;
    }

    @Override
    public int compareTo(Card card) {
        if(this.color.compareTo(card.color) != 0) return this.color.compareTo(card.color);
        return Integer.compare(this.value, card.value);
    }
}
