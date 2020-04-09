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

        for (Color color: Color.values()) {

            StringBuilder temp = new StringBuilder();
            for (Card card : pile) {
                if (card.getColor() == color)
                    temp.append(card).append(" ");
            }

            if (temp.length() > 0)
                ans.append(temp).append('\n');
        }
        return new String(ans);
    }
}
