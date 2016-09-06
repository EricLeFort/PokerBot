package tools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class Toolbox{
	
	/**
	 * Creates a shuffled deck of cards.
	 * @return An array holding 52 <code>Cards</code> in a random order.
	 */
	public static Card[] getShuffledDeck(){
		ArrayList<Card> orderedDeck = new ArrayList<Card>();
		Card[] shuffledDeck = new Card[52];
		
		for(int i = 0; i < 52; i++){											//Populates the orderedDeck.
			orderedDeck.add(new Card(i));
		}
		
		for(int i = 51; i > -1; i--){			
			shuffledDeck[i] = orderedDeck.remove((int)(Math.random() * (i+1)));	//Grabs random card, adds it to the shuffled deck.
		}
		return shuffledDeck;
	}//getShuffledDeck()
	
	/**
	 * Generates a random number within the specified range(from 0 to the range value inclusive). Returns -1 if an error occurs.
	 * @param range
	 * @return rnd
	 */
	public static int rndNumGen(int range){
		int rnd = 0, stdRnd = (int)(Math.random() * 500);			//Arbitrary value of 500 to give a decent range to the seed value.
		
		try{
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed(stdRnd);
			rnd = sr.nextInt(range + 1);
		}catch(NoSuchAlgorithmException nsae){
			return -1;
		}catch(NoSuchProviderException nspe){
			return -1;
		}
		
		return rnd;
	}//rndNumGen()

	/**
	 * Takes in a double and converts it into a formatted <code>String</code> to be used as monetary values.
	 * I.e
	 * 5.0  - "5"
	 * 5.01 - "5.01"
	 * 5.1  - "5.10"
	 * @param a - The double to be converted.
	 * @return A <code>String</code> representation of this double.
	 */
	public static String getMonetaryConversion(double a){
		String b = "" + a;
		
		if(b.charAt(b.length() - 2) == '.'){
			if(b.charAt(b.length() - 1) == '0'){
				return "" + (int)a;
			}else{
				return a + "0";
			}
		}
		
		return "" + a;
	}//getMonetaryConversion()
	
}//Toolbox
