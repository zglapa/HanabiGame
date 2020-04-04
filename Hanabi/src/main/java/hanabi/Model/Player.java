package hanabi.Model;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private String name;
    private LinkedList<Card> cards;
    Player(String name,LinkedList<Card> startingCards) {
        this.name=name;
        cards=startingCards;
    }

    public LinkedList <Card> getHand() {return cards;}
    public int getCurrentHandSize() {return cards.size();}
    public void playOrDiscard(Card played, Card drawn) {
        cards.remove(played); // it may not work
        if (drawn != null)
            cards.addLast(drawn);
    }
    @Override
    public String toString() {
        return "Player " + name + "Cards: " + cards;
    }
}