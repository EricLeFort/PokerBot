package handStorage;

import tools.Card;
import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class ShowdownInfo{
	private ShowdownInfoType type;
	private Card holeCardOne, holeCardTwo;
	private String playerName, hand, newline = System.getProperty("line.separator");
	private double winnings;
	private boolean isMainPot;
	
	/**
	 * Creates a new <code>ShowdownInfo</code> that will hold a shown hand.
	 * @param playerName - The name of the player this info involves.
	 * @param hand - The hand that this player showed.
	 * @param holeCardOne - The player's first hole card.
	 * @param holeCardTwo - The player's second hole card.
	 */
	public ShowdownInfo(String playerName, String hand, Card holeCardOne, Card holeCardTwo){
		type = ShowdownInfoType.SHOW;
		this.playerName = playerName;
		this.hand = hand;
		this.holeCardOne = holeCardOne;
		this.holeCardTwo = holeCardTwo;
	}//Constructor
	
	/**
	 * Creates a new <code>ShowdownInfo</code> that will hold a mucked hand result.
	 * @param playerName - The name of the player this info involves.
	 */
	public ShowdownInfo(String playerName){
		type = ShowdownInfoType.MUCK;
		this.playerName = playerName;
	}//Constructor
	
	/**
	 * Creates a new <code>ShowdownInfo</code> that will represent an uncalled bet being returned.
	 * @param playerName - The name of the player this info involves.
	 * @param amount - The amount this player bet that was returned to them.
	 */
	public ShowdownInfo(String playerName, double amount){
		type = ShowdownInfoType.UNCALLED_BET;
		this.playerName = playerName;
		this.winnings = amount;
	}//Constructor
	
	/**
	 * Creates a new <code>ShowdownInfo</code> that will hold a winnings collection result.
	 * @param playerName - The name of the player this info involves.
	 * @param winnings - The amount the player won.
	 * @param isMainPot - True if this is the main pot, false if it's a side pot.
	 */
	public ShowdownInfo(String playerName, double winnings, boolean isMainPot){
		type = ShowdownInfoType.COLLECT;
		this.playerName = playerName;
		this.winnings = winnings;
		this.isMainPot = isMainPot;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> representing this piece of showdown info.
	 * @return A <code>String</code> representation of this piece of showdown info.
	 * Some examples include:
	 * Player 1: mucks
	 * Joe Blow: shows [Ah Kh] (a pair of aces)
	 * Player 2 collected $0.22 from side pot
	 * Player 2 collected $5 from pot
	 */
	//TODO will not act as expected with whole values (need $5 not $5.0)
	@Override
	public String toString(){
		if(type == ShowdownInfoType.SHOW){
			return playerName + ": shows [" + holeCardOne.getValueSuit() + " " + holeCardTwo.getValueSuit() + "] (" + hand + ")" + newline;
		}else if(type == ShowdownInfoType.MUCK){
			return playerName + ": mucks hand" + newline;
		}else if(type == ShowdownInfoType.UNCALLED_BET){
			return "Uncalled bet ($" + Toolbox.getMonetaryConversion(winnings) + ") returned to " + playerName + newline;
		}else{
			if(isMainPot){
				return playerName + " collected $" + Toolbox.getMonetaryConversion(winnings) + " from pot" + newline;//TODO verify
			}
			return playerName + " collected $" + Toolbox.getMonetaryConversion(winnings) + " from side pot" + newline;//TODO verify
		}
	}//toString()
	
}//ShowdownInfo
