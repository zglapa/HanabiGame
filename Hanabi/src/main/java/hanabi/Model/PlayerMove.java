package hanabi.Model;

import java.io.Serializable;

public final class PlayerMove implements Serializable {
    private MoveType moveType;
    private Card card;
    private Hint hint;
    private Player currentPlayer;
    private int cardIndex;
    private Boolean correctPlay;

    public PlayerMove(Player currentPlayer, MoveType moveType, int cardIndex) {
        this.currentPlayer = currentPlayer;
        this.card = currentPlayer.getHand().get(cardIndex);
        this.moveType = moveType;
        this.cardIndex = cardIndex+1;
    }
    public PlayerMove(Player currentPlayer, MoveType moveType, Hint hint){
        this.currentPlayer = currentPlayer;
        this.moveType = moveType;
        this.hint = hint;
    }
    public Player getPlayer(){return  this.currentPlayer;}
    public MoveType getType() {
        return this.moveType;
    }
    public Card getCard() throws NoCardMoveException{
        if(this.moveType == MoveType.HINT ) throw new NoCardMoveException();
        return this.card;
    }
    public Hint getHint() throws NoHintMoveException{
        if(this.moveType != MoveType.HINT) throw new NoHintMoveException();
        return this.hint;
    }

    public void setCorrectPlay(boolean a) { correctPlay = a; }
    //@Override
    public String toString(boolean isCurrentPlayer){
        if(moveType == MoveType.HINT){
            if (isCurrentPlayer)
                return new String(new StringBuilder().append(currentPlayer.toString()).append("\n").append(hint.toStringHidden()));
            return new String(new StringBuilder().append(currentPlayer.toString()).append("\n").append(hint.toString()));
        }
        else {
            StringBuilder ans = new StringBuilder().append(currentPlayer.toString()).append("\n")
                    .append(moveType.name()).append(" from position ").append(cardIndex).append("\n")
                    .append(card.toString());
            if (moveType == MoveType.PLAY && correctPlay != null) {
                ans.append('\n');
                if (correctPlay)
                    ans.append("CORRECT");
                else ans.append("WRONG");
            }
            return new String(ans);
        }
    }
}

class NoCardMoveException extends Exception{
    public NoCardMoveException(){};

    @Override
    public String toString(){
        return "NoCardMoveException";
    }
}
class NoHintMoveException extends Exception{
    public NoHintMoveException(){};
    @Override
    public String toString(){
        return "NoHintMoveException";
    }
}