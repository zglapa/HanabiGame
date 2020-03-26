package hanabi;

public class Card {
    private Color color;
    private int value;

    Card(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    Color getColor() { return color; }
    int getValue() { return value; }
}
