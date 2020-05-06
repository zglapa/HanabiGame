package hanabi.Model;

import java.io.Serializable;
import java.util.*;

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
}

class EmptyDeckException extends Exception {}