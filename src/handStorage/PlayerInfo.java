package handStorage;

import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class PlayerInfo{
	private String playerNames[];
	private double stacks[], sb, bb, ante;
	private int sbPosition, bbPosition;
	private boolean inHand[], cashGame;
	
	/**
	 * Creates a new <code>PlayerInfo</code>.
	 * @param playerNames - The names of the players.
	 * @param stacks - The stack sizes of the players.
	 * @param inHand - An array of booleans signifying if the player in the corresponding positions are in the hand.
	 * @param sb - The amount of the small blind.
	 * @param bb - The amount of the big blind.
	 * @param ante - The amount of the ante.
	 * @param bbPosition - The position of the big blind.
	 * @param sbPosition - The position of the small blind.
	 */
	public PlayerInfo(String[] playerNames, double[] stacks, boolean[] inHand, double sb, double bb, double ante,
			int bbPosition, int sbPosition, boolean cashGame){
		this.playerNames = playerNames;
		this.stacks = stacks;
		this.inHand = inHand;
		this.sb = sb;
		this.bb = bb;
		this.bbPosition = bbPosition;
		this.sbPosition = sbPosition;
		this.ante = ante;
		this.cashGame = cashGame;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> representing this <code>PlayerInfo</code>.
	 * @return A <code>String</code> representation of this <code>PlayerInfo</code>.
	 */
	public String toString(){
		String output = "", newline = System.getProperty("line.separator");
		for(int i = 0; i < playerNames.length; i++){
			if(inHand[i]){
				output += "Seat " + (i+1) + ": " + playerNames[i] + " ($" + Toolbox.getMonetaryConversion(stacks[i]) + " in chips) " + newline;
			}else if(!cashGame){
				output += "Seat " + (i+1) + ": " + playerNames[i] + " ($" + Toolbox.getMonetaryConversion(stacks[i])
				+ " in chips) is sitting out" + newline;
			}
		}
		
		if(ante > 0){
			for(int i = 0; i < inHand.length; i++){
				if(inHand[i]){
					output += playerNames[i] + ": posts the ante $" + Toolbox.getMonetaryConversion(ante) + newline;
				}else{
					
				}
			}
		}
		
		for(int i = 0; i < sbPosition; i++){
			if(!inHand[i]){
				output += playerNames[i] + ": is sitting out " + newline;
			}
		}
		output += playerNames[sbPosition] + ": posts small blind $" + Toolbox.getMonetaryConversion(sb) + newline;
		
		for(int i = sbPosition + 1; i < bbPosition; i++){
			if(!inHand[i]){
				output += playerNames[i] + ": is sitting out " + newline;
			}
		}
		output += playerNames[bbPosition] + ": posts big blind $" + Toolbox.getMonetaryConversion(bb) + newline;
		for(int i = bbPosition + 1; i < inHand.length; i++){
			if(!inHand[i]){
				output += playerNames[i] + ": is sitting out " + newline;
			}
		}
		
		return output;
	}//toString()
	
}//PlayerInfo
