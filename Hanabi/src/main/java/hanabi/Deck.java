package hanabi;

import java.util.ArrayList;
import java.util.Collections;

public final class Deck {
    private ArrayList <Card> cards;
    private void addAllColors(int x){ //ladnie by bylo miec pentle
        cards.add(new Card(Color.YELLOW,x));
        cards.add(new Card(Color.WHITE,x));
        cards.add(new Card(Color.BLUE,x));
        cards.add(new Card(Color.RED,x));
        cards.add(new Card(Color.GREEN,x));
    }
    //public
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
    boolean isEmpty() {
        return cards.size()==0;
    }
    Card top() throws EmptyDeckException {
        if(isEmpty())
            throw new EmptyDeckException();
        return cards.get(cards.size()-1);
    }
    Card pop() throws EmptyDeckException{
        if(isEmpty())
            throw new EmptyDeckException();
        Card ans=top();
        cards.remove(cards.size()-1)
        return ans;
    }
}

class EmptyDeckException extends Exception {}