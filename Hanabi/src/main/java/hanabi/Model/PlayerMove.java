package hanabi.Model;

public final class PlayerMove {
    private MoveType moveType;
    private Card card;
    private Hint hint;
    private Player currentPlayer;
    public PlayerMove(Player currentPlayer, MoveType moveType,Card card){
        this.currentPlayer = currentPlayer;
        this.card = card;
        this.moveType = moveType;
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
    @Override
    public String toString(){
        if(moveType == MoveType.HINT){
            return currentPlayer.toString() + " | " + moveType.name() + " | " + hint.toString();
        }
        else{
            return currentPlayer.toString() + " | " + moveType.name() + " | " + card.toString();
        }
    }

}
enum MoveType {
    DISCARD, PLAY, HINT
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