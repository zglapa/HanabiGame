//Aleksander Katan
package hanabi.Model;

import java.util.ArrayList;

public final class Hint {
    private Color cardColor;
    private Integer cardValue;
    private Player hinted;
    private ArrayList<Card> hand;


    public Hint(Player hinted, Color cardColor) {
        this.hinted = hinted;
        this.cardColor = cardColor;
        hand = new ArrayList<>();
        hand.addAll(hinted.getHand());
    }

    public Hint(Player hinted, Integer cardValue) {
        this.hinted = hinted;
        this.cardValue = cardValue;
        this.cardColor = null;
        hand = new ArrayList<>();
        hand.addAll(hinted.getHand());
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
        ans.append(" ");
        ans.append(hand);

        return new String(ans);
    }

    public String toStringHidden() {
        StringBuilder ans = new StringBuilder("Hint ");
        if (isHintTypeColor())
            ans.append(cardColor.name());
        else
            ans.append(cardValue);
        ans.append(" to ");
        ans.append(hinted.getName());
        ans.append(" ");

        if (isHintTypeColor()) {
            ans.append("[");
            for (int i = 0; i< hand.size(); i++) {
                if (hand.get(i).getColor() == cardColor ||  hand.get(i).getColor() == Color.RAINBOW)
                    ans.append(cardColor.toString());
                else
                    ans.append("?");
                if (i!=hand.size()-1)
                    ans.append(", ");
            }
            ans.append("]");
        }  else {
            ans.append("[");
            for (int i = 0; i< hand.size(); i++) {
                if (hand.get(i).getValue() == cardValue)
                    ans.append(cardValue.toString());
                else
                    ans.append("?");
                if (i!=hand.size()-1)
                    ans.append(", ");
            }
            ans.append("]");
        }

        return new String(ans);
    }
}

class WrongHintTypeException extends Exception {}