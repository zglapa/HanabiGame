package hanabi;

import java.util.ArrayList;
import java.util.Collections;

public final class Deck {
    private ArrayList <Card> cards;
    private void addAllColors(int x) {
        for(Color color : Color.values())
            cards.add(new Card(color,x));
    }
    
    Deck() {
        cards=new ArrayList <>();
        for(int i=0;i<2;++i)
            for(int j=0;j<4;++j)
                addAllColors(j);
        addAllColors(1);
        addAllColors(5);
    }
    Deck(boolean shuffle) {
        this();
        if(shuffle)
            Collections.shuffle(cards);
    }
    public boolean isEmpty() {
        return cards.size()==0;
    }
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