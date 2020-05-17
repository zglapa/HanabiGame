//Aleksander Katan
package hanabi.Model;

import hanabi.Controller.AlertBox;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {
    HashMap<Color, Integer> result;
    Deck deck;
    HashSet<Color> inGameColors;
    DiscardPile discardPile;
    ArrayList<PlayerMove> playerMoveHistory;
    ArrayList<Player> players;
    Integer playerAmount;
    int currentLives;
    int currentHints;
    int maxHints;
    int currentPlayerIndex;
    int handSize;
    int turnsUntilEnd;
    int rewardedForPlaying;
    private static ArrayList<String> randomNames = null;



    public HashMap<Color, Integer> getResult() { return result; }
    public Deck getDeck() { return deck; }
    public HashSet<Color> getInGameColors() { return inGameColors; }
    public DiscardPile getDiscardPile() { return discardPile; }
    public ArrayList<PlayerMove> getPlayerMoveHistory() { return playerMoveHistory; }
    public ArrayList<Player> getPlayers() { return players; }
    public Integer getPlayerAmount() { return playerAmount; }
    public int getCurrentLives() { return currentLives; }
    public int getCurrentHints() { return currentHints; }
    public int getMaxHints() { return maxHints; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public int getHandSize() { return handSize; }
    public int getTurnsUntilEnd() { return (turnsUntilEnd < 0) ? -1 : turnsUntilEnd; }
    public int getRewardedForPlaying() { return rewardedForPlaying; }
    public int getRemainingCardsAmount() { return deck.getSize(); }


    public Board(int playerAmount, int lives, int hints, int maxHints, int handSize, Deck replacement, boolean shufflePlayers, String... names) {
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

        inGameColors = deck.getColors();


        discardPile = new DiscardPile();

        playerMoveHistory = new ArrayList<>();

        this.playerAmount = playerAmount;
        currentLives = lives;
        currentHints = hints;
        this.maxHints = maxHints;
        currentPlayerIndex = 0;

        if (handSize <= 0) {
            if (playerAmount < 4)
                this.handSize = 5;
            else
                this.handSize = 4;
        } else {
            this.handSize = handSize;
        }

        turnsUntilEnd = -2137;
        rewardedForPlaying = 0;
        for (Card card : deck.getCards())
            if (card.getValue() > rewardedForPlaying)
                rewardedForPlaying = card.getValue();


        players = new ArrayList<>();
        for (String name : names) {
            LinkedList<Card> hand = new LinkedList<>();

            for (int i = 0; i< this.handSize; i++) {
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
        this(playerAmount, 3, 8, 8, 0, null, true, names);
    }


    public void action(PlayerMove playerMove) throws GameEndException, NoHintsLeftException {
        if (playerMove.getType() == MoveType.HINT) {
            if (currentHints > 0) {
                currentHints--;
                Hint hint;
                LinkedList<Card> cards;
                try {
                    hint = playerMove.getHint();
                    cards = hint.getHinted().getHand();
                } catch (Exception e) {
                    e.printStackTrace();
                    endMove(playerMove);
                    return;
                }

                if (hint.isHintTypeColor()) {
                    Color color = Color.RAINBOW;
                    try {
                        color = hint.getCardColor();
                    } catch (Exception ignored) {};

                    for (Card card : cards)
                        card.publicCardInfo.setColor(color, color.equals(card.getColor()) || card.getColor()==Color.RAINBOW);
                } else {
                    int number = 0;
                    try {
                        number = hint.getCardValue();
                    } catch (Exception ignored) {ignored.printStackTrace();};

                    for (Card card : cards)
                        card.publicCardInfo.setNumber(number, card.getValue()==number);
                }
                //AlertBox.display("new hand status", hint.getHinted().getHand().toString());
                endMove(playerMove);
                return;
            }
            throw new NoHintsLeftException();
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
            }

            if (deck.getSize() == 0)
                if (turnsUntilEnd < 0)
                    turnsUntilEnd = playerAmount+1;

            playerMove.getPlayer().playOrDiscard(cardDiscarded, topDeck);

            discardPile.add(cardDiscarded);

            currentHints = (currentHints >= maxHints) ? currentHints : currentHints+1;
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
            }

            if (deck.getSize() == 0)
                if (turnsUntilEnd < 0)
                    turnsUntilEnd = playerAmount+1;

            playerMove.getPlayer().playOrDiscard(cardPlayed, topDeck);

            if (result.get(cardPlayed.getColor()) +1 == cardPlayed.getValue()) {
                result.put(cardPlayed.getColor(), cardPlayed.getValue());
                if (cardPlayed.getValue() == rewardedForPlaying)
                    currentHints = (currentHints >= maxHints) ? maxHints : currentHints+1;
                playerMove.setCorrectPlay(true);
            } else {
                currentLives--;
                discardPile.add(cardPlayed);
                playerMove.setCorrectPlay(false);
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

    public boolean hasGameEnded() {
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

    public static String[] randomNames(int amount) {
        String[] ans = new String[amount];

        if (randomNames == null) {
            randomNames = new ArrayList<>();
            randomNames.add("Rolande");
            randomNames.add("Domingo");
            randomNames.add("Cody");
            randomNames.add("Shawanda");
            randomNames.add("Tashia");
            randomNames.add("Tesha");
            randomNames.add("Yukiko");
            randomNames.add("Donita");
            randomNames.add("Paulette");
            randomNames.add("Dorie");
            randomNames.add("Yuki");
            randomNames.add("Nilsa");
            randomNames.add("Yevette");
            randomNames.add("Sabine");
            randomNames.add("Herma");
            randomNames.add("Adina");
            randomNames.add("Alphonse");
            randomNames.add("Adena");
            randomNames.add("Tomika");
            randomNames.add("Lanita");
            randomNames.add("Ethyl");
            randomNames.add("Abbie");
            randomNames.add("Marvin");
            randomNames.add("Myrta");
            randomNames.add("Sal");
            randomNames.add("Chung");
            randomNames.add("Joie");
            randomNames.add("Rosendo");
            randomNames.add("Anjanette");
            randomNames.add("Jonelle");
            randomNames.add("Florinda");
            randomNames.add("Leone");
            randomNames.add("Nickole");
            randomNames.add("Zada");
            randomNames.add("Cinda");
            randomNames.add("Kacy");
            randomNames.add("Jessica");
            randomNames.add("Neida");
            randomNames.add("Khadijah");
            randomNames.add("Chanel");
            randomNames.add("Marcelene");
            randomNames.add("Mari");
            randomNames.add("Odilia");
            randomNames.add("Jeanice");
            randomNames.add("Marvella");
            randomNames.add("Silva");
            randomNames.add("Simonne");
            randomNames.add("Dani");
            randomNames.add("Meaghan");
            randomNames.add("Latashia");
        }
        Collections.shuffle(randomNames);

        for (int i = 0; i< amount; i++) {
            ans[i]=randomNames.get(i);
        }

        return ans;
    }

    public String getStringPlayerMoveHistory() {
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i< playerMoveHistory.size(); i++) {
            PlayerMove move = playerMoveHistory.get(i);
            try {
                if (move.getHint().getHinted() == players.get(currentPlayerIndex))
                    ans.append(move.toString(true));
                else
                    ans.append(move.toString(false));
            } catch (Exception e) {
                ans.append(move.toString(false));
            }


            ans.append("\n");
        }
        return new String(ans);
    }

    public String getStringPlayerMoveHistory(int whosBlurred) {
        StringBuilder ans = new StringBuilder();

        ans.append("Remaining deck size: ");
        ans.append(deck.getSize());
        ans.append("\n\n");

        for (int i = playerMoveHistory.size()-1; i>=0; i--) {
            PlayerMove move = playerMoveHistory.get(i);

            try {
                if (move.getHint().getHinted() == players.get(whosBlurred))
                    ans.append(move.toString(true));
                else ans.append(move.toString(false));
            } catch (NoHintMoveException e) {
                ans.append(move.toString(false));
            }
            ans.append("\n\n");
        }

        return new String(ans);
    }

    public String getLastPlay() {
        if (playerMoveHistory.size() == 0)
            return "";
        StringBuilder ans = new StringBuilder();
        PlayerMove move = playerMoveHistory.get(playerMoveHistory.size()-1);
        ans.append(move.getPlayer().getName());
        switch (move.getType().ordinal()) {
            case 0: //discard
                ans.append(" discarded ");
                Card card0 = null;
                try {
                    card0 = move.getCard();
                } catch (NoCardMoveException ignored) {}
                ans.append(card0);
                break;
            case 1: //play
                ans.append(" played ");
                Card card1 = null;
                try {
                    card1 = move.getCard();
                } catch (NoCardMoveException ignored) {}
                ans.append(card1);
                break;
            case 2: //hint
                ans.append(" hinted ");
                Hint hint = null;
                try {
                    hint = move.getHint();
                } catch (Exception ignored) {}
                assert hint != null;

                if (hint.isHintTypeColor()) {
                    ans.append("color ");
                    try {
                        ans.append(hint.getCardColor());
                    } catch (Exception ignored) {}
                } else {
                    ans.append("number ");
                    try {
                        ans.append(hint.getCardValue());
                    } catch (Exception ignored) {}
                }

                ans.append(" to ");
                ans.append(hint.getHinted().getName());

                break;
        }
        return new String(ans);
    }
}

