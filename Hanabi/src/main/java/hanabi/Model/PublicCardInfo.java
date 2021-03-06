package hanabi.Model;

import java.io.Serializable;
import java.util.HashMap;

public class PublicCardInfo implements Serializable {
    public HashMap<Integer, CardStatus> publicNumber;
    public HashMap<Color, CardStatus> publicColor;


    public PublicCardInfo() {
        publicNumber = new HashMap<>();
        for (int i = 1; i<= 5; i++)
            publicNumber.put(i, CardStatus.MAYBE);
        publicColor = new HashMap<>();
        for (Color color : Color.values())
            publicColor.put(color, CardStatus.MAYBE);
    }

    public void setColor(Color color, boolean isThisColor) {
        if (isThisColor) {
            for (Color c : Color.values()) {
                if (publicColor.get(c) == CardStatus.YES && c != color) {
                    for (Color col : Color.values())
                        publicColor.put(col, CardStatus.YES);
                    break;
                }
            }
            publicColor.put(color, CardStatus.YES);
        }
        else {
            publicColor.put(color, CardStatus.NO);
            publicColor.put(Color.RAINBOW, CardStatus.NO);
        }
    }

    public void setNumber(Integer number, boolean isThisNumber) {
        System.out.println(isThisNumber);
        if (isThisNumber) {
            for (int i = 1; i<= 5; i++){
                System.out.println(i + " " + number);
                if (i == number)
                    publicNumber.put(i, CardStatus.YES);
                else
                    publicNumber.put(i, CardStatus.NO);
            }
        }
        else
            publicNumber.put(number, CardStatus.NO);
    }

    public Color getPublicColor() throws NoPublicInfoException {
        if (publicColor.get(Color.RAINBOW) == CardStatus.YES)
            return Color.RAINBOW;

        for (Color color : Color.values())
            if (publicColor.get(color) == CardStatus.YES)
                return color;

        throw new NoPublicInfoException();
    }

    public Integer getPublicNumber() throws NoPublicInfoException {
        for (int i = 1; i<= 5; i++)
            if (publicNumber.get(i) == CardStatus.YES)
                return i;
        throw new NoPublicInfoException();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("Information known to all players:").append('\n');
        int value = 0;
        try {
            value = getPublicNumber();
            ans.append("The number is known: ");
            ans.append(value);
        } catch (NoPublicInfoException e) {
            ans.append("Possible numbers:").append('\n');
            for (int i = 1; i<=5; i++) {
                if (publicNumber.get(i) == CardStatus.MAYBE)
                    ans.append(i).append(" ");
            }
        }
        ans.append('\n');
        Color col = null;

        try {
            col = getPublicColor();
        } catch (NoPublicInfoException ignored) {}

        if (publicColor.get(Color.RAINBOW) == CardStatus.NO) {
            if (col != null) {
                ans.append("The color is known: ");
                ans.append(col);
            } else {
                ans.append("Possible colors:").append('\n');
                for (Color c : Color.values()) {
                    if (c == Color.RAINBOW)
                        continue;
                    if (publicColor.get(c) == CardStatus.MAYBE)
                        ans.append(c).append("  ");
                }
            }
        } else {
            if (col == Color.RAINBOW) {
                ans.append("The color is known: ");
                ans.append(col);
            } else {
                ans.append("Possible colors:");
                ans.append('\n');
                boolean known = false;
                for (Color c : Color.values()) {
                    if (publicColor.get(c) == CardStatus.YES)
                        known = true;
                }
                if (known) {
                    for (Color c : Color.values()) {
                        if (publicColor.get(c) == CardStatus.YES)
                            ans.append(c).append("  ");
                    }
                    ans.append(Color.RAINBOW);
                } else {
                    for (Color c : Color.values()) {
                        if (publicColor.get(c) != CardStatus.NO)
                            ans.append(c).append("  ");
                    }
                }
            }
        }

        return new String(ans);
    }
}
