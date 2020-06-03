package hanabi.Model;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private Color color;
    private int value;
    public PublicCardInfo publicCardInfo;
    public boolean rotated;

    public Card(Color color, int value) {
        this.color = color;
        this.value = value;
        publicCardInfo = new PublicCardInfo();
        rotated = false;
    }

    public Color getColor() { return color; }
    public int getValue() { return value; }
    @Override
    public String toString(){
        return color.name() + " " + value;// + '\n' + publicCardInfo + '\n';
    }
    public void setRotated() {rotated = true;}
    public boolean getRotated() {return rotated;}

    @Override
    public int compareTo(Card card) {
        if(this.color.compareTo(card.color) != 0) return this.color.compareTo(card.color);
        return Integer.compare(this.value, card.value);
    }
}
