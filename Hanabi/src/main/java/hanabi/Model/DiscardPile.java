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
        return pile.toString();
    }
}
