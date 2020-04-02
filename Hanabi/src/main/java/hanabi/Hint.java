package hanabi;

public final class Hint {
    private Color cardColor;
    private Integer cardValue;
    private Integer playerId;


    Hint(int playerId, Color cardColor) {
        this.cardValue = null;
        this.cardColor = cardColor;
        this.playerId = playerId;
    }

    Hint(int playerId, Integer cardValue) {
        this.cardValue = cardValue;
        this.cardColor = null;
        this.playerId = playerId;
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

    public boolean isHintTypeValue() {
        return cardValue != null;
    }

    public boolean isHintTypeColor() {
        return cardColor != null;
    }
}

class WrongHintTypeException extends Exception {}