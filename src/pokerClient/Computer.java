package pokerClient;
/**
 * @author Eric Le Fort
 * @Version 1
 */

import java.util.ArrayList;

import tools.*;

public class Computer extends Player{
	
	public Computer(int computerIndex, int stack){
		super(computerIndex, stack);
	}//Computer(int nameNum, int stack)

	public double[] getPreFlopDecision(int BBposition, int myPosition, double currentBetSize, double BBsize, ArrayList<Player> table){
		Player currentPlayer;
		double[] choice = new double[2];
		double avgStack = 0;
		int numPlayers = 0, numLargeStack = 0, numCovering = 0, numSmaller = 0, numSmallStack = 0;	//TEMP maybe get around to implementing these variables later on.
		int numAfter = 0, numBefore = 0, lastToActIndex, stackStrength;
		int rndNum = Toolbox.rndNumGen(1000), handStrength = getStartingHandStrength(hand);
		
		if(BBposition == 0){
			lastToActIndex = table.size() - 2;
		}else if(BBposition == 1){
			lastToActIndex = table.size() - 1;
		}else{
			lastToActIndex = BBposition - 2;
		}
		
		for(int i = 0; i < table.size(); i++){
			avgStack += table.get(i).getStack();
		}
		avgStack = avgStack / table.size();
		
		if(stack > avgStack * 20){
			stackStrength = 6;
		}else if(stack > avgStack * 10){
			stackStrength = 5;
		}else if(stack > avgStack * 5){
			stackStrength = 4;
		}else if(stack > avgStack){
			stackStrength = 3;
		}else if(stack > avgStack * 0.5){
			stackStrength = 2;
		}else if(stack > avgStack * 0.1){
			stackStrength = 1;
		}else{
			stackStrength = 0;
		}
		
		
		for(int i = 0; i < table.size(); i++){			
			currentPlayer = table.get(i);
			if(currentPlayer.getInPlay() && i != myPosition){
				numPlayers++;
				if(this.stack > currentPlayer.getStack()){				//Determines relative stack sizes.
					if(this.stack * 3 <= currentPlayer.getStack()){
						numLargeStack++;
					}else{
						numCovering++;
					}
				}else{
					if(this.stack > currentPlayer.getStack() * 3){
						numSmallStack++;
					}else{
						numSmaller++;
					}
				}
				
				if((lastToActIndex > myPosition && i > myPosition) || (lastToActIndex < myPosition && (i > myPosition || i <= lastToActIndex))){		//This should work... Just trust it unless an issue is traced back to here, would be smart to double check logic.
					numAfter++;
				}else{
					numBefore++;
				}
			}
		}
		
		/*
		 * Next set of ifs determine the computers choice based on multiple variables including:  handStrength, position, stack size, 
		 * relative stack size, number of players still in hand and the size of the current bet.
		 * NPA - No Previous Action.
		 */
		
		if(handStrength == 7){									
			if(currentBetSize > BBsize && rndNum < 600){	//Any raise: call/check 60%, re-raise double 37%, re-raise all-in 3%.
				choice[0] = 2;									
				choice[1] = currentBetSize * 2;
			}else if(currentBetSize > BBsize && rndNum < 630){
				choice[0] = 2;
				choice[1] = stack;
			}else if(currentBetSize > BBsize){
				choice[1] = 1;
			}else if(rndNum < 750){							//NPA: Raise 3BBs 75%, raise all-in 3%, call/check 22%.
				choice[0] = 2;
				choice[1] = BBsize * 3;
			}else if(rndNum < 780){
				choice[0] = 2;
				choice[1] = stack;
			}else{
				choice[0] = 1;
			}
		}else if(handStrength == 6){
			if(stackStrength < 2){					//Small Stack: all-in 40%, raise double/raise 3BBs 30%, call 30%
				if(rndNum > 600){
					choice[0] = 2;
					choice[1] = stack;
				}else if(rndNum > 300){
					if(currentBetSize > BBsize){
						choice[0] = 2;
						choice[1] = currentBetSize * 2;
					}else{
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}
				}else{
					choice[0] = 1;
				}
			}else{									//Medium to Large Stack
				if(currentBetSize > BBsize * 7){	//Large bet: call 70%, fold 30%
					if(rndNum > 300){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize){	//Small to Medium bet: all-in 5%, re-raise double 15%, call 80 %
					if(rndNum > 950){
						choice[0] = 2;
						choice[1] = stack;
					}else if(rndNum > 800){
						choice[0] = 2;
						choice[1] = currentBetSize * 2;
					}else{
						choice[0] = 1;
					}
				}else{								//NPA: all-in 2%, raise 5-7 BBs 15%, raise 3BBs 63%, call 20%
					if(rndNum > 980){
						choice[0] = 2;
						choice[1] = stack;
					}else if(rndNum > 830){
						choice[0] = 2;
						choice[1] = BBsize * (Toolbox.rndNumGen(2) + 5);
					}else if (rndNum > 200){
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}else{
						choice[0] = 1;
					}
				}
			}
		}else if(handStrength == 5){
			if(stackStrength < 2){						//Small stack: all-in 75%, call/check 25%.
				if(rndNum > 750){
					choice[0] = 2;
					choice[1] = stack;
				}else{
					choice[0] = 1;
				}
			}else if(stackStrength <= 3){				//Medium stack.
				if(currentBetSize > BBsize * 7){		//Large bet: Call 4%, fold 96%
					if(rndNum > 960){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize * 3){	//Medium bet: Call 30%, fold 70%
					if(rndNum > 700){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize){		//Small bet: Re-raise double 15%, call 65%, fold 20%
					if(rndNum > 850){
						choice[0] = 2;
						choice[1] = currentBetSize * 2;
					}else if(rndNum > 200){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else{									//NPA: Raise 3BBs 65%, call/check 35%
					if(rndNum > 350){
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}else{
						choice[0] = 1;
					}
				}
			}else{										//Large stack.
				if(currentBetSize > BBsize * 7){		//Large bet: Call 10%, fold 90%
					if(rndNum > 900){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize * 3){	//Medium bet: Call 35%, fold 65%
					if(rndNum > 650){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize){		//Small bet: Re-raise double: 15%, call 85%
					if(rndNum > 850){
						choice[0] = 2;
						choice[0] = currentBetSize * 2;
					}else{
						choice[0] = 1;
					}
				}else{									//NPA: Raise 5-7 BBs 10%, raise 3BBs 60%, call 30%
					if(rndNum > 900){
						choice[0] = 2;
						choice[1] = BBsize * (Toolbox.rndNumGen(2) + 5);
					}else if(rndNum > 300){
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}else{
						choice[0] = 1;
					}
				}
			}
		}else if(handStrength == 4){
			if(stackStrength < 2){						//Small stack: all-in 65%, call/check 35%.
				if(rndNum > 350){
					choice[0] = 2;
					choice[1] = stack;
				}else{
					choice[0] = 1;
				}
			}else if(stackStrength < 4){				//Medium stack.
				if(currentBetSize > BBsize * 7){		//Large bet: fold 98%, call 2%.
					if(rndNum > 980){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize * 3){	//Medium bet: fold  85%, call 15%.
					if(rndNum > 850){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize){		//Small bet: fold 60%, call 40%.
					if(rndNum > 600){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else{									//NPA: raise 3BBs 25%, call/check 70%, fold 5%.
					if(rndNum > 750){
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}else if(rndNum > 50){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}
			}else{										//Large stack.
				if(currentBetSize > BBsize * 7){		//Large bet: fold 100%.
					choice[0] = 0;
				}else if(currentBetSize > BBsize * 3){	//Medium bet: fold 90%, call 10%.
					if(rndNum > 900){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else if(currentBetSize > BBsize){		//Small bet: re-raise (twice previous bet) 5%, call 70%, fold 25%.
					if(rndNum > 950){
						choice[0] = 2;
						choice[1] = currentBetSize * 2;
					}else if(rndNum > 250){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}else{									//NPA: bet(3BBs) 50%, bet(5-7BBs)10%, call/check 25%, fold 15%.
					if(rndNum > 900){
						choice[0] = 2;
						choice[1] = BBsize * (Toolbox.rndNumGen(2) + 5);
					}else if(rndNum > 400){
						choice[0] = 2;
						choice[1] = BBsize * 3;
					}else if(rndNum > 150){
						choice[0] = 1;
					}else{
						choice[0] = 0;
					}
				}
			}
		}else if(handStrength == 3){
			if(currentBetSize > BBsize * 5){	//Bet of 5BBs, fold.
				choice[0] = 0;
			}else if(currentBetSize > BBsize){	//Bet < 5BBs, call/check 30%, check/fold 70%.
				if(rndNum > 700){
					choice[0] = 1;
				}else{
					choice[0] = 0;
				}
			}else{								//NPA: Bet 5BBs 5%, bet 3BBs 20%, call 10%, fold 65%.
				if(rndNum > 950){
					choice[0] = 2;
					choice[1] = BBsize * 5;
				}else if(rndNum > 750){
					choice[0] = 2;
					choice[1] = BBsize * 3;
				}else if(rndNum > 650){
					choice[0] = 1;
				}else{
					choice[0] = 0;
				}
			}
		}else if(handStrength == 2){
			if(stackStrength <= 2 || currentBetSize > BBsize){	//Any raise: fold.
				choice[0] = 0;
			}else if(rndNum > 900){								//NPA: call/check 10%, raise 3BBs 3.5%, fold 86.5%.
				choice[0] = 1;
			}else if(rndNum > 865){
				choice[0] = 2;
				choice[1] = currentBetSize * 3;
			}else{
				choice[0] = 0;
			}
		}else if(handStrength == 1){
			if(stackStrength <= 2 || currentBetSize > BBsize){	//Any raise: fold.
				choice[0] = 0;
			}else if(rndNum > 950){								//NPA: call/check 5%, raise 3BBs 1%, fold 94%.
				choice[0] = 1;
			}else if(rndNum > 940){
				choice[0] = 2;
				choice[1] = currentBetSize * 3;
			}else{
				choice[0] = 0;
			}
		}
		
		try{
			Thread.sleep(3000);	//Creates delay between actions.
		}catch(InterruptedException ie){}
		
		//This is the default, later I'll also take into account opposition's playing style.
		//Make sure if option returned is fold but a check is available, the computer always takes the check.
		//Add in method to ensure computer doesn't fold if the size of the pot is already much larger than what it would take to make the call.
		//0 is fold/check, 1 is call/check, 2 is bet/raise.

		System.out.println( name + "	handStrength: " + handStrength + "	stack: " + stack);
		if(choice[0] == 0 && currentBetSize == playerCurrentBetSize){
			System.out.print("check.");
		}else if(choice[0] == 0){
			System.out.print("fold.");
		}else if(choice[0] == 1){
			System.out.print("call for " + currentBetSize + ".");
		}else if(choice[0] == 2){
			System.out.print("raise to " + choice[1] + "!");
		}//TEMP print to show computer move
		System.out.println("(" + hand.get(0) + ", " + hand.get(1) + ")");
		
		return choice;
	}//getPreFlopDecision()
	
	public double[] getFlopDecision(ArrayList<Card> communityCards, double pot, double currentBetSize, double BBsize){
		ArrayList<Card> fullHand = this.hand;
		fullHand.addAll(communityCards);
		double[] choice = new double[2];
		double potOdds, outStrength;
		
		
		//currentHandType = Toolbox.checkHand(this.hand, this.name);
		potOdds = currentBetSize / pot;		//Only use pot odds if current bet is larger than 2% of the computer's stack.
		
		if(currentHandType[0] == 10){			//Royal Flush
			//TODO
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 9){		//Straight Flush
			//TODO
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 8){		//Four-of-a-Kind
			//TODO
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 7){		//Full House
			//TODO
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 6){		//Flush
			//TODO Check if close to full House (e.g two pair or three of a kind present)
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 5){		//Straight
			//TODO Check for flush and full house.
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 4){		//Three-of-a-Kind
			//TODO Check likelihood of flush/straight.
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 3){		//Two Pair
			//TODO Check likelihood of flush/straight. Check if both parts of pair are from your hand.
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else if(currentHandType[0] == 2){		//Pair
			//TODO Check likelihood of flush/straight. Check if pocket pair, else check if one card from hand.
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}else{									//High Card
			//TODO Check likelihood of flush/straight.
			//checkStraightOuts();
			//checkFlushOuts();
			if(currentBetSize < 0.02 * stack){	//Extremely Small Bet.
				
			}else if (potOdds >= 1){			//Overbet
				
			}else if(potOdds >= 0.5){			//Medium bet
				
			}else if(currentBetSize >= BBsize * 3){	//Small-medium bet
				
			}else{								//Small bet
				
			}
		}
		
		return choice;
	}//getFlopDecision()
	
	public double[] getTurnDecision(){
		double[] choice = new double[2];
		
		return choice;
	}//getTurnDecision()
	
	public double[] getRiverDecision(){
		double[] choice = new double[2];
		
		return choice;
	}//getRiverDecision()
	
	/**
	 * Checks for the possibility of making a stronger hand depending on the amount of cards needed to make the stronger hand, the number of outs
	 * in the deck, etc.
	 * @param hand
	 * @param communityCards
	 * @param handType
	 * @return outs
	 */
	public static double checkOutStrength(ArrayList<Card> hand, ArrayList<Card> communityCards, int[] handType){
		double outStrength = 0;
		
		if(handType[0] == 10 || handType[0] == 8 || handType[0] == 7){	//Royal Flush/Four-of-a-Kind/Full House, no possibility for a higher hand.
			return 500;//TEMP 500 reps best hand
		}else if(handType[0] == 9){										//Straight Flush.
			return 499;//TEMP 499 reps second best hand 
		}else if(handType[0] == 6){										//Flush, no possibility of full house.
			//TODO Check if close to straight flush.
		}else if(handType[0] == 5){										//Straight, no possibility of full house.
			//TODO Check cards left to make flush, check if close to straight flush.
		}else{															//Three of a kind and down.
			//TODO Check if close to flush, check if close to straight, 
		}
		
		return outStrength;
	}//checkOutStrength(ArrayList<Card> hand, ArrayList<Card> communityCards)

	/**
	 * This method is intended to take a two-card hand and return a value from 1-7 that denotes the strength of that hand.
	 * @param hand
	 * @return An integer representing the strength of the hand.
	 */
	public static int getStartingHandStrength(ArrayList<Card> hand){
		int value1 = hand.get(0).getCardValue(), value2 = hand.get(1).getCardValue(), highCard, lowCard;

		if(value1 > value2){										//Assigns the high and low cards.
			highCard = value1;
			lowCard = value2;
		}else{
			highCard = value2;
			lowCard = value1;
		}
		
		if(value1 == value2){										//Pairs
			if(value1 >= 10){										//AA, KK, QQ
				return 7;
			}else if(value1 >= 8){									//JJ, 1010
				return 6;
			}else if(value1 >= 4){									//99, 88, 77, 66
				return 5;
			}else{													//55, 44, 33, 22
				return 4;
			}
		}else if(hand.get(0).getSuit() == hand.get(1).getSuit()){	//Suited
			if((highCard == 8 && lowCard < 4)
					|| ((highCard == 7 || highCard == 6) && lowCard < 3)
					|| (highCard == 5 && lowCard < 2)
					|| (highCard < 5 || lowCard == 0)){
				return 2;
			}else if(highCard < 12 && lowCard < 6){
				return 3;
			}else if(lowCard < 8){
				return 5;
			}else if(lowCard < 11){
				return 6;
			}else{
				return 7;
			}
		}else{														//Unsuited
			if(highCard < 12 && lowCard < 6){
				return 1;
			}else if(lowCard < 7){
				return 2;
			}else if(lowCard == 7){
				return 3;
			}else if(lowCard == 8 && highCard < 12){
				return 4;
			}else if(lowCard < 11){
				return 5;
			}else{
				return 6;
			}
		}
	}//getStartingHandStrength()

	/**
	 * Goes through player's hand and the community cards and determines the appropriate straight possibility strength representation based on the
	 * following guide:
	 * 
	 * 2 cards from hand		14
	 * 1 cards from hand		12
	 * 0 cards from hand		3
	 * 
	 * Gut shot, 4 cards:	
	 * 2 cards from hand		10
	 * 1 cards from hand		8
	 * 0 cards from hand		2
	 * 
	 * Open ended, 3 cards:	
	 * 2 cards from hand		6
	 * 1 cards from hand		4
	 * 0 cards from hand 		1
	 * 
	 * anything else		-1
	 * 
	 * @param hand
	 * @param communityCards
	 * @return straightStrength
	 */
	public static int checkStraightOuts(ArrayList<Card> hand, ArrayList<Card> communityCards){
		ArrayList<Integer> values = new ArrayList<Integer>();
		int strength = 0, type = 0, bestStartValue = 0, numCards, highNumCards = 0, highHandContribution = 0, handCardsInvolved = 0;
		
		values.add(hand.get(0).getCardValue());
		values.add(hand.get(1).getCardValue());
		for(int i = 0; i < communityCards.size(); i++){
			values.add(communityCards.get(i).getCardValue());
		}
		
		for(int i = 0; i < values.size(); i++){		//Checks if next five cards in sequence exist.
			numCards = 1;
			if(values.contains(i + 1)){
				numCards++;
			}
			if(values.contains(i + 2)){
				numCards++;
			}
			if(values.contains(i + 3)){
				numCards++;
			}
			if(values.contains(i + 4)){
				numCards++;
			}
			
			if(numCards >= highNumCards){
				for(int j = i; j < i + 5; j++){
					if(values.indexOf(j) < 3){
						highHandContribution = j;
					}
				}
				highNumCards = numCards;
				bestStartValue = i;
			}
		}
		
		if(highNumCards == 4 && values.contains(bestStartValue + 1) && values.contains(bestStartValue + 2) && 	//Open ended, 4 cards
				values.contains(bestStartValue + 3) && (values.contains(bestStartValue) || values.contains(bestStartValue + 4))){
			type = 1;
		}else if(highNumCards == 4){										//Gut shot, 4 cards.
			type = 2;
		}else if(highNumCards == 3 && values.contains(bestStartValue + 2)){	//Open ended, 3 cards.
			if((values.contains(bestStartValue + 1) && values.contains(bestStartValue + 3)) ||
					(values.contains(bestStartValue + 1) && values.contains(bestStartValue)) ||
							(values.contains(bestStartValue + 3) && values.contains(bestStartValue + 4))){
				type = 3;
			}
		}else{
			strength = -1;
		}
		
		for(int i = bestStartValue; i < bestStartValue + 5; i++){
			if(values.indexOf(i) < 3){
				handCardsInvolved++;
			}
		}
		
		
		
		if(type == 3){
			if(handCardsInvolved == 2){
				strength = 6;
			}else if(handCardsInvolved == 1){
				strength = 4;
			}else{
				strength = 1;
			}	
		}else if(type == 2){
			if(handCardsInvolved == 2){
				strength = 10;
			}else if(handCardsInvolved == 1){
				strength = 8;
			}else{
				strength = 2;
			}	
		}else if(type == 1){
			if(handCardsInvolved == 2){
				strength = 14;
			}else if(handCardsInvolved == 1){
				strength = 12;
			}else{
				strength = 3;
			}	
		}
		
		if(highHandContribution - bestStartValue > 3){
			strength++;
		}
		return strength;
	}//checkStraightOuts()
	
	/**
	 * Goes through player's hand and the community cards and determines the appropriate flush possibility strength representation based on the following guide:
	 * 
	 * 4 cards of the same suit:
	 * 2 cards from hand		
	 * 1 card from hand		
	 *
	 * 3 cards of the same suit:
	 * 2 cards from hand		2
	 * 1 card from hand			1
	 * 
	 * anything else			-1
	 * 
	 * @param hand
	 * @param communityCards
	 * @return flushStrength
	 */
	public static int checkFlushOuts(ArrayList<Card> hand, ArrayList<Card> communityCards){
		int flushStrength = 0, numClubs = 0, numDiamonds = 0, numHearts = 0, numSpades = 0, clubsInHand = 0, diamondsInHand = 0, heartsInHand = 0, spadesInHand = 0;
		
		for(int i = 0; i < 2; i++){
			if(hand.get(i).getSuit() == 0){
				clubsInHand++;
				numClubs++;
			}else if(hand.get(i).getSuit() == 1){
				diamondsInHand++;
				numDiamonds++;
			}else if(hand.get(i).getSuit() == 2){
				heartsInHand++;
				numHearts++;
			}else if(hand.get(i).getSuit() == 3){
				spadesInHand++;
				numSpades++;
			}
		}
		for(int i = 0; i < communityCards.size(); i++){
			if(communityCards.get(i).getSuit() == 0){
				numClubs++;
			}else if(communityCards.get(i).getSuit() == 1){
				numDiamonds++;
			}else if(communityCards.get(i).getSuit() == 2){
				numHearts++;
			}else if(communityCards.get(i).getSuit() == 3){
				numSpades++;
			}
		}
		
		if(numClubs == 4 || numDiamonds == 4 || numHearts == 4 || numSpades == 4){
			if(numClubs == 4){
				if(clubsInHand == 2){
					flushStrength = 4;
				}else if(clubsInHand == 1){
					flushStrength = 3;
				}else{
					flushStrength = -1;
				}
			}else if(numDiamonds == 4){
				if(clubsInHand == 2){
					flushStrength = 4;
				}else if(clubsInHand == 1){
					flushStrength = 3;
				}else{
					flushStrength = -1;
				}
			}else if(numHearts == 4){
				if(clubsInHand == 2){
					flushStrength = 4;
				}else if(clubsInHand == 1){
					flushStrength = 3;
				}else{
					flushStrength = -1;
				}
			}else if(numSpades == 4){
				if(clubsInHand == 2){
					flushStrength = 4;
				}else if(clubsInHand == 1){
					flushStrength = 3;
				}else{
					flushStrength = -1;
				}
			}
		}else if(numClubs == 3 || numDiamonds == 3 || numHearts == 3 || numSpades == 3){
			if(numClubs == 4){
				if(clubsInHand == 2){
					flushStrength = 2;
				}else if(clubsInHand == 1){
					flushStrength = 1;
				}else{
					flushStrength = -1;
				}
			}else if(numDiamonds == 4){
				if(clubsInHand == 2){
					flushStrength = 2;
				}else if(clubsInHand == 1){
					flushStrength = 1;
				}else{
					flushStrength = -1;
				}
			}else if(numHearts == 4){
				if(clubsInHand == 2){
					flushStrength = 2;
				}else if(clubsInHand == 1){
					flushStrength = 1;
				}else{
					flushStrength = -1;
				}
			}else if(numSpades == 4){
				if(clubsInHand == 2){
					flushStrength = 2;
				}else if(clubsInHand == 1){
					flushStrength = 1;
				}else{
					flushStrength = -1;
				}
			}
		}else{
			flushStrength = -1;
		}
		
		return flushStrength;
	}//checkFlushOuts()
	
}//Computer

/**
 * Use bet sizing relative to the size of the pot(x < half pot is small, x >= half stack is medium, x > stack is overbet)
 */
