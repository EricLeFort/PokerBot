package handStorage;
import tools.Card;
import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.1
 */
class PlayerActionSummary{
	private FoldTime foldTime;
	private Card holeCards[];
	private String playerName, hand;
	private double winnings;
	private int seatNum;
	private boolean won, buttonPosition, bbPosition, sbPosition;
	
	/**
	 * Creates a new <code>PlayerActionSummary</code>.
	 * @param foldTimes - When/if the player folded.
	 * @param playerNames - The name of the player. 
	 * @param hands - The hand that this player has.
	 * @param holeCards - The value/suit representation of this players' hole cards.
	 * @param winnings - The winnings for this player.
	 * @param buttonPosition - This player is on the button.
	 * @param bbPosition - This player is the big blind.
	 * @param sbPosition - This player is the small blind.
	 * @param won - Whether this player won this hand or not.
	 */
	public PlayerActionSummary(FoldTime foldTime, String playerName, String hand, Card holeCards[],
			double winnings, int seatNum, boolean buttonPosition, boolean bbPosition, boolean sbPosition, boolean won){
		this.foldTime = foldTime;
		this.playerName = playerName;
		this.hand = hand;
		this.holeCards = holeCards;
		this.winnings = winnings;
		this.seatNum = seatNum;
		this.buttonPosition = buttonPosition;
		this.bbPosition = bbPosition;
		this.sbPosition = sbPosition;
		this.won = won;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> representing this <code>PlayerActionSummary</code>.
	 * @return A <code>String</code> representation of this <code>PlayerActionSummary</code>.
	 */
	@Override
	public String toString(){
		String output = "", newline = System.getProperty("line.separator");
		
			output += "Seat " + (seatNum + 1) + ": " + playerName;
			
			if(buttonPosition){						//TODO what if they're a blind and a button?
				output += " (button) ";
			}else if(sbPosition){
				output += " (small blind) ";
			}else if(bbPosition){
				output += " (big blind) ";
			}else{
				output += " ";
			}
													//TODO if won without showdown it's different:
			if(foldTime == FoldTime.DIDNT_FOLD){	//TODO verify whether mucking is different?
				output += "showed [" + holeCards[0].getValueSuit() + " " + holeCards[1].getValueSuit() + "] and ";
				if(won){
					output += "won ($" + Toolbox.getMonetaryConversion(winnings) + ") ";
				}else{
					output += "lost ";
				}
				return output + "with " + hand + newline;
			}else if(foldTime == FoldTime.MADE_EVERYONE_ELSE_FOLD){
				return output + "collected ($" + Toolbox.getMonetaryConversion(winnings) + ")";
			}else if(foldTime == FoldTime.DIDNT_BET){
				return output + "folded before Flop (didn't bet)";
			}else if(foldTime == FoldTime.BEFORE_FLOP){
				return output + "folded before Flop";
			}else if(foldTime == FoldTime.AFTER_FLOP){
				return output + "folded on the Flop";
			}else if(foldTime == FoldTime.AFTER_TURN){
				return output + "folded on the Turn";
			}else{
				return output + "folded on the River";
			}
	}//toString()
	
}//PlayerActionSummary
