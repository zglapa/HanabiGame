package hanabi.Model;

import javafx.scene.control.Label;

import java.io.Serializable;
import java.util.*;

import static java.lang.Integer.parseInt;

public final class Deck implements Serializable {
    private ArrayList <Card> cards;

    private void addAllColors(int x, boolean rainbow) {
        for(Color color : Color.values()) {
            if (!rainbow)
                if (color == Color.RAINBOW)
                    continue;
            cards.add(new Card(color, x));
        }
    }

    public Deck(Collection<Card> col, boolean shuffle) {
        cards = new ArrayList<>();

        for (Card card : col) {
            cards.add(card);
        }

        if (shuffle)
            Collections.shuffle(cards);
    }

    public Deck(boolean shuffle, boolean rainbow, boolean rainbowFriendlyShuffle) {
        cards=new ArrayList <>();
        if (rainbow) {
            for (int i = 1; i<= 5; i++)
                addAllColors(i, true);
            for (int i = 1; i<= 4; i++)
                addAllColors(i, false);
            addAllColors(1, false);
        } else {
            for (int i = 0; i < 2; ++i)
                for (int j = 1; j <= 4; ++j)
                    addAllColors(j, false);
            addAllColors(1, false);
            addAllColors(5, false);
            for (Card card : cards)
                card.publicCardInfo.setColor(Color.RAINBOW, false);
        }

        if(shuffle)
            Collections.shuffle(cards);

        if(shuffle && rainbowFriendlyShuffle) {
            for (int i = 0; i< 50; i++) {
                boolean safe = true;
                for (int j = 0; j< 4; j++)
                    if (cards.get(j).getColor()==Color.RAINBOW)
                        safe = false;
                if (safe)
                    break;
                Collections.shuffle(cards);
            }
        }
    }

    public Deck(String s) { //smol deck
        cards = new ArrayList<>();
        for (int i = 0; i < 1; ++i)
            for (int j = 1; j <= 3; ++j)
                addAllColors(j, false);
        for (Card card : cards)
            card.publicCardInfo.setColor(Color.RAINBOW, false);
        Collections.shuffle(cards);
    }

    public Deck() {
        this(true, false, false);
    }

    public HashSet<Color> getColors() {
        HashSet<Color> answer = new HashSet<>();
        for (Card card : cards)
            answer.add(card.getColor());
        return answer;
    }

    public int getSize() {return cards.size();}
    public boolean isEmpty() {
        return cards.size()==0;
    }

    public ArrayList<Card> getCards() { return cards; }

    public Card top() throws EmptyDeckException {
        if(isEmpty())
            throw new EmptyDeckException();
        return cards.get(cards.size()-1);
    }
    public Card pop() throws EmptyDeckException{
        if(isEmpty())
            throw new EmptyDeckException();
        Card ans=top();
        cards.remove(cards.size()-1);
        return ans;
    }

    public static Deck createDeck(ArrayList<ArrayList<Label>> labels, boolean rainbows, int minimum) throws NotEnoughCardsException {
        int ans = 0;
        for (int i = 0; i< 5; i++) {
            for (int j = 0; j< (rainbows?6:5); j++) {
                ans += parseInt(labels.get(i).get(j).getText());
            }
        }
        if (ans<minimum)
            throw new NotEnoughCardsException();

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i< 5; i++) {
            for (int j = 0; j< (rainbows?6:5); j++) {
                Color col = Color.getReverseOrdinal(j);
                int num = i+1;

                for (int k = parseInt(labels.get(i).get(j).getText()); k>0; k--) {
                    cards.add(new Card(col, num));
                }
            }
        }
        return new Deck(cards, true);
    }

}

class EmptyDeckException extends Exception {}