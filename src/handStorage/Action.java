package handStorage;

import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
class Action{
	private String playerName;
	private ActionType type;
	private double amount, raiseAmount;
	
	/**
	 * Creates a new <code>Action</code> based on the parameters provided.
	 * @param playerName - The name of the player making this action.
	 * @param actionType - The type of action.
	 * @param amount - The amount associated with this action (0 for fold/check).
	 * @param raiseAmount - The amount raised. (0 for fold/check/call/bet)
	 */
	public Action(String playerName, ActionType actionType, double amount, double raiseAmount){
		this.playerName = playerName;
		this.type = actionType;
		
		if(actionType == ActionType.FOLD || actionType == ActionType.CHECK){
			amount = 0;
			raiseAmount = 0;
		}else if(actionType == ActionType.RAISE){
			this.amount = amount;
			this.raiseAmount = raiseAmount;
		}else{
			this.amount = amount;
			this.raiseAmount = 0;
		}
	}//Constructor
	
	/**
	 * Returns a <code>String</code> to represent this action.
	 * Some examples include:
	 * "folds"
	 * "checks"
	 * "calls $1.50"
	 * "bets $0.05"
	 * "raises $5 to $15"
	 * @return A <code>String</code> representing this <code>Action</code>.
	 */
	@Override
	public String toString(){
		String output = playerName + ": ";
		
		if(type == ActionType.FOLD){
			return output + "folds ";
		}else if(type == ActionType.CHECK){
			return output + "checks ";
		}else if(type == ActionType.CALL){
			return output + "calls $" + Toolbox.getMonetaryConversion(amount);
		}else if(type == ActionType.BET){
			return output + "bets $" + Toolbox.getMonetaryConversion(amount);
		}else{
			return output + "raises $" + Toolbox.getMonetaryConversion(raiseAmount) + " to $" + Toolbox.getMonetaryConversion(amount);
		}
	}//toString()
	
}//Action
