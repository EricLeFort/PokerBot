package pokerClient;
/**
 * @author Eric Le Fort
 * @version 1
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import tools.*;

public class PokerBot{
	private static final String fileLocation = "PokerPlayers.txt";
	private static Card[] deck;
	private static ArrayList<Card> communityCards = new ArrayList<Card>();
	private static ArrayList<Player> table = new ArrayList<Player>();
	private static ArrayList<Human> pastPlayers = new ArrayList<Human>();
	private static ArrayList<BigDecimal> pot = new ArrayList<BigDecimal>();
	private static String newline = System.getProperty("line.separator"), playerName;
	private static double currentBetSize = 0, BBsize;
	private static int numCom, startStack, playersInHand = 0, playersRemaining, BBposition = 0, playersAllIn = 0, playerPosition;
	
	public static void main(String[] args){	
		preparePlayerList();
		int choice;
		boolean gameOn = true;
		pot.add(new BigDecimal("0.00"));
		
		if(pastPlayers.size() == 0){		//first time playing, no saved players.
			userSettingsSelection(true, true);
		}else{
			choice = JOptionPane.showConfirmDialog(null, "Would you like to play as a returning player?", "Player Selection", JOptionPane.YES_NO_OPTION);
			if(choice == 0){				//Yes selected.
				choice = choosePlayer();
				
				if(choice == -1){			//Chose to go back and create new player.
					userSettingsSelection(false, true);
				}else{
					userSettingsSelection(false, false);
				}
				
			}else{							//No selected.
				userSettingsSelection(false, true);
			}
		}
		
		/**
		 * This marks the beginning of dealing hands to users and computers and carrying on with play.
		 */
		
		while(gameOn){
			gameOn = deal();
			
			System.out.println("Remaining Players:");
			for(int i = 0; i < table.size(); i++){
				if(i != table.size() - 1 && table.get(i).getInPlay()){
					System.out.println("Computer # " + i + ":");
					System.out.println(table.get(i).getHand().get(0));
					System.out.println(table.get(i).getHand().get(1));
				}else if(table.get(i).getInPlay()){
					System.out.println("Player:");
					System.out.println(table.get(i).getHand().get(0));
					System.out.println(table.get(i).getHand().get(1));
				}
			}//TEMP print to display hands.

			System.out.println("Players that aren't in the hand:");
			for(int i = 0; i < table.size(); i++){
				if(i != table.size() - 1 && !table.get(i).getInPlay()){
					System.out.println("Computer # " + i + ":");
					System.out.println(table.get(i).getHand().get(0));
					System.out.println(table.get(i).getHand().get(1));
				}else if(!table.get(i).getInPlay()){
					System.out.println("Player:");
					System.out.println(table.get(i).getHand().get(0));
					System.out.println(table.get(i).getHand().get(1));
				}
			}//TEMP print to display hands.
			System.out.println();	//TEMP
		}//while(gameOn)
	}//main
 	
	/**
	 * Sets the size of the blinds relative to the size of the starting stacks of the players.
	 * @return BBsize
	 */
	private static double setBBSize(){
		double BBsize;
		
		if(startStack >= 500000){
			BBsize = 50000;
		}else if(startStack >= 250000){
			BBsize = 20000;
		}else if(startStack >= 100000){
			BBsize = 10000;
		}else if(startStack >= 50000){
			BBsize = 2000;
		}else if(startStack >= 10000){
			BBsize = 500;
		}else if(startStack >= 5000){
			BBsize = 200;
		}else if(startStack >= 1000){
			BBsize = 100;
		}else if(startStack >= 500){
			BBsize = 50;
		}else{
			BBsize = 30;
		}
		return BBsize;
	}//setBBSize()

	/**
	 * "Deals" to player and computers by taking the top 2 cards off of deck and assigning those cards to the respective 
	 * player or computers current hand. Returns whether or not there's enough players to continu
	 * @return gameOn
	 */
	public static boolean deal(){
		boolean gameOn = true;
		playersInHand = 0;
		deck = Toolbox.getShuffledDeck();

		for(int i = 0; i < table.size(); i++){	//Checks how many players still have stacks and resets their inPlay status, deals hands to each remaining player.
			if(table.get(i).getStack() != 0){
				//table.get(i).setHand(deck.remove(0), deck.remove(0));
				playersInHand++;
				table.get(i).setInPlay(true);
			}else if(i == playerPosition){		//If the index is that of the player and they happen to be out of chips, game ends. 
				gameOn = false;
			}else{
				table.get(i).setInPlay(false);	//Sets the computer to be out of play if they're out of chips.
			}
		}
		
		if(gameOn){
			playersRemaining = playersInHand;
			playHand();							//Runs through the rounds of betting and ensures that the hand is still in progress
		}
		
		if(playersRemaining == 1){				//If there's only one player left, they win! In theory, this should only happen when the player wins.
			gameOn = false;						//Later add a button where the user can exit manually.
		}
		
		return gameOn;
	}//deal()

	/**
	 * Alters the ArrayList of Computer objects stored in "table" based on an amount of computers specified by the user as
	 * well as the stack size that they should start off with.
	 * @param numCom, newPlayer, startStack
	 * @param 
	 */
	public static void createTable(){
		table.clear();
		for(int i = 0; i < numCom; i++){
			table.add(new Computer(i, startStack));
		}
		table.add(new Human(playerName, startStack));
		playerPosition = table.size() - 1;
	}//createTable()
	
	/**
	 * Operates the same as the other createTable method except uses a player profile that has previously been used.
	 * @param player
	 */	
	public static void createTable(Human player){
		table.clear();
		for(int i = 0; i < numCom; i++){
			table.add(new Computer(i, startStack));
		}
		table.add(player);
		playerPosition = table.size() - 1;
	}//createTable(Human player)
	
	/**
	 * Allows the user to input their name and will handle any exceptions that might happen. Handles possible 
	 * exceptions as well
	 * @return name
	 */
	public static String setName(){
		String name = "";
		int count = 0;
		boolean valid = false;

		do{
			try{
				if(count == 0){
					count++;
					name = JOptionPane.showInputDialog(null, "Hello and welcome to the Poker Bot! What is your name?").trim();
				}else{
					name = JOptionPane.showInputDialog(null, "Please keep your name between 1-30 characters and avoid the use of commas.").trim();
				}

				if(name != null && !name.equals("") && name.length() <= 30 && !name.contains(",")){
					valid = true;
				}

				for(int i = 0; i < pastPlayers.size(); i++){
					if(valid && pastPlayers.get(i).getName().toUpperCase().equals(name.toUpperCase().trim())){
						JOptionPane.showMessageDialog(null, "That name has already been chosen, please choose another.", "Username Taken", JOptionPane.INFORMATION_MESSAGE);
						valid = false;
						i = pastPlayers.size();
					}
				}
				
			}catch(NullPointerException npe){
				System.out.println(npe.getMessage());
				System.exit(-1);
			}
		}while(!valid);
		
		pastPlayers.add(new Human(name, 0));
		saveData();
		return name;
	}//setName()
	
	/**
	 * Allows the user to select the amount of opponents they wish to face between 1 and 8, ensures input is in 
	 * this range and handles possible exceptions.
	 * @param name
	 * @return numCom
	 */
	public static int setCom(String name){
		int numCom = 0, count = 0;
		boolean valid = false;
		
		do{
			try{
				if(count == 0){
					count++;
					numCom = Integer.parseInt(JOptionPane.showInputDialog(null, "Okay, " + playerName + " how many adversaries would you like to be put up against? (between 1-8)"));
				}else{
					numCom = Integer.parseInt(JOptionPane.showInputDialog(null, "Error!", "Please enter a number between 1-8."));
				}//TEMP use this form for invalid input or the one used in setStartStack()? Just a preference issue really.
			}catch(Exception e){}
			if(numCom < 9 && numCom > 0){
				valid = true;
			}
		}while(!valid);
		return numCom;
	}
	
	/**
	 * Sets the size of the start stack based on what the user specifies. Handles possible exceptions as well.
	 * @return startStack
	 */
	public static int setStartStack(){
		int startStack = 0, count = 0;
		boolean valid = false;
		
		do{			
			try{	
				if(count == 0){
					count++;
					startStack = Integer.parseInt(JOptionPane.showInputDialog(null, "Sounds good, and what would you"
							+ "like your starting stacks to be? (between $50 and $1,000,000).").trim());
				}else{
					startStack = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a number that is between $50 and $1,000,000."));
				}
				
				if(startStack >= 50 && startStack <= 1000000){
					valid = true;
				}else if(startStack < 0){
					System.out.println("It wouldn't be advisable to start with a negative stack!");
				}

			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Make sure you're not entering anything weird!", "Oops!", JOptionPane.ERROR_MESSAGE);
			}
		}while(!valid);
		return startStack;
	}//setStartStack()
	
	/**
	 * Checks to see which players are still in the hand and determines who has won. If 20 is returned there is not yet a winner.
	 * Otherwise the index of the winning player is returned.
	 */
	public static ArrayList<Integer> checkWinner(){
		ArrayList<Card> fullHand = new ArrayList<Card>();
		ArrayList<Integer> winningIndices = new ArrayList<Integer>();
		int[] highHand = new int[2];
		
		winningIndices.add(20);

		for(int i = 0; i < table.size(); i++){			//Checks all players remaining and allocate winning index to the player with the highest hand.
			Player player = table.get(i);
			int[] currentHandType = new int[2];
			if(playersInHand == 1 && player.getInPlay()){
				winningIndices.add(i);
			}else if(player.getInPlay()){				//Checks if player is in play.
				fullHand.clear();
				fullHand.addAll(communityCards);
				fullHand.addAll(player.getHand());
				//currentHandType = Toolbox.checkHand(fullHand, playerName);
				if(currentHandType[0] > highHand[0]){	//Checks if the type of set is stronger.
					winningIndices.clear();
					winningIndices.add(i);
				}else if(currentHandType[0] == highHand[0] && currentHandType[1] > highHand[1]){	//Checks the specific type of set.
					winningIndices.clear();
					winningIndices.add(i);
				}else if(currentHandType[0] == highHand[0] && currentHandType[1] == highHand[1]){	//Checks for kickers.
					if(currentHandType[1] > 5 && currentHandType[1] != 8){			//flushes, straights and full houses don't need to be checked for kickers.
						winningIndices.add(i);
					}else{
						if(currentHandType[2] > highHand[2]){
							winningIndices.clear();
							winningIndices.add(i);
						}else if(currentHandType[2] == highHand[2]){
							winningIndices.add(i);
						}
					}
				}
			}
		}

		return winningIndices;
	}//checkWinner()
	
	/**
	 * Goes through the various different rounds of play in a hand and checks throughout to verify if there's still enough players still in to
	 * continue the hand.
	 * @return winningIndex
	 */
	public static void playHand(){
		ArrayList<Integer> winningIndices = new ArrayList<Integer>();
		int startIndex = 0, lastIndex = table.size() - 1, playersAllIn = 0;
		currentBetSize = BBsize;
		communityCards.clear();
		
		if(BBposition == lastIndex){							//Changes the index if it's trying to refer to an element past the end of the list.
			startIndex = 0;
		}else{
			startIndex = BBposition + 1;
		}
			
		table.get(BBposition).setCurrentBetSize(BBsize);		//This chunk posts the Small and Big blinds.
		if(table.get(BBposition).getAllIn()){
			playersAllIn++;
		}
		if(BBposition == 0){
			table.get(lastIndex).setCurrentBetSize(BBsize / 2);
			if(table.get(lastIndex).getAllIn()){
				playersAllIn++;
			}
		}else{
			table.get(BBposition - 1).setCurrentBetSize(BBsize / 2);
			if(table.get(BBposition - 1).getAllIn()){
				playersAllIn++;
			}
		}
		
		currentBetSize = BBsize;
		bettingRound(startIndex, 0);
		
		/*
		 * Beginning of flop.
		 */
		
		if(playersInHand > 1){
			System.out.println("Flop:  Pot: " + pot);
			for(int i = 0; i < 3; i++){							//Deals the next three cards from the deck to represent the flop.
				//communityCards.add(deck.remove(0));
			}
			printCommunityCards();
		}
		if(playersInHand > 1 && playersAllIn < playersInHand - 1){
			currentBetSize = 0;

			if(BBposition == 0){								//Changes the index if it's trying to refer to an element past the end of the list.
				startIndex = lastIndex;
			}else{
				startIndex = BBposition - 1;
			}
			
			bettingRound(startIndex, 1);
		}
		
		/*
		 * Beginning of turn.
		 */
		
		if(playersInHand > 1){
			System.out.println("Turn:  Pot: " + pot);
			//communityCards.add(deck.remove(0));
			printCommunityCards();
		}
		
		if(playersInHand > 1 && playersAllIn < playersInHand - 1){
			currentBetSize = 0;
			bettingRound(startIndex, 2);
		}
		
		/*
		 * Beginning of river.
		 */
		
		if(playersInHand > 1){
			System.out.println("River:  Pot: " + pot);
			//communityCards.add(deck.remove(0));
			printCommunityCards();
		}
		
		if(playersInHand > 1 && playersAllIn < playersInHand - 1){
			currentBetSize = 0;
			bettingRound(startIndex, 3);
		}

		winningIndices = checkWinner();									//Checks for who has won the hand and then shifts the BBposition by one spot.
		
		if(winningIndices.size() == 1){
			table.get(winningIndices.get(0)).alterStack(pot.get(0).intValue());
		}else{															//If there's more than one winner, they all get a share.
			ArrayList<BigDecimal> playerShares = new ArrayList<BigDecimal>();
			BigDecimal partialPot = pot.get(0).divide(new BigDecimal(winningIndices.size())), actual = new BigDecimal("0.00"), difference = new BigDecimal("0.00");		
			
			for(int i = 0; i < winningIndices.size(); i++){
				playerShares.add(partialPot);
				actual = actual.add(partialPot);
			}
			difference = actual.subtract(pot.get(0));
			
			if(!difference.equals(new BigDecimal("0.00"))){				//Some remainder left over.
				for(int i = 0; i < winningIndices.size(); i++){
					playerShares.set(i, playerShares.get(i).subtract(new BigDecimal("0.01")));
				}
				for(int i = 0; i <= difference.multiply(new BigDecimal("100.00")).intValue(); i++){	//Figures out how many extra pennies to dish out.
					playerShares.set(i, playerShares.get(i).add(new BigDecimal("0.01")));
				}
			}
			
			for(int i = 0; i < winningIndices.size(); i++){				//Adds the appropriate amount to all those with a share of the pot.
				table.get(winningIndices.get(i)).alterStack(playerShares.get(i).intValue());
			}
		}
		
		
		pot = new ArrayList<BigDecimal>();
		pot.add(new BigDecimal("0.00"));
		if(BBposition == lastIndex){
			BBposition = 0;
		}else{
			BBposition++;
		}
	}//playHand()

	/**
	 * Gets input from either computer AI or player as to what action they would like to take.
	 * 0 - Fold
	 * 1 - Check
	 * 2 - Call
	 * 3 - Bet/Raise
	 * 4 - All-In
	 */
	public static void humanChoice(Human player, int index){
		String message;
		double[] playerChoice = {10, 0};
		int confirm;
		boolean valid = false, goodInput = false;

		System.out.println("Your Hand:");
		System.out.println(player.getHand().get(0));
		System.out.println(player.getHand().get(1));

		if(currentBetSize == 0 || player.getCurrentBetSize() == currentBetSize){
			message = "Enter:" + newline + "0 to fold," + newline + "1 to check," + newline + "2 to raise," + newline + "3 to go All-In." + newline;
		}else if(currentBetSize >= player.getStack()){
			message = "Enter:" + newline + "0 to fold," + newline + "1 to call for $"+ player.getStack() + " and go all in" + newline;
		}else{
			message = "Enter:" + newline + "0 to fold," + newline + "1 to call for " + currentBetSize + newline + "2 to raise," + newline + 
					"3 to go All-In." + newline;
		}

		System.out.println(message + playerName + ", make your choice. (Stack: " + player.getStack() +")");

		do{
			try{
				playerChoice[0] = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number corresponding to the action you wish to make.", 
						"Action is on you", JOptionPane.QUESTION_MESSAGE));
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Make sure you're not entering anything weird!", "Oops!", JOptionPane.ERROR_MESSAGE);
			}

			if(playerChoice[0] >= 4 || playerChoice[0] <= -1){
				System.out.println("Sorry for being a pain, but please enter a number between 0-3.");
			}else if(playerChoice[0] == 0){						//player folds.
				valid = true;
				playersInHand--;
				player.fold();
			}else if(playerChoice[0] == 1){						//player calls/checks.
				valid = true;
				player.setCurrentBetSize(currentBetSize);
				if(player.getAllIn()){
					playersAllIn++;
				}
			}else if(playerChoice[0] == 2){						//Player raises.
				playerChoice[1] = ((Human)player).getBetSize(currentBetSize);
				if(playerChoice[1] != -1){
					currentBetSize = playerChoice[1];
					valid = true;
				}
			}else if(playerChoice[0] == 3){						//Player all in, then confirms this is their decision.
				do{
					confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm your choice", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(confirm == 0){							////Player chose yes.
						System.out.println("You are now all in.");
						player.allIn();
						playersAllIn++;
						if(player.getCurrentBetSize() > currentBetSize){
							currentBetSize = player.getCurrentBetSize();
						}
						valid = true;
						goodInput = true;
					}else if(confirm == 1){						//Player chose no.
						goodInput = true;
						playerChoice[1] = -1;
					}else{
						System.out.println("Umm anyone know what went wrong there..?"); 	//TEMP
					}
				}while(!goodInput);
			}
			if(playerChoice[1] == -1){						//Indicates that the player has changed their mind and would like to choose a different option.
				valid = false;
			}
		}while(!valid);
		//Later on it'll be easy to set pressing certain buttons as changing the int representation of fold/check/raise/call or whatever else.
		//For now it can simply be input from the user. Removes options if they aren't possible (e.g. checking if there was already a bet placed).
	}//playerChoice()

	/**
	 * Returns the index of the winning player if there is one or returns the value 20 otherwise. The values of round correspond to 
	 * whether it's pre-flop, flop, turn or river with 0, 1, 2, 3 respectively.
	 * @param startIndex, round
	 * @return winningIndex
	 */
	public static void bettingRound(int startIndex, int round){
		Player currentPlayer;
		double[] comChoice = {0, 0};
		int currentIndex = startIndex, lastIndex = table.size() - 1;
		boolean roundOn = true, BBaction = false;
		
		do{
			if(playersInHand == 1){							//Ends round if only one person is left; that person will win the round.
				roundOn = false;
			}else if(playersAllIn == playersInHand - 1){	//Checks to see if all players but one are all in, ends hand if that last person already called.
				for(int i = 0; i < table.size(); i++){
					if(table.get(i).getCurrentBetSize() == currentBetSize && !table.get(i).getAllIn() && table.get(i).getInPlay()){
						roundOn = false;
					}
				}
			}else if(playersAllIn > playersInHand - 1){		//All players all in.
				roundOn = false;
			}else if(table.get(currentIndex).getCurrentBetSize() == currentBetSize && BBaction){	//Checks if the player has already matched the current bet, this indicates the betting round is finished.
				roundOn = false;
			}
			
			currentPlayer = table.get(currentIndex);
			
			if(currentIndex == BBposition){					//Ensures Big Blind gets to act before the flop if all others only call.
				BBaction = true;
			}
			
			if(currentPlayer.getInPlay() && playersInHand > 1 && roundOn && !currentPlayer.getAllIn()){
				if(currentPlayer instanceof Human){
					humanChoice((Human)currentPlayer, currentIndex);
				}else{
					if(round == 0){
						comChoice = ((Computer) currentPlayer).getPreFlopDecision(BBposition, currentIndex, currentBetSize, BBsize, table);
					}else if(round == 1){
						//comChoice = getFlopDecision(communityCards, pot, currentBetSize, BBsize);
					}else if(round == 2){
						//comChoice = getTurnDecision
					}else{
						//comChoice = getRiverDecision
					}

					if(comChoice[0] == 0 && currentBetSize == currentPlayer.getCurrentBetSize()){			//Handles action performed by the computer.
						//TEMP 1playerChecks.. no information needs to be handled.
					}else if(comChoice[0] == 0){
						currentPlayer.fold();
						playersInHand--;
					}else if(comChoice[0] == 1){
						playersAllIn = currentPlayer.call(currentBetSize, playersAllIn);
					}else if(comChoice[0] == 2){
						currentPlayer.setCurrentBetSize(comChoice[1]);
						if(currentPlayer.getAllIn()){
							playersAllIn++;
						}
						currentBetSize = currentPlayer.getCurrentBetSize();
					}
				}

			}
			
			if(currentIndex == lastIndex){
				currentIndex = 0;
			}else{
				currentIndex++;
			}
		}while(roundOn);
		
		for(int i = 0; i < table.size(); i++){				//Adds all bets to the pot and resets all the players' current bets to be 0.
			pot.set(0, pot.get(0).add(new BigDecimal(Double.toString(table.get(i).getCurrentBetSize()))));
			table.get(i).setCurrentBetSize(0);
		}
		
	}//bettingRound(int startIndex)
	
	public static void preparePlayerList(){
		BufferedReader lineReader;
		Scanner reader = null;
		String name, playingStyle, line = "";
		int handsSeen, handsSeenBB, handsSeenSB, flopsSeen, flopsSeenBB, flopsSeenSB, showdownsWon, showdownsSeen;
		
		try{									
			lineReader = new BufferedReader(new FileReader(fileLocation));
			
			while((line = lineReader.readLine()) != null ){							//Populate humans with the data in the pokerPlayers.txt file
				reader = new Scanner(line);
				reader.useDelimiter(",");
				name = reader.next();
				playingStyle = reader.next();
				handsSeen = Integer.parseInt(reader.next());
				handsSeenBB = Integer.parseInt(reader.next());
				handsSeenSB = Integer.parseInt(reader.next());
				flopsSeen = Integer.parseInt(reader.next());
				flopsSeenBB = Integer.parseInt(reader.next());
				flopsSeenSB = Integer.parseInt(reader.next());
				showdownsWon = Integer.parseInt(reader.next());
				showdownsSeen = Integer.parseInt(reader.next());
				
				pastPlayers.add(new Human(name, playingStyle, 500, handsSeen, handsSeenBB, handsSeenSB, 
						flopsSeen, flopsSeenBB, flopsSeenSB, showdownsWon, showdownsSeen));
				reader.close();
			}
			lineReader.close();
			
			saveData();
		}catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
			System.out.println("The file at: " + fileLocation + " was not found.");
		}catch(IOException ioe){
			System.out.println(ioe);
		}
	}//preparePlayerList()
	
	public static int choosePlayer(){
		int choice = 0;
		boolean valid = false;
		
		System.out.println("0. Create a new player.");
		for(int i = 0; i < pastPlayers.size(); i++){
			System.out.println((i + 1) + ". " + pastPlayers.get(i).getName());
		}
		System.out.println("Choose the number corresponding to the user you wish to play as.");

		do{
			try{
				choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number corresponding to the player you wish to play as.", 
						"Choose your player", JOptionPane.QUESTION_MESSAGE));
				
				if(choice <= pastPlayers.size() && choice  > 0){
					playerName = pastPlayers.get(choice - 1).getName();
					valid = true;
				}else if(choice == 0){
					valid = true;
				}else{
					JOptionPane.showMessageDialog(null, "Please choose a value that is displayed.", "Something didn't add up.", JOptionPane.PLAIN_MESSAGE);
				}
			}catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "Make sure you're not entering anything weird!", "Oops!", JOptionPane.ERROR_MESSAGE);
			}
		}while(!valid);
		return choice - 1;
	}
	
	public static void saveData(){
		BufferedWriter writer = null;
		String line;
		try{
			writer = new BufferedWriter(new FileWriter(fileLocation));
			for(int i = 0; i < pastPlayers.size(); i++){							//Writes back to the file to reduce likelihood of lost data.
				line = pastPlayers.get(i).getName() + "," + pastPlayers.get(i).getPlayingStyle() + "," +
						pastPlayers.get(i).getHandsSeen() + "," + pastPlayers.get(i).getHandsSeenBB() +
						"," + pastPlayers.get(i).getHandsSeenSB() + "," + pastPlayers.get(i).getFlopsSeen() +
						"," + pastPlayers.get(i).getFlopsSeenBB() + "," + pastPlayers.get(i).getFlopsSeenSB() +
						"," + pastPlayers.get(i).getShowdownsWon() + "," + pastPlayers.get(i).getShowdownsSeen();
				writer.write(line);
				writer.newLine();
			}
			writer.close();

		}catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
			System.out.println("The file at: " + fileLocation + " was not found.");
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try {writer.close();} catch (Exception ex) {}
		}
	}//saveData(ArrayList<Human> humans)
	
	public static void userSettingsSelection(boolean firstPlay, boolean createNewPlayer){
		if(firstPlay){
			System.out.println("Hello and welcome to the PokerBot! I hope you enjoy the game and maybe even learn a thing or two!");
		}else{
			System.out.println("Welcome back to the PokerBot!");
		}
		if(createNewPlayer){
			playerName = setName(); //"Eric"; //
		}
		numCom = setCom(playerName); //3; //
		startStack = setStartStack(); //500; //
		BBsize = setBBSize();
		createTable();
		playersInHand = table.size();
		playersRemaining = table.size();
		
		if(createNewPlayer || firstPlay){
			System.out.println("Hello " + playerName + ", Good Luck!");
		}else{
			System.out.println("Hello again " + playerName + ", Good Luck!");
		}
	}
	
	public static void printCommunityCards(){
		System.out.println("Community Cards:");
		for(int i = 0; i < communityCards.size(); i++){
			System.out.println(communityCards.get(i));
		}
		System.out.println();
	}//printCommunityCards()
	
}//PokerBot

/**
 * Finish player object.
 * Create a section to keep track of player stats. Updates after each hand finishes. 
 * 
 * Eventually make a way to incorporate the GUI with the program.
 * 
 * Working on:
 * 		Make program able to handle side pots and all that sort of thing.		
 * 
 * 		playHand() method and just the actual hand-by-hand play in general, sooooo many random issues..
 * 	
 * Bug List:
 * 		None in specific for now! :)
 */