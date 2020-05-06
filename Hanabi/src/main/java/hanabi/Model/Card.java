package hanabi.Model;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private Color color;
    private int value;

    Card(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() { return color; }
    public int getValue() { return value; }
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
