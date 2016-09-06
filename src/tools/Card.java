package tools;

/**
 * @author Eric
 * @version 1.0
 */
public class Card implements Comparable<Card>{
	final private String cardName, name, valueSuit;
	final private int suit, cardValue;
	
	/**
	 * Takes the <code>integer</code> value of the card as input assuming the suits go from Clubs to Diamonds to Hearts to Spades.
	 * 2 represents the first card in the suit and Ace the last.
	 * @param value - An integer from 0-51 representing which card this is.
	 */
	public Card(int value){
		String tempValueSuit;
		this.suit = value / 13;
		this.cardValue = value % 13;
		
		if(cardValue == 0){
			name = "two";
			tempValueSuit = "2";
		}else if(cardValue == 1){
			name = "three";
			tempValueSuit = "3";
		}else if(cardValue == 2){
			name = "four";
			tempValueSuit = "4";
		}else if(cardValue == 3){
			name = "five";
			tempValueSuit = "5";
		}else if(cardValue == 4){
			name = "six";
			tempValueSuit = "6";
		}else if(cardValue == 5){
			name = "seven";
			tempValueSuit = "7";
		}else if(cardValue == 6){
			name = "eight";
			tempValueSuit = "8";
		}else if(cardValue == 7){
			name = "nine";
			tempValueSuit = "9";
		}else if(cardValue == 8){
			name = "ten";
			tempValueSuit = "T";
		}else if(cardValue == 9){
			name = "jack";
			tempValueSuit = "J";
		}else if(cardValue == 10){
			name = "queen";
			tempValueSuit = "Q";
		}else if(cardValue == 11){
			name = "king";
			tempValueSuit = "K";
		}else if(cardValue == 12){
			name = "ace";
			tempValueSuit = "A";
		}else{
			name = "ERROR";
			tempValueSuit = "E";
		}
		
		if(suit == 0){
			cardName = name + " of clubs";
			tempValueSuit += "c";
		}else if(suit == 1){
			cardName = name + " of diamonds";
			tempValueSuit += "d";
		}else if(suit == 2){
			cardName = name + " of hearts";
			tempValueSuit += "h";
		}else if(suit == 3){
			cardName = name + " of spades";
			tempValueSuit += "s";
		}else{
			cardName = "ERROR";
			tempValueSuit += "E";
		}
		
		valueSuit = tempValueSuit;
	}//Constructor
	
	/**
	 * Takes a <code>String</code> of length two that represents the value suit of this <code>Card</code>.
	 * I.e. Kh is the King of Hearts
	 * 9c is the 9 of clubs
	 * As is the Ace of Spades
	 * Td is the 10 of Diamonds
	 */
	public Card(String valueSuit){
		String tempValueSuit;
		
		if(valueSuit.length() != 2){
			cardName = "ERROR";
			name = "ERROR";
			this.valueSuit = "EE";
			suit = -1;
			cardValue = -1;
			return;
		}
		
		if(valueSuit.charAt(0) == '2'){
			cardValue = 0;
			name = "two";
			tempValueSuit = "2";
		}else if(valueSuit.charAt(0) == '3'){
			cardValue = 1;
			name = "three";
			tempValueSuit = "3";
		}else if(valueSuit.charAt(0) == '4'){
			cardValue = 2;
			name = "four";
			tempValueSuit = "4";
		}else if(valueSuit.charAt(0) == '5'){
			cardValue = 3;
			name = "five";
			tempValueSuit = "5";
		}else if(valueSuit.charAt(0) == '6'){
			cardValue = 4;
			name = "six";
			tempValueSuit = "6";
		}else if(valueSuit.charAt(0) == '7'){
			cardValue = 5;
			name = "seven";
			tempValueSuit = "7";
		}else if(valueSuit.charAt(0) == '8'){
			cardValue = 6;
			name = "eight";
			tempValueSuit = "8";
		}else if(valueSuit.charAt(0) == '9'){
			cardValue = 7;
			name = "nine";
			tempValueSuit = "9";
		}else if(valueSuit.charAt(0) == 'T'){
			cardValue = 8;
			name = "ten";
			tempValueSuit = "T";
		}else if(valueSuit.charAt(0) == 'J'){
			cardValue = 9;
			name = "jack";
			tempValueSuit = "J";
		}else if(valueSuit.charAt(0) == 'Q'){
			cardValue = 10;
			name = "queen";
			tempValueSuit = "Q";
		}else if(valueSuit.charAt(0) == 'K'){
			cardValue = 11;
			name = "king";
			tempValueSuit = "K";
		}else if(valueSuit.charAt(0) == 'A'){
			cardValue = 12;
			name = "ace";
			tempValueSuit = "A";
		}else{
			cardValue = -1;
			tempValueSuit = "E";
			name = "ERROR";
		}
		
		if(valueSuit.charAt(1) == 'c'){
			suit = 0;
			tempValueSuit += "c";
			cardName = name + " of clubs";
		}else if(valueSuit.charAt(1) == 'd'){
			suit = 1;
			tempValueSuit += "d";
			cardName = name + " of diamonds";
		}else if(valueSuit.charAt(1) == 'h'){
			suit = 2;
			tempValueSuit += "h";
			cardName = name + " of hearts";
		}else if(valueSuit.charAt(1) == 's'){
			suit = 3;
			tempValueSuit += "s";
			cardName = name + " of spades";
		}else{
			suit = -1;
			tempValueSuit += "E";
			cardName = "ERROR";
		}
		
		this.valueSuit = tempValueSuit;
	}//Constructor
	
	/**
	 * Checks the appropriate suit based on the value of the card.
	 * @return name of suit or that input is invalid in the form of a String.
	 */
	public String getSuitName(){
		if(this.suit == 0){
			return "clubs";
		}else if(this.suit == 1){
			return "diamonds";
		}else if(this.suit == 2){
			return "hearts";
		}else if (this.suit == 3){
			return "spades";
		}else{
			return "Error in reading suit";
		}
	}//getSuitName()
	
	/**
	 * Returns the name of the card (e.g. Seven or King)
	 * @return name
	 */
	public String getName(){ return name; }//getName()
	
	/**
	 * Returns the integer value representing each suit.
	 * 0 - Clubs
	 * 1 - Diamonds
	 * 2 - Hearts
	 * 3 - Spades
	 * @return suit
	 */
	public int getSuit(){ return suit; }//getSuit()
	
	/**
	 * Returns the integer value representing the card.
	 * 0 - 2
	 * ...
	 * 12 - Ace
	 * @return cardValue
	 */
	public int getCardValue(){ return cardValue; }//getcardValue()
	
	/**
	 * Returns a <code>String</code> representing the value-suit representation of this <code>Card</code>.
	 * i.e.	A Jack of Hearts would be "Jh"
	 * 		A Ten of Spades would be "Ts".
	 * @return A <code></code> representing the value-suit representation of this <code>Card</code>.
	 */
	public String getValueSuit(){ return valueSuit; }//getValueSuit()
	
	/**
	 * Compares this <code>Card</code> to another <code>Card</code>'s value.
	 * Returns:
	 * 1  - This <code>Card</code> is higher.
	 * 0  - The <code>Card</code>s are equal.
	 * -1 - The other <code>Card</code> is higher.
	 * IMPORTANT: Suit is not considered, use <code>equals()</code> for that.
	 * 
	 * @param other - The other <code>Card</code>.
	 * @return -1, 0 or 1 based on which <code>Card</code> is best.
	 */
	@Override
	public int compareTo(Card other){
		if(cardValue > other.getCardValue()){
			return 1;
		}else if(cardValue < other.getCardValue()){
			return -1;
		}
		return 0;
	}//compareTo()
	
	/**
	 * Compares this <code>Card</code> to another <code>Object</code>.
	 * Returns true if the other <code>Object</code> is also a <code>Card</code> of the same value and suit.
	 * 
	 * @param other - The <code>Object</code> to compare this <code>Card</code> to.
	 * @return true if the <code>Cards</code> are equal or false otherwise.
	 */
	@Override
	public boolean equals(Object other){
		return other instanceof Card
				&& suit == ((Card)other).getSuit()
				&& cardValue == ((Card)other).getCardValue();
	}//equals()
	
	/**
	 * Returns the string representing the name of the card.
	 * i.e. "Six of clubs"
	 * @return cardName
	 */
	@Override
	public String toString(){ return cardName; }//toString()
	
}//Card
