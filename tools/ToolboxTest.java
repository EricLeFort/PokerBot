package tools;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class ToolboxTest{
	static ArrayList<Card> testHand1 = new ArrayList<Card>();		//Royal Flush
	static ArrayList<Card> testHand2 = new ArrayList<Card>();		//Straight Flush (9-High)
	static ArrayList<Card> testHand3 = new ArrayList<Card>();		//Four-of-a-Kind (sixes)
	static ArrayList<Card> testHand4 = new ArrayList<Card>();		//Full House (A's full of 2's)
	static ArrayList<Card> testHand5 = new ArrayList<Card>();		//Full House (5's full of 8's)
	static ArrayList<Card> testHand6 = new ArrayList<Card>();		//Full House (9's full of A's)
	static ArrayList<Card> testHand7 = new ArrayList<Card>();		//Flush (8 high) 
	static ArrayList<Card> testHand8 = new ArrayList<Card>();		//Flush (A high)
	static ArrayList<Card> testHand9 = new ArrayList<Card>();		//Straight (A to 5)
	static ArrayList<Card> testHand10 = new ArrayList<Card>();		//Straight (7 to J)
	static ArrayList<Card> testHand11 = new ArrayList<Card>();		//Three-of-a-kind (J's)
	static ArrayList<Card> testHand12 = new ArrayList<Card>();		//Three-of-a-kind (10's)
	static ArrayList<Card> testHand13 = new ArrayList<Card>();		//Two-Pair (K's and Q's)
	static ArrayList<Card> testHand14 = new ArrayList<Card>();		//Two-Pair (8's and 3's)
	static ArrayList<Card> testHand15 = new ArrayList<Card>();		//Two-Pair (10's and 9's)
	static ArrayList<Card> testHand16 = new ArrayList<Card>();		//Pair (5's)
	static ArrayList<Card> testHand17 = new ArrayList<Card>();		//Pair (2's)
	static ArrayList<Card> testHand19 = new ArrayList<Card>();		//High-Card (9)(Worst possible high card)
	static ArrayList<Card> testHand18 = new ArrayList<Card>();		//High-Card (Q)
	static ArrayList<Card> testHand20 = new ArrayList<Card>();		//High-Card (A)(Best possible high card)

	@BeforeClass
	public static void beforeClass(){
		testHand1.add(new Card(51));		//A SPADES
		testHand1.add(new Card(50));		//K SPADES
		testHand1.add(new Card(49));		//Q SPADES
		testHand1.add(new Card(48));		//J SPADES
		testHand1.add(new Card(47));		//10 SPADES
		testHand1.add(new Card(1));			//3 CLUBS
		testHand1.add(new Card(0));			//2 CLUBS
		
		testHand2.add(new Card(29));		//5 HEARTS
		testHand2.add(new Card(30));		//6 HEARTS
		testHand2.add(new Card(31));		//7 HEARTS
		testHand2.add(new Card(32));		//8 HEARTS
		testHand2.add(new Card(33));		//9 HEARTS
		testHand2.add(new Card(46));		//9 SPADES
		testHand2.add(new Card(16));		//5 DIAMONDS
		
		testHand3.add(new Card(4));			//6 CLUBS
		testHand3.add(new Card(17));		//6 DIAMONDS
		testHand3.add(new Card(30));		//6 HEARTS
		testHand3.add(new Card(43));		//6 SPADES
		testHand3.add(new Card(0));			//2 CLUBS
		testHand3.add(new Card(25));		//A DIAMONDS
		testHand3.add(new Card(38));		//A HEARTS
		
		testHand4.add(new Card(38));		//A HEARTS
		testHand4.add(new Card(51));		//A SPADES
		testHand4.add(new Card(12));		//A CLUBS
		testHand4.add(new Card(0));			//2 CLUBS
		testHand4.add(new Card(26));		//2 HEARTS
		testHand4.add(new Card(42));		//5 SPADES
		testHand4.add(new Card(45));		//8 SPADES
		
		testHand5.add(new Card(3));			//5 CLUBS
		testHand5.add(new Card(29));		//5 HEARTS
		testHand5.add(new Card(42));		//5 SPADES
		testHand5.add(new Card(6));			//8 CLUBS
		testHand5.add(new Card(19));		//8 DIAMONDS
		testHand5.add(new Card(13));		//2 DIAMONDS
		testHand5.add(new Card(25));		//A DIAMONDS
			
		testHand6.add(new Card(33));		//9 HEARTS
		testHand6.add(new Card(7));			//9 CLUBS
		testHand6.add(new Card(20));		//9 DIAMONDS
		testHand6.add(new Card(23));		//Q DIAMONDS
		testHand6.add(new Card(49));		//Q SPADES
		testHand6.add(new Card(12));		//A CLUBS
		testHand6.add(new Card(38));		//A HEARTS
		
		testHand7.add(new Card(4));			//6 CLUBS	
		testHand7.add(new Card(0));			//2 CLUBS
		testHand7.add(new Card(3));			//5 CLUBS
		testHand7.add(new Card(1));			//3 CLUBS
		testHand7.add(new Card(6));			//8 CLUBS
		testHand7.add(new Card(33));		//9 HEARTS
		testHand7.add(new Card(22));		//J DIAMONDS
		
		testHand8.add(new Card(26));		//2 HEARTS
		testHand8.add(new Card(38));		//A HEARTS
		testHand8.add(new Card(33));		//9 HEARTS
		testHand8.add(new Card(34));		//10 HEARTS
		testHand8.add(new Card(35));		//J HEARTS
		testHand8.add(new Card(37));		//K HEARTS
		testHand8.add(new Card(28));		//4 HEARTS
		
		testHand9.add(new Card(12));		//A CLUBS
		testHand9.add(new Card(3));			//5 CLUBS
		testHand9.add(new Card(13));		//2 DIAMONDS
		testHand9.add(new Card(41));		//4 SPADES
		testHand9.add(new Card(0));			//2 CLUBS
		testHand9.add(new Card(40));		//3 SPADES
		testHand9.add(new Card(27));		//3 HEARTS
		
		testHand10.add(new Card(35));		//J HEARTS
		testHand10.add(new Card(5));		//7 CLUBS
		testHand10.add(new Card(39));		//2 SPADES
		testHand10.add(new Card(1));		//3 CLUBS
		testHand10.add(new Card(20));		//9 DIAMONDS
		testHand10.add(new Card(45));		//8 SPADES
		testHand10.add(new Card(21));		//10 DIAMONDS
		
		testHand11.add(new Card(35));		//J HEARTS
		testHand11.add(new Card(9));		//J CLUBS
		testHand11.add(new Card(48));		//J SPADES
		testHand11.add(new Card(8));		//10 CLUBS
		testHand11.add(new Card(36));		//Q HEARTS
		testHand11.add(new Card(11));		//K CLUBS
		testHand11.add(new Card(5));		//7 CLUBS
		
		testHand12.add(new Card(47));		//10 SPADES
		testHand12.add(new Card(34));		//10 HEARTS
		testHand12.add(new Card(8));		//10 CLUBS
		testHand12.add(new Card(13));		//2 DIAMONDS
		testHand12.add(new Card(14));		//3 DIAMONDS
		testHand12.add(new Card(29));		//5 HEARTS
		testHand12.add(new Card(50));		//K SPADES
		
		testHand13.add(new Card(29));		//5 HEARTS
		testHand13.add(new Card(24));		//K DIAMONDS
		testHand13.add(new Card(9));		//J CLUBS
		testHand13.add(new Card(49));		//Q SPADES
		testHand13.add(new Card(11));		//K CLUBS
		testHand13.add(new Card(36));		//Q HEARTS
		testHand13.add(new Card(0));		//2 CLUBS
		
		testHand14.add(new Card(32));		//8 HEARTS
		testHand14.add(new Card(6));		//8 CLUBS
		testHand14.add(new Card(26));		//2 HEARTS
		testHand14.add(new Card(14));		//3 DIAMONDS
		testHand14.add(new Card(40));		//3 SPADES
		testHand14.add(new Card(34));		//10 HEARTS
		testHand14.add(new Card(0));		//2 CLUBS
		
		testHand15.add(new Card(21));		//10 DIAMONDS
		testHand15.add(new Card(8));		//10 CLUBS
		testHand15.add(new Card(44));		//7 SPADES
		testHand15.add(new Card(19));		//8 DIAMONDS
		testHand15.add(new Card(45));		//8 SPADES
		testHand15.add(new Card(20));		//9 DIAMONDS
		testHand15.add(new Card(7));		//9 CLUBS
		
		testHand16.add(new Card(3));		//5 CLUBS
		testHand16.add(new Card(27));		//3 HEARTS
		testHand16.add(new Card(35));		//J HEARTS
		testHand16.add(new Card(13));		//2 DIAMONDS
		testHand16.add(new Card(46));		//9 SPADES
		testHand16.add(new Card(42));		//5 SPADES
		testHand16.add(new Card(12));		//A CLUBS
		
		testHand17.add(new Card(15));		//4 DIAMONDS
		testHand17.add(new Card(50));		//K SPADES
		testHand17.add(new Card(26));		//2 HEARTS
		testHand17.add(new Card(8));		//10 CLUBS
		testHand17.add(new Card(13));		//2 DIAMONDS
		testHand17.add(new Card(7));		//9 CLUBS
		testHand17.add(new Card(14));		//3 DIAMONDS
		
		testHand18.add(new Card(0));		//2 CLUBS
		testHand18.add(new Card(29));		//5 HEARTS
		testHand18.add(new Card(1));		//3 CLUBS
		testHand18.add(new Card(45));		//8 SPADES
		testHand18.add(new Card(20));		//9 DIAMONDS
		testHand18.add(new Card(21));		//10 DIAMONDS
		testHand18.add(new Card(23));		//Q DIAMONDS
		
		testHand19.add(new Card(26));		//2 HEARTS
		testHand19.add(new Card(40));		//3 SPADES
		testHand19.add(new Card(2));		//4 CLUBS
		testHand19.add(new Card(3));		//5 CLUBS
		testHand19.add(new Card(18));		//7 DIAMONDS
		testHand19.add(new Card(19));		//8 DIAMONDS
		testHand19.add(new Card(46));		//9 SPADES
		
		testHand20.add(new Card(0));		//2 CLUBS
		testHand20.add(new Card(1));		//3 CLUBS
		testHand20.add(new Card(2));		//4 CLUBS
		testHand20.add(new Card(6));		//8 CLUBS
		testHand20.add(new Card(33));		//9 HEARTS
		testHand20.add(new Card(38));		//A HEARTS
		testHand20.add(new Card(34));		//10 HEARTS
	}//beforeClass()
	
	@Test
	public void testShuffleUp(){
		ArrayList<Card> deck = new ArrayList<Card>();
		Card currentCard;
		boolean found;
		deck = Toolbox.shuffleUp();
		
		if(deck.size() != 52){							//Ensures deck has 52 cards.
			fail("The deck isn't the correct size!");
		}
		
		for(int i = 0; i < 52; i++){					//Ensures every card is within deck.
			currentCard = new Card(i);
			found = false;
			for(int j = 0; j < 52; j++){
				if(deck.get(j).getCardName().equals(currentCard.getCardName())){
					j = 52;
					found = true;
				}
			}
			if(!found){
				fail("The card " + currentCard.getCardName() + " was not found.");
			}
		}
	}//testShuffleUp()
	
	@Test
	public void testFlushValue(){
		int[] expected1 = {10, 0, 0};
		int[] expected2 = {9, 5, 0};
		int[] expected3 = {0, 0, 0};
		int[] expected4 = {0, 0, 0};
		int[] expected5 = {0, 0, 0};
		int[] expected6 = {0, 0, 0};
		int[] expected7 = {6, 4, 0};
		int[] expected8 = {6, 10, 0};
		int[] expected9 = {0, 0, 0};
		int[] expected10 = {0, 0, 0};
		int[] expected11 = {0, 0, 0};
		int[] expected12 = {0, 0, 0};
		int[] expected13 = {0, 0, 0};
		int[] expected14 = {0, 0, 0};
		int[] expected15 = {0, 0, 0};
		int[] expected16 = {0, 0, 0};
		int[] expected17 = {0, 0, 0};
		int[] expected18 = {0, 0, 0};
		int[] expected19 = {0, 0, 0};
		int[] expected20 = {0, 0, 0};
		
		int[] actual1 = Toolbox.flushValue(testHand1);
		int[] actual2 = Toolbox.flushValue(testHand2);
		int[] actual3 = Toolbox.flushValue(testHand3);
		int[] actual4 = Toolbox.flushValue(testHand4);
		int[] actual5 = Toolbox.flushValue(testHand5);
		int[] actual6 = Toolbox.flushValue(testHand6);
		int[] actual7 = Toolbox.flushValue(testHand7);
		int[] actual8 = Toolbox.flushValue(testHand8);
		int[] actual9 = Toolbox.flushValue(testHand9);
		int[] actual10 = Toolbox.flushValue(testHand10);
		int[] actual11 = Toolbox.flushValue(testHand11);
		int[] actual12 = Toolbox.flushValue(testHand12);
		int[] actual13 = Toolbox.flushValue(testHand13);
		int[] actual14 = Toolbox.flushValue(testHand14);
		int[] actual15 = Toolbox.flushValue(testHand15);
		int[] actual16 = Toolbox.flushValue(testHand16);
		int[] actual17 = Toolbox.flushValue(testHand17);
		int[] actual18 = Toolbox.flushValue(testHand18);
		int[] actual19 = Toolbox.flushValue(testHand19);
		int[] actual20 = Toolbox.flushValue(testHand20);
		
		assertArrayEquals(expected1, actual1);
		assertArrayEquals(expected2, actual2);
		assertArrayEquals(expected3, actual3);
		assertArrayEquals(expected4, actual4);
		assertArrayEquals(expected5, actual5);
		assertArrayEquals(expected6, actual6);
		assertArrayEquals(expected7, actual7);
		assertArrayEquals(expected8, actual8);
		assertArrayEquals(expected9, actual9);
		assertArrayEquals(expected10, actual10);
		assertArrayEquals(expected11, actual11);
		assertArrayEquals(expected12, actual12);
		assertArrayEquals(expected13, actual13);
		assertArrayEquals(expected14, actual14);
		assertArrayEquals(expected15, actual15);
		assertArrayEquals(expected16, actual16);
		assertArrayEquals(expected17, actual17);
		assertArrayEquals(expected18, actual18);
		assertArrayEquals(expected19, actual19);
		assertArrayEquals(expected20, actual20);
	}//testFlushValue()
	
	@Test
	public void testStraightValue(){
		int[] expected1 = {5, 10, 0};
		int[] expected2 = {5, 5, 0};
		int[] expected3 = {0, 0, 0};
		int[] expected4 = {0, 0, 0};
		int[] expected5 = {0, 0, 0};
		int[] expected6 = {0, 0, 0};
		int[] expected7 = {0, 0, 0};
		int[] expected8 = {0, 0, 0};
		int[] expected9 = {5, 1, 0};
		int[] expected10 = {5, 7, 0};
		int[] expected11 = {0, 0, 0};
		int[] expected12 = {0, 0, 0};
		int[] expected13 = {0, 0, 0};
		int[] expected14 = {0, 0, 0};
		int[] expected15 = {0, 0, 0};
		int[] expected16 = {0, 0, 0};
		int[] expected17 = {0, 0, 0};
		int[] expected18 = {0, 0, 0};
		int[] expected19 = {0, 0, 0};
		int[] expected20 = {0, 0, 0};
		
		int[] actual1 = Toolbox.straightValue(testHand1);
		int[] actual2 = Toolbox.straightValue(testHand2);
		int[] actual3 = Toolbox.straightValue(testHand3);
		int[] actual4 = Toolbox.straightValue(testHand4);
		int[] actual5 = Toolbox.straightValue(testHand5);
		int[] actual6 = Toolbox.straightValue(testHand6);
		int[] actual7 = Toolbox.straightValue(testHand7);
		int[] actual8 = Toolbox.straightValue(testHand8);
		int[] actual9 = Toolbox.straightValue(testHand9);
		int[] actual10 = Toolbox.straightValue(testHand10);
		int[] actual11 = Toolbox.straightValue(testHand11);
		int[] actual12 = Toolbox.straightValue(testHand12);
		int[] actual13 = Toolbox.straightValue(testHand13);
		int[] actual14 = Toolbox.straightValue(testHand14);
		int[] actual15 = Toolbox.straightValue(testHand15);
		int[] actual16 = Toolbox.straightValue(testHand16);
		int[] actual17 = Toolbox.straightValue(testHand17);
		int[] actual18 = Toolbox.straightValue(testHand18);
		int[] actual19 = Toolbox.straightValue(testHand19);
		int[] actual20 = Toolbox.straightValue(testHand20);
		
		assertArrayEquals(expected1, actual1);
		assertArrayEquals(expected2, actual2);
		assertArrayEquals(expected3, actual3);
		assertArrayEquals(expected4, actual4);
		assertArrayEquals(expected5, actual5);
		assertArrayEquals(expected6, actual6);
		assertArrayEquals(expected7, actual7);
		assertArrayEquals(expected8, actual8);
		assertArrayEquals(expected9, actual9);
		assertArrayEquals(expected10, actual10);
		assertArrayEquals(expected11, actual11);
		assertArrayEquals(expected12, actual12);
		assertArrayEquals(expected13, actual13);
		assertArrayEquals(expected14, actual14);
		assertArrayEquals(expected15, actual15);
		assertArrayEquals(expected16, actual16);
		assertArrayEquals(expected17, actual17);
		assertArrayEquals(expected18, actual18);
		assertArrayEquals(expected19, actual19);
		assertArrayEquals(expected20, actual20);
	}//testStraightValue()
	
	@Test
	public void testcheckSets(){
		int[] expected1 = {1, 12, 0};
		int[] expected2 = {3, 25, 6};
		int[] expected3 = {8, 4 ,12};
		int[] expected4 = {7, 145, 0};
		int[] expected5 = {7, 42, 0};
		int[] expected6 = {7, 96, 0};
		int[] expected7 = {1, 9, 0};
		int[] expected8 = {1, 12, 0};
		int[] expected9 = {3, 1, 12};
		int[] expected10 = {1, 9, 0};
		int[] expected11 = {4, 9, 11};
		int[] expected12 = {4, 8, 11};
		int[] expected13 = {3, 66, 9};
		int[] expected14 = {3, 17, 8};
		int[] expected15 = {3, 36, 6};
		int[] expected16 = {2, 3, 12};
		int[] expected17 = {2, 0, 11};
		int[] expected18 = {1, 10, 0};
		int[] expected19 = {1, 7, 0};
		int[] expected20 = {1, 12, 0};
		
		int[] actual1 = Toolbox.checkSets(testHand1);
		int[] actual2 = Toolbox.checkSets(testHand2);
		int[] actual3 = Toolbox.checkSets(testHand3);
		int[] actual4 = Toolbox.checkSets(testHand4);
		int[] actual5 = Toolbox.checkSets(testHand5);
		int[] actual6 = Toolbox.checkSets(testHand6);
		int[] actual7 = Toolbox.checkSets(testHand7);
		int[] actual8 = Toolbox.checkSets(testHand8);
		int[] actual9 = Toolbox.checkSets(testHand9);
		int[] actual10 = Toolbox.checkSets(testHand10);
		int[] actual11 = Toolbox.checkSets(testHand11);
		int[] actual12 = Toolbox.checkSets(testHand12);
		int[] actual13 = Toolbox.checkSets(testHand13);
		int[] actual14 = Toolbox.checkSets(testHand14);
		int[] actual15 = Toolbox.checkSets(testHand15);
		int[] actual16 = Toolbox.checkSets(testHand16);
		int[] actual17 = Toolbox.checkSets(testHand17);
		int[] actual18 = Toolbox.checkSets(testHand18);
		int[] actual19 = Toolbox.checkSets(testHand19);
		int[] actual20 = Toolbox.checkSets(testHand20);
		
		assertArrayEquals(expected1, actual1);
		assertArrayEquals(expected2, actual2);
		assertArrayEquals(expected3, actual3);
		assertArrayEquals(expected4, actual4);
		assertArrayEquals(expected5, actual5);
		assertArrayEquals(expected6, actual6);
		assertArrayEquals(expected7, actual7);
		assertArrayEquals(expected8, actual8);
		assertArrayEquals(expected9, actual9);
		assertArrayEquals(expected10, actual10);
		assertArrayEquals(expected11, actual11);
		assertArrayEquals(expected12, actual12);
		assertArrayEquals(expected13, actual13);
		assertArrayEquals(expected14, actual14);
		assertArrayEquals(expected15, actual15);
		assertArrayEquals(expected16, actual16);
		assertArrayEquals(expected17, actual17);
		assertArrayEquals(expected18, actual18);
		assertArrayEquals(expected19, actual19);
		assertArrayEquals(expected20, actual20);
	}//testFullHouseValue()
	
	@Test
	public void testRndNumGen(){
		int rnd;
		boolean zero = false, one = false, two = false, three = false, four = false, five = false,
				six = false, seven = false, eight = false, nine = false, ten = false;
		
		for(int i = 0; i < 500; i++){
			rnd = Toolbox.rndNumGen(2);
			if(!zero && rnd == 0){
				zero = true;
			}else if(!one && rnd == 1){
				one = true;
			}else if(!two && rnd == 2){
				two = true;
			}
			if(rnd > 2 || rnd < 0){
				fail("The number was outside of the given range.");
			}
		}
		
		if(!zero || !one || !two){
			fail("One of the numbers from 0 - 2 wasn't used.");
		}
		zero = false;
		one = false;
		two = false;
		
		for(int i = 0; i < 100; i++){
			rnd = Toolbox.rndNumGen(10);
			if(rnd == 10){
				ten = true;
			}else if(rnd == 9){
				nine = true;
			}else if(rnd == 8){
				eight = true;
			}else if(rnd == 7){
				seven = true;
			}else if(rnd == 6){
				six = true;
			}else if(rnd == 5){
				five = true;
			}else if(rnd == 4){
				four = true;
			}else if(rnd == 3){
				three = true;
			}else if(rnd == 2){
				two = true;
			}else if(rnd == 1){
				one = true;
			}else if(rnd == 0){
				zero = true;
			}
		}
		
		if(!ten){
			fail("High result didn't happen.");
		}else if(!zero){
			fail("Low result didn't happen.");
		}else if(!one || !two || !three || !four || !five || !six || !seven || !eight || !nine){
			fail("A middle result didn't happen.");
		}
		
	}//testRndNumGen()
	
	@Test
	public void testGetHandMessage(){
		int[] handType1 = {10, };
		int[] handType2 = {9, 6};
		int[] handType3 = {8, 10};
		int[] handType4 = {7, 152};
		int[] handType5 = {6, 4};
		int[] handType6 = {5, 1};
		int[] handType7 = {4, 4};
		int[] handType8 = {3, 58};
		int[] handType9 = {2, 1};
		int[] handType10 = {1, 11};
		
		String expected1 = "Royal Flush";
		String expected2 = "Straight Flush, 10 high";
		String expected3 = "Four-of-a-Kind, Queens";
		String expected4 = "Full House, Aces full of Nines";
		String expected5 = "Flush, 8 high";
		String expected6 = "Straight, 5 high";
		String expected7 = "Three-of-a-Kind, Sixes";
		String expected8 = "Two Pair, Kings and Fours";
		String expected9 = "Pair of Threes";
		String expected10 = "King high";
		
		String actual1 = Toolbox.getHandMessage(handType1);
		String actual2 = Toolbox.getHandMessage(handType2);
		String actual3 = Toolbox.getHandMessage(handType3);
		String actual4 = Toolbox.getHandMessage(handType4);
		String actual5 = Toolbox.getHandMessage(handType5);
		String actual6 = Toolbox.getHandMessage(handType6);
		String actual7 = Toolbox.getHandMessage(handType7);
		String actual8 = Toolbox.getHandMessage(handType8);
		String actual9 = Toolbox.getHandMessage(handType9);
		String actual10 = Toolbox.getHandMessage(handType10);
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
		assertEquals(expected5, actual5);
		assertEquals(expected6, actual6);
		assertEquals(expected7, actual7);
		assertEquals(expected8, actual8);
		assertEquals(expected9, actual9);
		assertEquals(expected10, actual10);	
	}//testGetHandMessage()
	
	@Test
	public void testCheckHand(){
		int[] expected1 = {10, 0, 0};
		int[] expected2 = {9, 5, 0};
		int[] expected3 = {8, 4, 12};
		int[] expected4 = {7, 145, 0};
		int[] expected5 = {7, 42, 0};
		int[] expected6 = {7, 96, 0};
		int[] expected7 = {6, 4, 0};
		int[] expected8 = {6, 10, 0};
		int[] expected9 = {5, 1, 0};
		int[] expected10 = {5, 7, 0};
		int[] expected11 = {4, 9, 11};
		int[] expected12 = {4, 8, 11};
		int[] expected13 = {3, 66, 9};
		int[] expected14 = {3, 17, 8};
		int[] expected15 = {3, 36, 6};
		int[] expected16 = {2, 3, 12};
		int[] expected17 = {2, 0, 11};
		int[] expected18 = {1, 10, 0};
		int[] expected19 = {1, 7, 0};
		int[] expected20 = {1, 12, 0};
		
		int[] actual1 = Toolbox.checkHand(testHand1, "Hand 1");
		int[] actual2 = Toolbox.checkHand(testHand2, "Hand 2");
		int[] actual3 = Toolbox.checkHand(testHand3, "Hand 3");
		int[] actual4 = Toolbox.checkHand(testHand4, "Hand 4");
		int[] actual5 = Toolbox.checkHand(testHand5, "Hand 5");
		int[] actual6 = Toolbox.checkHand(testHand6, "Hand 6");
		int[] actual7 = Toolbox.checkHand(testHand7, "Hand 7");
		int[] actual8 = Toolbox.checkHand(testHand8, "Hand 8");
		int[] actual9 = Toolbox.checkHand(testHand9, "Hand 9");
		int[] actual10 = Toolbox.checkHand(testHand10, "Hand 10");
		int[] actual11 = Toolbox.checkHand(testHand11, "Hand 11");
		int[] actual12 = Toolbox.checkHand(testHand12, "Hand 12");
		int[] actual13 = Toolbox.checkHand(testHand13, "Hand 13");
		int[] actual14 = Toolbox.checkHand(testHand14, "Hand 14");
		int[] actual15 = Toolbox.checkHand(testHand15, "Hand 15");
		int[] actual16 = Toolbox.checkHand(testHand16, "Hand 16");
		int[] actual17 = Toolbox.checkHand(testHand17, "Hand 17");
		int[] actual18 = Toolbox.checkHand(testHand18, "Hand 18");
		int[] actual19 = Toolbox.checkHand(testHand19, "Hand 19");
		int[] actual20 = Toolbox.checkHand(testHand20, "Hand 20");
		
		assertArrayEquals(expected1, actual1);
		assertArrayEquals(expected2, actual2);
		assertArrayEquals(expected3, actual3);
		assertArrayEquals(expected4, actual4);
		assertArrayEquals(expected5, actual5);
		assertArrayEquals(expected6, actual6);
		assertArrayEquals(expected7, actual7);
		assertArrayEquals(expected8, actual8);
		assertArrayEquals(expected9, actual9);
		assertArrayEquals(expected10, actual10);
		assertArrayEquals(expected11, actual11);
		assertArrayEquals(expected12, actual12);
		assertArrayEquals(expected13, actual13);
		assertArrayEquals(expected14, actual14);
		assertArrayEquals(expected15, actual15);
		assertArrayEquals(expected16, actual16);
		assertArrayEquals(expected17, actual17);
		assertArrayEquals(expected18, actual18);
		assertArrayEquals(expected19, actual19);
		assertArrayEquals(expected20, actual20);
			
	}//testCheckHand()
}//ToolboxTest
