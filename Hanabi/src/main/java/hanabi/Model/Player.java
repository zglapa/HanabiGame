package hanabi.Model;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private String name;
    private int maxHandSize;
    private LinkedList<Card> cards;
    Player(String name,List<Card> startingCards) {
        this.name=name;
        maxHandSize=startingCards.size();
        cards=new LinkedList<>(startingCards);
    }

    public int getCurrentHandSize() {return cards.size();}
    public void playOrDiscard(Card played, Card drawn) {
        cards.remove(played); // it may not work
        cards.addLast(drawn);
    }
    public LinkedList <Card> getHand() {return cards;}

    @Override
    public String toString() {
        return "Player " + name;
    }
}