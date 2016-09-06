package handStorage;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

import tools.Card;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public abstract class HandDataParser{
	
	/**
	 * Parses a file containing the data of a given PokerStars cash game hand and returns a <code>Hand</code> object that
	 * contains the information. The input file must follow the format provided by PokerStars as of August 2016.
	 * The format is as follows:
	 * 
	 * PokerStars Hand #HAND_ID:  GAME_TYPE ($SB/$BB CURRENCY) - YYYY/MM/DD HH:MM:SS TIMEZONE
	 * Table 'TABLE_NAME' TABLE_TYPE Seat #BUTTON_POSITION is the button
	 * Seat 1: PLAYER1_NAME ($STACK1 in chips) is sitting out			//is sitting out is optional
	 * Seat N: PLAYERN_NAME ($STACKN in chips)							//From 1-N for however many players
	 * PLAYER#_NAME: posts the ante $ANTE								//Only included if there's an ante greater than 0.
	 * PLAYER#_NAME: posts small blind $SB
	 * PLAYER#_NAME: posts big blind $BB
	 * *** HOLE CARDS ***
	 * Dealt to PLAYER#_NAME [VALUE_SUIT VALUE_SUIT]					//For as many as are known?
	 * PLAYER#_NAME: ACTION												//For as many as necessary
	 * *** FLOP *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT]
	 * PLAYER#_NAME: ACTION												//For as many as necessary
	 * *** TURN *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT] [VALUE_SUIT]
	 * PLAYER#_NAME: ACTION												//For as many as necessary
	 * *** RIVER *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT] [VALUE_SUIT]
	 * PLAYER#_NAME: ACTION												//For as many as necessary
	 * *** SHOW DOWN ***
	 * PLAYER#_NAME: shows [VALUE_SUIT VALUE_SUIT] (HAND_TYPE)			//For all that show
	 * PLAYER#_NAME: mucks hand
	 * PLAYER#_NAME: collected $WINNINGS from pot						//For each player that won money
	 * *** SUMMARY ***
	 * Total pot $POT | Rake $RAKE
	 * Board [VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT]
	 * Seat 1: PLAYER_NAME (button) folded TIME
	 * Seat 2: PLAYER_NAME (small blind) showed [VALUE_SUIT VALUE_SUIT] and lost with HAND_TYPE
	 * Seat 3: PLAYER_NAME (big blind) showed [VALUE_SUIT VALUE_SUIT] and won ($WINNINGS) with HAND_TYPE
	 * Seat 4: PLAYER_NAME mucked [VALUE_SUIT VALUE_SUIT]
	 * Seat 5: PLAYER_NAME folded TIME
	 * Seat 6: PLAYER_NAME folded TIME	
	 * 
	 * @param file - The file to be parsed.
	 * @return A <code>Hand</code> containing all necessary information about this cash game poker hand.
	 */
	public static Hand readCashGameHandFromFile(File file){
		MainInfo mainInfo;
		ActionSummary actionSummary;
		ShowdownInfo[] showdownSection;
		HandSummary handSummary;
		PlayerInfo playerInfo;
		ArrayList<ShowdownInfo> showdownInfo = new ArrayList<ShowdownInfo>();
		ArrayList<PlayerActionSummary> playerActionSummaries = new ArrayList<PlayerActionSummary>();
		ArrayList<Action> holeCardActions = new ArrayList<Action>(),
				flopActions = new ArrayList<Action>(),
				turnActions = new ArrayList<Action>(),
				riverActions = new ArrayList<Action>();
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<Double> stacks = new ArrayList<Double>();
		ArrayList<Boolean> inHand = new ArrayList<Boolean>();
		Card knownHands[][], board[] = new Card[5], hand[] = new Card[2];
		Date date;
		Currency currency;
		FoldTime foldTime;
		String current, gameType, timezone, tableName, tableType, player, splitLine[], handType;
		long handID;
		double sb, bb, ante, amount, pot, rake, primStacks[];
		int year, month, day, hour, min, sec, buttonPosition, sbPosition, bbPosition, numInHand = 0, currentSeat = 1, temp1;
		boolean won, showdownReached, primInHand[];
		
		try{
			Scanner reader = new Scanner(file);
			reader.useDelimiter(" ");
			
			reader.next();									//"PokerStars Hand #HAND_ID:"
			reader.next();
			current = reader.next();
			handID = Long.parseLong(current.substring(1, current.length() - 1));
			
			gameType = "";
			reader.next();
			while(!(current = reader.next()).contains("/")){
				gameType += current + " ";					//"GAME_TYPE"
			}
			gameType = gameType.substring(0, gameType.length() - 1);

															//"($SB/$BB CURRENCY)"
			sb = Double.parseDouble(current.substring(2, current.indexOf('/')));
			bb = Double.parseDouble(current.substring(current.indexOf('/') + 2));
			current = reader.next();
			currency = Currency.valueOf(current.substring(0, current.length() - 1));
			
			reader.next();
			current = reader.next();						//"YYYY/MM/DD"
			year = Integer.parseInt(current.substring(0, 4));
			month = Integer.parseInt(current.substring(5, 7));
			day = Integer.parseInt(current.substring(8, 10));
			
			current = reader.next();						//"HH:MM:SS"
			hour = Integer.parseInt(current.substring(0, 2));
			min = Integer.parseInt(current.substring(3, 5));
			sec = Integer.parseInt(current.substring(6, 8));
			
			timezone = reader.nextLine().substring(1);		//"TIMEZONE"
			date = new Date(year, month, day, hour, min, sec, timezone);
			
			reader.next();									//"Table 'TABLE_NAME'"
			current = reader.next();
			tableName = current.substring(1, current.length() - 1);
			
			tableType = reader.next();						//"TABLE_TYPE"
															
			reader.next();									//"Seat #BUTTON_POSITION is the button"
			buttonPosition = Integer.parseInt(reader.next().substring(1)) - 1;
			reader.next();
			reader.next();
			reader.nextLine();
			
			current = reader.next();						//"Seat N: PLAYERN_NAME ($STACKN in chips) <is sitting out>"
			while(current.equals("Seat")){
				current = reader.next();
				while(Integer.parseInt(current.substring(0, current.length() - 1)) != currentSeat){
					playerNames.add("");					//Handles blank spaces for players sitting out in cash games.
					inHand.add(false);
					stacks.add(0.0);
					currentSeat++;
				}
				playerNames.add(reader.next());
				current = reader.next();
				stacks.add(Double.parseDouble(current.substring(2)));
				reader.next();
				reader.nextLine();
				current = reader.next();
				if(current.equals("is sitting")){			//Player is sitting out.
					reader.next();
					reader.next();
					inHand.add(false);
					current = reader.next();
				}else{
					inHand.add(true);
					numInHand++;
				}
				currentSeat++;
			}
			if(buttonPosition == playerNames.size() - 1){	//Determines regular position of big blind.
				bbPosition = 1;
			}else if(buttonPosition == playerNames.size() - 2){
				bbPosition = 0;
			}else{
				bbPosition = buttonPosition + 2;
			}
			
			while(!inHand.get(bbPosition)){					//Rectifies misplaced blinds due to players sitting out.
				if(bbPosition == playerNames.size() - 1){
					bbPosition = 0;
				}else{
					bbPosition++;
				}
			}
			if(bbPosition == 0){
				sbPosition = playerNames.size();
			}else{
				sbPosition = bbPosition - 1;
			}
			while(!inHand.get(sbPosition)){
				if(sbPosition == 0){
					sbPosition = playerNames.size() - 1;
				}else{
					sbPosition--;
				}
			}//TODO player noted as sitting out during the small/big blind listings
			
			current = reader.nextLine();					//"PLAYER#_NAME: posts the ante $ANTE" (or blinds if no ante)
			if(current.contains("ante")){					//TODO what if the first player is a blind?
				//TODO check for "is sitting out"
				ante = Double.parseDouble(current.substring(current.indexOf("ante") + 5));
				for(int i = 0; i < inHand.size(); i++){
					if(current.contains("is sitting")){		//"PLAYER_NAME: is sitting out"
						i--;								//TODO assuming ante posted when player sitting out for cash and tourney.
						player = current.substring(0, current.indexOf(':'));
						for(int j = 0; j < playerNames.size(); j++){
							if(playerNames.get(j).equals("")){
								playerNames.set(j, player);
							}
						}
						current = reader.nextLine();
					}else{
						current = reader.nextLine();
					}
				}
			}else{
				ante = 0;
			}
			
			while(current.contains("is sitting")){			//"PLAYER_NAME: is sitting out"
				player = current.substring(0, current.indexOf(':'));
				for(int j = 0; j < playerNames.size(); j++){
					if(playerNames.get(j).equals("")){
						playerNames.set(j, player);
					}
				}
				current = reader.nextLine();
			}
			//"PLAYER#_NAME: posts small blind $SB" //TODO possible error, is there ALWAYS a sb??
			current = reader.nextLine();					//"PLAYER#_NAME: posts big blind $BB"//TODO temp current assigns (this and next)
			while(current.contains("is sitting")){			//"PLAYER_NAME: is sitting out"
				player = current.substring(0, current.indexOf(':'));
				for(int j = 0; j < playerNames.size(); j++){
					if(playerNames.get(j).equals("")){
						playerNames.set(j, player);
						j = playerNames.size();
					}
				}
				current = reader.nextLine();
			}
			current = reader.nextLine();					//"*** HOLE CARDS ***"	//TODO missed gathering info, maybe bc I don't nextLine() for sb?
			current = reader.next();
			
			reader.next();									//"Dealt to PLAYER#_NAME [SUIT_VALUE SUIT_VALUE]"
			current = reader.next();
			knownHands = new Card[playerNames.size()][];
			for(int i = 0; i < playerNames.size(); i++){
				if(playerNames.get(i).equals(current)){
					knownHands[i] = new Card[]{
							new Card(reader.next().substring(1, 3)),
							new Card(reader.nextLine().substring(1, 3))
					};
					i = playerNames.size();
				}
			}
			
			showdownReached = false;
			//"*** HOLE CARDS ***"	//TODO verify whether "***" could be in a player's name. (appears further down as well)
			while(!(current = reader.next()).equals("***") && !current.equals("Uncalled")){
				holeCardActions.add(parseAction(current + reader.nextLine()));
			}
			
			if(reader.next().equals("FLOP")){				//"*** FLOP *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT]"
				reader.next();
				board[0] = new Card(reader.next().substring(1, 3));
				board[1] = new Card(reader.next().substring(0, 2));
				board[2] = new Card(reader.nextLine().substring(1, 3));
				while(!(current = reader.next()).equals("***") && !current.equals("Uncalled")){
					flopActions.add(parseAction(current + reader.nextLine()));
				}
				
				if(reader.next().equals("TURN")){			//"*** TURN *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT] [VALUE_SUIT]"
					reader.next();
					reader.next();
					reader.next();
					reader.next();
					current = reader.nextLine();
					board[3] = new Card(current.substring(2, 4));
					while(!(current = reader.next()).equals("***") && !current.equals("Uncalled")){
						turnActions.add(parseAction(current + reader.nextLine()));
					}
					
					if(reader.next().equals("RIVER")){		//"*** RIVER *** [VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT] [VALUE_SUIT]"
						reader.next();
						reader.next();
						reader.next();
						reader.next();
						reader.next();
						current = reader.nextLine();
						board[4] = new Card(current.substring(2, 4));
						while(!(current = reader.next()).equals("***") && !current.equals("Uncalled")){
							riverActions.add(parseAction(current + reader.nextLine())); 
						}
						current = reader.next();
						
						if(current.equals("SHOW")){			//"*** SHOW DOWN ***"
							showdownReached = true;
							reader.nextLine();
							while(!(current = reader.next()).equals("***")){	//"PLAYER_NAME: shows/mucks hand/collected ..."	
								if(current.charAt(current.length() - 1) == ':'){
									player = current.substring(0, current.length() - 1);
								}else{
									player = current.substring(0, current.length());
								}
								current = reader.next();
								if(current.equals("shows")){					//"... shows [VALUE_SUIT VALUE_SUIT (HAND_TYPE)"
									hand[0] = new Card(reader.next().substring(1, 3));
									hand[1] = new Card(reader.next().substring(0, 2));
									for(int i = 0; i < playerNames.size(); i++){
										if(playerNames.get(i).equals(player)){	//Adds this player's hand to the known hands.
											knownHands[i] = hand;
											i = playerNames.size();
										}
									}
									current = reader.nextLine();
									showdownInfo.add(new ShowdownInfo(player, current.substring(2, current.length() - 1), hand[0], hand[1]));
								}else if(current.equals("mucks")){						//"... mucks hand"
									reader.nextLine();
									showdownInfo.add(new ShowdownInfo(player));
								}else if(current.equals("collected")){					//"... collected $WINNINGS from ..."
									current = reader.next();
									amount = Double.parseDouble(current.substring(1, current.length()));
									reader.next();
									if(reader.nextLine().equals(" pot")){	//"... pot"
										showdownInfo.add(new ShowdownInfo(player, amount, true));
									}else{												//"... side pot"//TODO confirm this is accurate
										showdownInfo.add(new ShowdownInfo(player, amount, false));
										reader.next();
									}
								}
							}
						}//Showdown
					}//River
				}//Turn
			}//Flop
			
			if(!showdownReached){												//"Uncalled bet ($AMOUNT) returned to PLAYER_NAME"
				current = reader.next();
				amount = Double.parseDouble(current.substring(2, current.length() - 1));
				reader.next();
				reader.next();
				showdownInfo.add(new ShowdownInfo(reader.nextLine().substring(1), amount));
				//current = reader.nextLine();
				player = reader.next();
				reader.next();
				amount = Double.parseDouble(reader.next().substring(1));
				showdownInfo.add(new ShowdownInfo(player, amount, true));		//TODO might need to check side pots?
				reader.nextLine();
			}
			
			reader.nextLine();													//"*** SUMMARY ***"
			reader.next();														//"Total pot $POT | Rake $RAKE"
			reader.next();
			pot = Double.parseDouble(reader.next().substring(1));
			reader.next();
			reader.next();
			rake = Double.parseDouble(reader.next().substring(1));
			
			reader.nextLine();													//"Board <[VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT VALUE_SUIT]>"
			reader.nextLine();													//Board was already parsed.
			for(int i = 0; i < numInHand; i++){									//"Seat #: PLAYER_NAME <(button/small blind/big blind)> ..."
				current = reader.nextLine();
				splitLine = current.split(" ");
				if(i == buttonPosition){
					temp1 = 4;
				}else if(i == bbPosition || i == sbPosition){
					temp1 = 5;
				}else{
					temp1 = 3;
				}
				if(splitLine[temp1].equals("showed")){							//"... showed [VALUE_SUIT VALUE_SUIT] and ..."
					temp1 += 5;
					if(splitLine[temp1 - 1].equals("won")){						//"... won ($WINNINGS) with HAND_TYPE"
						won = true;
						amount = Double.parseDouble(splitLine[temp1].substring(2, splitLine[temp1].length() - 2));
						handType = "";
						for(int j = temp1 + 1; j < splitLine.length; j++){
							handType += splitLine[j];
						}
					}else{														//"... lost with HAND_TYPE"
						won = false;
						amount = 0;
						handType = "";
						for(int j = temp1 + 1; j < splitLine.length; j++){
							handType += splitLine[j];
						}
					}
					playerActionSummaries.add(
							new PlayerActionSummary(
									FoldTime.DIDNT_FOLD,
									playerNames.get(i),
									handType,
									knownHands[i],
									amount,
									i,
									i == buttonPosition, i == bbPosition, i == sbPosition,
									won)
							);
				}else if(splitLine[temp1].equals("collected")){
					amount = Double.parseDouble(splitLine[temp1 + 1].substring(2, splitLine[temp1 + 1].length() - 1));
					playerActionSummaries.add(
							new PlayerActionSummary(
									FoldTime.MADE_EVERYONE_ELSE_FOLD,
									playerNames.get(i),
									"",
									knownHands[i],
									amount,
									i,
									i == buttonPosition, i == bbPosition, i == sbPosition,
									true)
							);
				}else if(splitLine[temp1].equals("folded")){					//"... folded ..."
					temp1++;
					if(splitLine[temp1].equals("before")){						//"... before flop ..."
						if(splitLine[splitLine.length - 1].equals("bet)")){		//"... (didn't bet)"
							foldTime = FoldTime.DIDNT_BET;
						}else{													//""
							foldTime = FoldTime.BEFORE_FLOP;
						}
					}else if(splitLine[splitLine.length - 1].equals("Flop")){	//"... after the flop"
						foldTime = FoldTime.AFTER_FLOP;
					}else if(splitLine[splitLine.length - 1].equals("Turn")){	//"... on the turn"
						foldTime = FoldTime.AFTER_TURN;
					}else{														//"... on the river"
						foldTime = FoldTime.AFTER_RIVER;
					}
					playerActionSummaries.add(
							new PlayerActionSummary(
									foldTime,
									playerNames.get(i),
									"",
									knownHands[i],
									0,
									i,
									i == buttonPosition, i == bbPosition, i == sbPosition,
									false
									)
							);
				}else if(splitLine[temp1].equals("mucked")){					//"... mucked [VALUE_SUIT VALUE_SUIT]"
					hand[0] = new Card(splitLine[temp1 + 1].substring(1, 2));
					hand[1] = new Card(splitLine[temp1 + 2].substring(0, 1));
					knownHands[i] = hand;
					playerActionSummaries.add(
							new PlayerActionSummary(
									FoldTime.DIDNT_BET,
									playerNames.get(i),
									"",
									knownHands[i],
									0,
									i,
									i == buttonPosition, i == bbPosition, i == sbPosition,
									false
									)
							);
				}
			}
			
			primStacks = new double[playerNames.size()];						//Converts the ArrayLists to primitives.
			primInHand = new boolean[playerNames.size()];
			for(int i = 0; i < playerNames.size(); i++){
				primStacks[i] = stacks.get(i);
				primInHand[i] = inHand.get(i);
			}
			
			mainInfo = new MainInfo(date, currency, tableName, gameType, tableType, sb, bb, ante, handID,
					buttonPosition, bbPosition, sbPosition, playerNames.size());
			
			actionSummary = new ActionSummary(
					holeCardActions.toArray(new Action[0]),
					flopActions.toArray(new Action[0]),
					turnActions.toArray(new Action[0]),
					riverActions.toArray(new Action[0]),
					knownHands,
					board,
					playerNames.toArray(new String[0])
					);
			
			showdownSection = showdownInfo.toArray(new ShowdownInfo[0]);
			
			handSummary = new HandSummary(
					playerActionSummaries.toArray(new PlayerActionSummary[0]),
					board, playerNames.toArray(new String[0]),
					new double[]{pot},											//TODO Change to allow side pots when I figure out how they work.
					rake
					);
			
			playerInfo = new PlayerInfo(
					playerNames.toArray(new String[0]),
					primStacks,
					primInHand,
					sb, bb, ante,
					bbPosition,
					sbPosition,
					true
					);
			
			reader.close();
			return new Hand(mainInfo, actionSummary, showdownSection, handSummary, playerInfo);
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		return null;//TODO blinds and ante posting: verify player has sufficient chips.
	}//readCashGameHandFromFile()
	
	/**
	 * Takes in a <code>String</code> of the form "PLAYER_NAME: ACTION_TYPE \<AMOUNT\> < to RAISE_AMOUNT>"
	 * and returns the <code>Action</code> it represents or an error if it's invalid.
	 * @param actionText - The <code>String</code> to be parsed.
	 * @return An <code>Action</code> that was represented in the inputed <code>String</code>.
	 */
	private static Action parseAction(String actionText) throws IllegalArgumentException{
		Scanner reader = new Scanner(actionText);
		String current = reader.next(), player = current.substring(0, current.length() - 1);
		double amount, raiseAmount;
		
		current = reader.next();
		if(current.equals("folds")){									//"PLAYER_NAME folds"
			reader.close();
			return new Action(player, ActionType.FOLD, -1, -1);
		}else if(current.equals("checks")){								//"PLAYER_NAME checks"
			reader.close();
			return new Action(player, ActionType.CHECK, -1, -1);
		}else{
			amount = Double.parseDouble(reader.next().substring(1));
			if(current.equals("calls")){								//"PLAYER_NAME calls $AMOUNT"
				reader.close();
				return new Action(player, ActionType.CALL, amount, -1);
			}else if(current.equals("bets")){							//"PLAYER_NAME bets $AMOUNT"
				reader.close();
				return new Action(player, ActionType.BET, amount, -1);
			}else if(current.equals("raises")){							//"PLAYER_NAME raises $RAISE_AMOUNT to $AMOUNT"
				raiseAmount = amount;
				reader.next();
				amount = Double.parseDouble(reader.next().substring(1));
				reader.close();
				return new Action(player, ActionType.RAISE, amount, raiseAmount);
			}
		}
		reader.close();
		throw new IllegalArgumentException("The String was unable to be parsed as an Action.");
	}//parseAction
	
}//HandDataParser