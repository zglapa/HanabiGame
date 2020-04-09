package hanabi.Model;

import java.util.LinkedList;

public class DiscardPile {
    LinkedList<Card> pile;
    int pileSize;
    DiscardPile(){
        pile = new LinkedList<>();
        pileSize = 0;
    }
    void add(Card discarded){
        for(int i = 0; i < pileSize;++i){
            Card c = pile.get(i);
            if(discarded.compareTo(c) <= 0){
                pile.add(i,discarded);
                break;
            }
        }
        if(pile.size() == pileSize){
            pile.add(discarded);
        }
        pileSize++;
    }
    LinkedList<Card> getDiscardPile(){
        return pile;
    }
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder();
        Color prefColor=null;
        for (Card card : pile) {
            if (prefColor==null || card.getColor() != prefColor) {
                if(prefColor!=null)
                    ans.append("\n");
                ans.append(card.getColor()).append(": ");
                prefColor=card.getColor();
            }
            ans.append(card.getValue()).append(" ");
        }
        return new String(ans);
    }
}
