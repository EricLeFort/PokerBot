package handStorage;

import tools.Card;
import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class Hand{//TODO missing flop, turn and river cards on toString()!
	private MainInfo mainInfo;
	private ActionSummary actionSummary;
	private ShowdownInfo showdownInfo[];
	private HandSummary handSummary;
	private PlayerInfo playerInfo;
	
	//TODO JavaDoc
	public Hand(MainInfo mainInfo, ActionSummary actionSummary, ShowdownInfo[] showdownInfo,
			HandSummary handSummary, PlayerInfo playerInfo){
		this.mainInfo = mainInfo;
		this.actionSummary = actionSummary;
		this.showdownInfo = showdownInfo;
		this.handSummary = handSummary;
		this.playerInfo = playerInfo;
	}//Constructor
	
	//TODO create all getters to access the inner data. There's gonna be a lot probably.
	//TODO important info to have easily accessible:
	/*	-All known hole cards
	 *	-Pot size
	 *	-Starting stack sizes
	 *	-Community cards
	 *	-Blind/ante sizes
	 *	-Winning player
	 *	-Number of players in hand to start, after flop, after turn, after river, at showdown.
	 *	-
	 */
	
	//TODO JavaDoc
	@Override
	public String toString(){
		String output = "" + mainInfo + playerInfo + actionSummary;
		
		if(showdownInfo != null){							//Didn't get to the showdown.
			for(int i = 0; i < showdownInfo.length; i++){
				output += showdownInfo[i];
			}
		}
		output += handSummary;
		return output.substring(0, output.length() - 1);
	}//toString()
	
	
}//Hand
