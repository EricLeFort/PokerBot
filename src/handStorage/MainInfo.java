package handStorage;

import tools.Toolbox;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
class MainInfo{
	private Date date;
	private Currency currency;
	private String gameType, tableName, tableType;
	private long handID;
	private double sb, bb, ante;
	private int buttonPosition, sbPosition, bbPosition;
	
	/**
	 * Creates a new <code>MainInfo</code>.
	 * @param date - The date of this hand.
	 * @param currency - The currency this hand is using.
	 * @param tableName - The name of this table.
	 * @param gameType - The style of poker (i.e. Hold'em No Limit)
	 * @param tableType - The type of table (i.e. 6-max).
	 * @param handID - The ID of this hand.
	 * @param buttonPosition - The position of the button.
	 * @param bbPosition - The position of the big blind.
	 * @param sbPosition - The position of the small blind.
	 * @param sb - The amount of the small blind.
	 * @param bb - The amount of the big blind.
	 * @param ante - The amount of the ante.
	 */
	public MainInfo(Date date, Currency currency, String tableName, String gameType, String tableType, double sb, double bb, double ante,
			long handID, int buttonPosition, int bbPosition, int sbPosition, int numPlayers){
		gameType = "Hold'em No Limit";
		this.date = date;
		this.currency = currency;
		this.tableName = tableName;
		this.gameType = gameType;
		this.tableType = tableType;//TODO verify, make enum?
		this.handID = handID;
		this.buttonPosition = buttonPosition;
		this.bbPosition = bbPosition;
		this.sbPosition = sbPosition;
		this.sb = sb;
		this.bb = bb;
		this.ante = ante;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> to represent this info.
	 * An example is:
	 * "PokerStars Hand #150669471391:  Hold'em No Limit ($0.02/$0.05 USD) - 2016/03/20 16:20:59 ET
	 * Table 'Helionape' 6-max Seat #5 is the button
	 * "
	 * @return A <code>String</code> representing this info.
	 */
	@Override
	public String toString(){
		String newline = System.getProperty("line.separator");
		return "PokerStars Hand #" + handID + ":  " + gameType + " ($" + Toolbox.getMonetaryConversion(sb) + "/$" +
				Toolbox.getMonetaryConversion(bb) + " " + currency + ") - " + date + newline + "Table '" + tableName +
				"' " + tableType + " Seat #" + (buttonPosition + 1) + " is the button" + newline;
	}//toString()
}//MainInfo
