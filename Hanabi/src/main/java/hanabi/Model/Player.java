package hanabi.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private LinkedList<Card> cards;
    private boolean handMan;
    Player(String name,LinkedList<Card> startingCards, boolean autoHandManagement) {
        this.name=name;
        cards=startingCards;
        handMan = autoHandManagement;
    }

    public LinkedList <Card> getHand() {return cards;}
    public int getCurrentHandSize() {return cards.size();}
    public String getName() { return name;}
    public void changeName(String name){this.name = name;}
    public void playOrDiscard(Card played, Card drawn) {
        cards.remove(played); // it may not work
        if (!handMan) {
            if (drawn != null)
                cards.addLast(drawn);
        } else {
            int index = cards.size()-1;
            while (index>=0 && cards.get(index).getRotated()) index--;
            cards.add(index+1, drawn);
        }
    }

    public void manageAround (int value) {
        LinkedList<Card> rotated = new LinkedList<>();
        LinkedList<Card> nonRotated = new LinkedList<>();
        LinkedList<Card> rest = new LinkedList<>();

        for (Card card : cards) {
            if (card.getValue() == value) {
                if (card.getRotated()) {
                    rotated.add(card);
                } else {
                    card.setRotated();
                    nonRotated.add(card);
                }
            } else {
                rest.add(card);
            }
        }

        rest.addAll(rotated);
        rest.addAll(nonRotated);
        cards.clear();
        cards.addAll(rest);
    }

    public void manageAround (Color color) {
        LinkedList<Card> rotated = new LinkedList<>();
        LinkedList<Card> nonRotated = new LinkedList<>();
        LinkedList<Card> rest = new LinkedList<>();

        for (Card card : cards) {
            if (card.getColor() == color || card.getColor()==Color.RAINBOW) {
                if (card.getRotated()) {
                    rotated.add(card);
                } else {
                    card.setRotated();
                    nonRotated.add(card);
                }
            } else {
                rest.add(card);
            }
        }

        rest.addAll(rotated);
        rest.addAll(nonRotated);
        cards = rest;
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