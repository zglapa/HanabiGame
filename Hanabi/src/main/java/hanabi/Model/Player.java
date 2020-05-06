package hanabi.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private LinkedList<Card> cards;
    Player(String name,LinkedList<Card> startingCards) {
        this.name=name;
        cards=startingCards;
    }

    public LinkedList <Card> getHand() {return cards;}
    public int getCurrentHandSize() {return cards.size();}
    public String getName() { return name;}
    public void playOrDiscard(Card played, Card drawn) {
        cards.remove(played); // it may not work
        if (drawn != null)
            cards.addLast(drawn);
    }
    @Override
    public String toString() {
        return name;// + " " + cards;
    }

    public String getStringData() {
        //System.out.println(name + "\n" + cards.toString());
        return name + "\n" + cards.toString();
    }

    public String getStringBlurredData() {
        StringBuilder ans = new StringBuilder();
        ans.append(name);
        ans.append("\n");
        ans.append("[");
        for (int j = 0; j< cards.size(); j++) {
            ans.append("???");
            if (j != cards.size()-1)
                ans.append(", ");
        }
        ans.append("]");
        return new String(ans);
    }
}