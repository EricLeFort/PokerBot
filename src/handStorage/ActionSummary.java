package handStorage;

import tools.Card;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
class ActionSummary{
	private Action holeCardsAction[], flopAction[], turnAction[], riverAction[];
	private Card holeCards[][], board[];
	private String[] playerNames;
	
	/**
	 * Creates a new <code>ActionSummary</code>.
	 * @param holeCardsAction - The actions that happened after the cards were dealt.
	 * @param flopAction - The actions that happened after the flop.
	 * @param turnAction - The actions that happened after the turn.
	 * @param riverAction - The actions that happened after the river.
	 * @param holeCards - The initially known hole cards (null references indicate the card(s) are not known).
	 * @param board - The community cards for this hand.
	 * @param playerNames - The name of the players at the table.
	 */
	public ActionSummary(Action holeCardsAction[], Action flopAction[], Action turnAction[], Action riverAction[],
			Card[][] holeCards, Card[] board, String[] playerNames){
		this.holeCardsAction = holeCardsAction;
		this.flopAction = flopAction;
		this.turnAction = turnAction;
		this.riverAction = riverAction;
		this.holeCards = holeCards;
		this.board = board;
		this.playerNames = playerNames;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> representing this <code>ActionSummary</code>.
	 * @return A <code>String</code> representation of this <code>ActionSummary</code>.
	 */
	@Override
	public String toString(){
		String output = "", newline = System.getProperty("line.separator");
		output += "*** HOLE CARDS ***" + newline;
		for(int i = 0; i < holeCards.length; i++){
			if(holeCards[i] != null){
				output += "Dealt to " + playerNames[i] + " [";
				if(holeCards[i][1] != null){
					output += holeCards[i][0].getValueSuit() + " " + holeCards[i][1].getValueSuit();
				}else{
					output += holeCards[i][1].getValueSuit() + " ??";
				}
				output += "]" + newline;
			}
		}
		
		for(int i = 0; i < holeCardsAction.length; i++){
			output += holeCardsAction[i] + newline;
		}
		
		if(flopAction == null){						//Didn't get to the flop.
			return output;
		}
		output += "*** FLOP *** ";
		output += "[" + board[0].getValueSuit() + " " + board[1].getValueSuit() + " " + board[2].getValueSuit() + "]" + newline;
		for(int i = 0; i < flopAction.length; i++){
			output += flopAction[i] + newline;
		}
		
		if(turnAction == null){						//Didn't get to the turn.
			return output;
		}
		output += "*** TURN *** ";
		output += "[" + board[0].getValueSuit() + " " + board[1].getValueSuit() + " " + board[2].getValueSuit() + "] ";
		output += "[" + board[3].getValueSuit() + "]" + newline;
		for(int i = 0; i < turnAction.length; i++){
			output += turnAction[i] + newline;
		}
		
		if(riverAction == null){					//Didn't get to the river
			return output;
		}
		output += "*** RIVER *** ";
		output += "[" + board[0].getValueSuit() + " " + board[1].getValueSuit() + " " +
				board[2].getValueSuit() + " " + board[3].getValueSuit() +"] ";
		output += "[" + board[4].getValueSuit() + "]" + newline;
		for(int i = 0; i < riverAction.length; i++){
			output += riverAction[i] + newline;//TODO add cards
		}
		return output;
	}//toString()
	
}//ActionSummary
