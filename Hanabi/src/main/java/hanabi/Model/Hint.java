//Aleksander Katan
package hanabi.Model;

public final class Hint {
    private Color cardColor;
    private Integer cardValue;
    private Player hinted;


    public Hint(Player hinted, Color cardColor) {
        this.hinted = hinted;
        this.cardColor = cardColor;
    }

    public Hint(Player hinted, Integer cardValue) {
        this.hinted = hinted;
        this.cardValue = cardValue;
        this.cardColor = null;
    }

    public Color getCardColor() throws WrongHintTypeException {
        if (cardColor == null)
            throw new WrongHintTypeException();
        return cardColor;
    }

    public Integer getCardValue() throws WrongHintTypeException {
        if (cardValue == null)
            throw new WrongHintTypeException();
        return cardValue;
    }

    public Player getHinted() {
        return hinted;
    }

    public boolean isHintTypeValue() {
        return cardValue != null;
    }

    public boolean isHintTypeColor() {
        return cardColor != null;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("Hint ");
        if (isHintTypeColor())
            ans.append(cardColor.name());
        else
            ans.append(cardValue);
        ans.append(" to ");
        ans.append(hinted.getName());

        return new String(ans);
    }
}

class WrongHintTypeException extends Exception {}