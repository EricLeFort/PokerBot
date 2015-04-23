package pokerClient;
/**
 * @author Eric Le Fort
 * @version 1
 */

import java.util.ArrayList;
import tools.Card;

public class Player{
	protected ArrayList<Card> hand = new ArrayList<Card>();
	protected int[] currentHandType = new int[2];
	protected String name;
	protected double playerCurrentBetSize = 0;
	protected int stack;
	private boolean inPlay = true, allIn = false;
	
	public Player(String name, int stack){
		this.name = name;
		this.stack = stack;
	}//Player(int name, String stack)
	
	public Player(int nameNum, int stack){
		this.name = "Computer" + nameNum;
		this.stack = stack;
	}//Player(int nameNum, int stack)
	
	public Player(int stack){
		this.name = "Computer";
		this.stack = stack;
	}//Player(int stack)
	
	public int getStack(){
		return this.stack;
	}//getStack()
	
	public String getName(){
		return this.name;
	}//getName()
	
	public ArrayList<Card> getHand(){
		return this.hand;
	}//getHand()
	
	public boolean getInPlay(){
		return this.inPlay;
	}//getInPlay()
	
	public int[] getCurrentHandType(){
		return this.currentHandType;
	}//getCurrentHandType()
	
	public boolean getAllIn(){
		return this.allIn;
	}//getAllIn()
	
	public double getCurrentBetSize(){
		return this.playerCurrentBetSize;
	}//getCurrentBetSize()
	
	public void setHand(ArrayList<Card> cards){
		this.hand = cards;
	}//getHand()
	
	public void setHand(Card a, Card b){
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.clear();
		cards.add(a);
		cards.add(b);
		this.hand = cards;
	}//setHand(Card a, Card b)
	
	public void setInPlay(boolean arg1){
		this.inPlay = arg1;
	}//setInPlay
	
	public void setCurrentHandType(int[] handType){
		this.currentHandType = handType;
	}//setCurrentHandType(int[] handType)
	
	public void allIn(){
		this.playerCurrentBetSize = this.stack;
		this.stack = 0;
		this.allIn = true;
	}//setAllIn(boolean allIn)
	
	public int call(double currentBetSize, int playersAllIn){
		if(currentBetSize >= this.stack){
			allIn();
			playersAllIn++;
		}else{
			this.playerCurrentBetSize = currentBetSize;
			this.stack -= currentBetSize;
		}
		return playersAllIn;
	}//call(double currentBetSize, int playersAllIn)
	
	public void setCurrentBetSize(double betSize){
		if(betSize >= this.stack){
			this.playerCurrentBetSize = this.stack;
			this.stack = 0;
			this.allIn = true;
		}else if(betSize == 0){
			this.playerCurrentBetSize = 0;
		}else{
			this.stack -= (betSize - this.playerCurrentBetSize);
			this.playerCurrentBetSize = betSize;
		}
	}//setCurrentBetSize(double betSize)
	
	public void alterStack(double arg1){
		this.stack += arg1;
	}//addToStack()
	
	/**
	 * The player at the given index will fold their hand and the program checks to see if there's only one person left.
	 * If there is, then the index of that remaining player is returned. 
	 * @param index
	 * @return winningIndex
	 */
	public void fold(){
		this.inPlay = false;
	}//fold(int index)
	
}//Player

/**
 * Every hand needs to update a bunch of player stats at different times, make sure I hit all of them appropriately.
 * HandSeen should be updated when player folds or hand finishes to ensure all appropriate variables are updated.
 * Change percent to be formatted to XX.XX, %100.
 */
