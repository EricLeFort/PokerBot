package handStorage;

import tools.Card;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
class HandSummary{
	private PlayerActionSummary[] playerSummaries;
	private Card board[];
	private String[] playerNames;
	private double pots[], rake;
	
	/**
	 * Creates a new <code>HandSummary</code>.
	 * @param playerSummaries - The summaries of the players' actions.
	 * @param board - The cards that were part of the community cards.
	 * @param playerNames - The names of the players.
	 * @param pots - The pots involved in this hand.
	 * @param rake - The amount of rake taken from this pot.
	 */
	public HandSummary(PlayerActionSummary[] playerSummaries, Card[] board, String[] playerNames, double[] pots, double rake){
		this.playerSummaries = playerSummaries;
		this.board = board;
		this.playerNames = playerNames;
		this.pots = pots;
		this.rake = rake;
	}//Constructor
	
	//TODO JavaDoc
	@Override
	public String toString(){
		String output = "", newline = System.getProperty("line.separator");
		
		output += "*** SUMMARY ***" + newline;
		output += "Total pot $" + pots[0] + " | Rake $" + rake + " " + newline;	//TODO verify this whole "pot" breakdown deal.
		output += "Board [";
		for(int i = 0; i < board.length - 1; i++){
			if(board[i] != null){
				output += board[i].getValueSuit();
			}
			if(i != board.length - 1){
				output += " ";
			}
		}
		output += board[board.length - 1].getValueSuit() + "]" + newline;
		for(int i = 0; i < playerSummaries.length; i++){
			output += playerSummaries[i] + newline;
		}
		
		return output;
	}//toString()
	
}//HandSummary
