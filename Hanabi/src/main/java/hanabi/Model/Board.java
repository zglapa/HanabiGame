//Aleksander Katan
package hanabi.Model;

import java.util.*;

public class Board {
    HashMap<Color, Integer> result;
    Deck deck;
    DiscardPile discardPile;
    ArrayList<PlayerMove> playerMoveHistory;
    ArrayList<Player> players;
    Integer playerAmount;
    int currentLives;
    int currentHints;
    int currentPlayerIndex;
    int handSize;
    int turnsUntilEnd;



    public HashMap<Color, Integer> getResult() { return result; }
    public Deck getDeck() { return deck; }
    public DiscardPile getDiscardPile() { return discardPile; }
    public ArrayList<PlayerMove> getPlayerMoveHistory() { return playerMoveHistory; }
    public ArrayList<Player> getPlayers() { return players; }
    public Integer getPlayerAmount() { return playerAmount; }
    public int getCurrentLives() { return handSize; }
    public int getCurrentHints() { return currentHints; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public int getHandSize() { return handSize; }
    public int getTurnsUntilEnd() { return (turnsUntilEnd < 0) ? -1 : turnsUntilEnd; }


    public Board(int playerAmount, int lives, int hints, int handSize, Deck replacement, boolean shufflePlayers, String... names) {
        //if replacement is null then the deck is ordinary 50 cards, if handsize is 0, it's automatically calculated
        result = new HashMap<>();
        for (Color name : Color.values()) {
            result.put(name, 0);
        }

        if (replacement == null) {
            deck = new Deck();
        } else {
            deck = replacement;
        }

        discardPile = new DiscardPile();

        playerMoveHistory = new ArrayList<>();

        this.playerAmount = playerAmount;
        currentLives = lives;
        currentHints = hints;
        currentPlayerIndex = 0;

        if (handSize <= 0) {
            if (playerAmount < 4)
                handSize = 5;
            else
                handSize = 4;
        }

        turnsUntilEnd = -2137;

        players = new ArrayList<>();
        for (String name : names) {
            LinkedList<Card> hand = new LinkedList<>();

            for (int i = 0; i< handSize; i++) {
                try {
                    hand.add(deck.pop());
                } catch (EmptyDeckException e) {
                    System.out.println(e);
                }
            }
            players.add(new Player(name, hand));
        }

        if (shufflePlayers)
            Collections.shuffle(players);
    }

    public Board(int playerAmount, String... names) {
        this(playerAmount, 3, 8, 0, null, true, names);
    }


    public void action(PlayerMove playerMove) throws GameEndException, NoHintsLeft {
        if (playerMove.getType() == MoveType.HINT) {
            if (currentHints > 0) {
                currentHints--;
                endMove(playerMove);
                return;
            }
            throw new NoHintsLeft();
        }
        if (playerMove.getType() == MoveType.DISCARD) {
            Card cardDiscarded;
            Card topDeck;

            try {
                cardDiscarded = playerMove.getCard();
            } catch (NoCardMoveException e) {
                System.out.println(e);
                throw new RuntimeException("NoCardException");
            }

            try {
                topDeck = deck.pop();
            } catch (EmptyDeckException e) {
                topDeck = null;
                if (turnsUntilEnd < 0)
                    turnsUntilEnd = playerAmount;
            }

            playerMove.getPlayer().playOrDiscard(cardDiscarded, topDeck);

            discardPile.add(cardDiscarded);

            currentHints = (currentHints == 8) ? 8 : currentHints+1;
            endMove(playerMove);
            return;
        }
        if (playerMove.getType() == MoveType.PLAY) {
            Card cardPlayed;
            Card topDeck;

            try {
                cardPlayed = playerMove.getCard();
            } catch (NoCardMoveException e) {
                System.out.println(e);
                throw new RuntimeException("NoCardException");
            }

            try {
                topDeck = deck.pop();
            } catch (EmptyDeckException e) {
                topDeck = null;
                if (turnsUntilEnd < 0)
                    turnsUntilEnd = playerAmount;
            }

            playerMove.getPlayer().playOrDiscard(cardPlayed, topDeck);

            if (result.get(cardPlayed.getColor()) +1 == cardPlayed.getValue()) {
                result.put(cardPlayed.getColor(), cardPlayed.getValue());
                if (cardPlayed.getValue() == 5)
                    currentHints = (currentHints == 8) ? 8 : currentHints+1;
            } else {
                currentLives--;
                discardPile.add(cardPlayed);
            }

            endMove(playerMove);
            return;
        }

    }

    private void endMove(PlayerMove playerMove) throws GameEndException {
        turnsUntilEnd--;
        currentPlayerIndex = (currentPlayerIndex+1)%playerAmount;
        playerMoveHistory.add(playerMove);
        if (hasGameEnded())
            throw new GameEndException();
    }

    //Roch Wojtowicz
    private boolean blocked(Card x) {
        int temp=0;
        for(Card i:discardPile.getDiscardPile())
            if(i.getColor()==x.getColor() && i.getValue()==x.getValue())
                ++temp;
        if(x.getValue()==1)
            return temp==3;
        if(x.getValue()==5)
            return temp==1;
        return temp==2;
    }

    private boolean hasGameEnded() {
        if (turnsUntilEnd == 0) {
            //System.out.println("turns");
            return true;
        }

        if (currentLives == 0) {
            //System.out.println("lives");
            return true;
        }
        for(Color color:Color.values())
            if(!blocked(new Card(color,result.get(color)+1)))
                return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i< playerAmount; i++) {
            ans.append("Player " + i + ": ");
            ans.append(players.get(i));
            ans.append("\n");
        }
        ans.append("Hint amount: ");
        ans.append(currentHints);
        ans.append("\n");
        ans.append("Lives amount: ");
        ans.append(currentLives);
        ans.append("\nBoard: \n");
        for (Color color : Color.values()){
            ans.append(color);
            ans.append(": ");
            ans.append(result.get(color));
            ans.append("\n");
        }
        ans.append("\n");

        return new String(ans);
    }
}

class GameEndException extends Exception {}
class NoHintsLeft extends Exception {}