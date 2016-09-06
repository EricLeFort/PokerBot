package Testing;

import static org.junit.Assert.*;
import org.junit.Test;

import handTypes.*;
import tools.*;

public class HandAnalyzerTest{
	/*
	 * These hands are in descending order by rank, this is required for testing purposes.
	 */
	static Card[][] testHands = new Card[][]{
		new Card[7],			//Royal Flush
		new Card[7],			//Straight Flush (9 high)
		new Card[7],			//Straight Flush (8 high)
		new Card[7],			//Straight Flush (5 high)
		new Card[7],			//Four of a kind (sixes, ace kicker)
		new Card[7],			//Four of a kind (sixes, king kicker)
		new Card[7],			//Full House (A's full of 2's)
		new Card[7],			//Full House (9's full of A's)
		new Card[7],			//Full House (9's full of K's)
		new Card[7],			//Full House (5's full of 8's)
		new Card[7],			//Flush (A high)
		new Card[7],			//Flush (K high)
		new Card[7],			//Flush (8 high)
		new Card[7],			//Straight (jack high)
		new Card[7],			//Straight (ten high)
		new Card[7],			//Straight (five high)
		new Card[7],			//Three-of-a-kind (J's, king, queen kicker)
		new Card[7],			//Three-of-a-kind (J's, king, jack kicker)
		new Card[7],			//Three-of-a-kind (J's, five, four kicker)
		new Card[7],			//Three-of-a-kind (10's)
		new Card[7],			//Two-Pair (K's and Q's, jack kicker)
		new Card[7],			//Two-Pair (K's and Q's, ten kicker)
		new Card[7],			//Two-Pair (K's and Q's, three kicker)
		new Card[7],			//Two-Pair (10's and 9's, eight kicker)
		new Card[7],			//Two-Pair (10's and 8's, ace kicker)
		new Card[7],			//Two-Pair (8's and 3's, ten kicker)
		new Card[7],			//Two-Pair (8's and 3's, nine kicker)
		new Card[7],			//Pair (5's, ace, jack, nine kicker)
		new Card[7],			//Pair (5's, ace, jack, eight kicker)
		new Card[7],			//Pair (5's, ace, ten, nine kicker)
		new Card[7],			//Pair (5's, king, queen, jack kicker)
		new Card[7],			//Pair (2's)
		new Card[7],			//High-Card (A)(Best possible high card)
		new Card[7],			//High-Card (Q)
		new Card[7],			//High-Card (9)(Worst possible high card)
	};
	static PokerHand[] results = new PokerHand[testHands.length];
	
	static{
		testHands[0][0] = new Card(51);		//A SPADES		Royal Flush
		testHands[0][1] = new Card(50);		//K SPADES
		testHands[0][2] = new Card(49);		//Q SPADES
		testHands[0][3] = new Card(48);		//J SPADES
		testHands[0][4] = new Card(47);		//10 SPADES
		testHands[0][5] = new Card(1);		//3 CLUBS
		testHands[0][6] = new Card(0);		//2 CLUBS
		
		testHands[1][0] = new Card(29);		//5 HEARTS		Straight Flush, 9 high
		testHands[1][1] = new Card(30);		//6 HEARTS
		testHands[1][2] = new Card(31);		//7 HEARTS
		testHands[1][3] = new Card(32);		//8 HEARTS
		testHands[1][4] = new Card(33);		//9 HEARTS
		testHands[1][5] = new Card(46);		//9 SPADES
		testHands[1][6] = new Card(16);		//5 DIAMONDS
		
		testHands[2][0] = new Card(32);		//8 HEARTS		Straight Flush, 8 high
		testHands[2][1] = new Card(31);		//7 HEARTS
		testHands[2][2] = new Card(39);		//2 SPADES
		testHands[2][3] = new Card(28);		//4 HEARTS
		testHands[2][4] = new Card(46);		//9 SPADES
		testHands[2][5] = new Card(29);		//5 HEARTS
		testHands[2][6] = new Card(30);		//6 HEARTS
		
		testHands[3][0] = new Card(4);		//6 CLUBS		Straight Flush, 5 high
		testHands[3][1] = new Card(15);		//4 DIAMONDS
		testHands[3][2] = new Card(16);		//5 DIAMONDS
		testHands[3][3] = new Card(13);		//2 DIAMONDS
		testHands[3][4] = new Card(31);		//7 HEARTS
		testHands[3][5] = new Card(25);		//A DIAMONDS
		testHands[3][6] = new Card(14);		//3 DIAMONDS
		
		testHands[4][0] = new Card(4);		//6 CLUBS		Four of a kind, sixes, ace kicker
		testHands[4][1] = new Card(17);		//6 DIAMONDS
		testHands[4][2] = new Card(30);		//6 HEARTS
		testHands[4][3] = new Card(43);		//6 SPADES
		testHands[4][4] = new Card(0);		//2 CLUBS
		testHands[4][5] = new Card(25);		//A DIAMONDS
		testHands[4][6] = new Card(38);		//A HEARTS
		
		testHands[5][0] = new Card(17);		//6 DIAMONDS	Four of a kind, sixes, king kicker
		testHands[5][1] = new Card(11);		//K CLUBS
		testHands[5][2] = new Card(4);		//6 CLUBS
		testHands[5][3] = new Card(39);		//2 SPADES
		testHands[5][4] = new Card(30);		//6 HEARTS
		testHands[5][5] = new Card(36);		//Q HEARTS
		testHands[5][6] = new Card(43);		//6 SPADES
		
		testHands[6][0] = new Card(38);		//A HEARTS		Full house, kings full of twos
		testHands[6][1] = new Card(51);		//A SPADES
		testHands[6][2] = new Card(12);		//A CLUBS
		testHands[6][3] = new Card(0);		//2 CLUBS
		testHands[6][4] = new Card(26);		//2 HEARTS
		testHands[6][5] = new Card(42);		//5 SPADES
		testHands[6][6] = new Card(45);		//8 SPADES
		
		testHands[7][0] = new Card(33);		//9 HEARTS		Full house, nines full of aces
		testHands[7][1] = new Card(7);		//9 CLUBS
		testHands[7][2] = new Card(20);		//9 DIAMONDS
		testHands[7][3] = new Card(23);		//Q DIAMONDS
		testHands[7][4] = new Card(49);		//Q SPADES
		testHands[7][5] = new Card(12);		//A CLUBS
		testHands[7][6] = new Card(38);		//A HEARTS
		
		testHands[8][0] = new Card(46);		//9 SPADES		Full house, nines full of kings
		testHands[8][1] = new Card(33);		//9 HEARTS
		testHands[8][2] = new Card(11);		//K CLUBS
		testHands[8][3] = new Card(0);		//2 CLUBS
		testHands[8][4] = new Card(7);		//9 CLUBS
		testHands[8][5] = new Card(42);		//5 SPADES
		testHands[8][6] = new Card(37);		//K HEARTS
		
		testHands[9][0] = new Card(3);		//5 CLUBS		Full house, fives full of eights
		testHands[9][1] = new Card(29);		//5 HEARTS
		testHands[9][2] = new Card(42);		//5 SPADES
		testHands[9][3] = new Card(6);		//8 CLUBS
		testHands[9][4] = new Card(19);		//8 DIAMONDS
		testHands[9][5] = new Card(13);		//2 DIAMONDS
		testHands[9][6] = new Card(25);		//A DIAMONDS
		
		testHands[10][0] = new Card(26);	//2 HEARTS		Flush, ace high
		testHands[10][1] = new Card(38);	//A HEARTS
		testHands[10][2] = new Card(33);	//9 HEARTS
		testHands[10][3] = new Card(34);	//10 HEARTS
		testHands[10][4] = new Card(35);	//J HEARTS
		testHands[10][5] = new Card(37);	//K HEARTS
		testHands[10][6] = new Card(28);	//4 HEARTS
		
		testHands[11][0] = new Card(13);	//2 DIAMONDS	Flush, king high
		testHands[11][1] = new Card(14);	//3 DIAMONDS
		testHands[11][2] = new Card(24);	//K DIAMONDS
		testHands[11][3] = new Card(16);	//5 DIAMONDS
		testHands[11][4] = new Card(12);	//A CLUBS
		testHands[11][5] = new Card(15);	//4 DIAMONDS
		testHands[11][6] = new Card(38);	//A HEARTS
		
		testHands[12][0] = new Card(4);		//6 CLUBS		Flush, eight high
		testHands[12][1] = new Card(0);		//2 CLUBS
		testHands[12][2] = new Card(3);		//5 CLUBS
		testHands[12][3] = new Card(1);		//3 CLUBS
		testHands[12][4] = new Card(6);		//J DIAMONS
		testHands[12][5] = new Card(33);	//9 HEARTS
		testHands[12][6] = new Card(22);	//8 CLUBS
		
		testHands[13][0] = new Card(35);	//J HEARTS		Straight, jack high
		testHands[13][1] = new Card(5);		//7 CLUBS
		testHands[13][2] = new Card(39);	//2 SPADES
		testHands[13][3] = new Card(1);		//3 CLUBS
		testHands[13][4] = new Card(20);	//9 DIAMONDS
		testHands[13][5] = new Card(45);	//8 SPADES
		testHands[13][6] = new Card(21);	//10 DIAMONDS
		
		testHands[14][0] = new Card(33);	//9 HEARTS		Straight, ten high
		testHands[14][1] = new Card(23);	//Q DIAMONDS
		testHands[14][2] = new Card(17);	//6 DIAMONDS
		testHands[14][3] = new Card(6);		//8 CLUBS
		testHands[14][4] = new Card(51);	//A SPADES
		testHands[14][5] = new Card(31);	//7 HEARTS
		testHands[14][6] = new Card(8);		//10 CLUBS
		
		testHands[15][0] = new Card(12);	//A CLUBS		Straight, five high
		testHands[15][1] = new Card(3);		//5 CLUBS
		testHands[15][2] = new Card(13);	//2 DIAMONDS
		testHands[15][3] = new Card(41);	//4 SPADES
		testHands[15][4] = new Card(0);		//2 CLUBS
		testHands[15][5] = new Card(40);	//3 SPADES
		testHands[15][6] = new Card(27);	//3 HEARTS
		
		testHands[16][0] = new Card(35);	//J HEARTS		Three of a kind, jacks, king, queen kicker
		testHands[16][1] = new Card(9);		//J CLUBS
		testHands[16][2] = new Card(48);	//J SPADES
		testHands[16][3] = new Card(8);		//10 CLUBS
		testHands[16][4] = new Card(36);	//Q HEARTS
		testHands[16][5] = new Card(11);	//K CLUBS
		testHands[16][6] = new Card(5);		//7 CLUBS
		
		testHands[17][0] = new Card(34);	//10 HEARTS 	Three-of-a-kind jacks, king, ten kicker
		testHands[17][1] = new Card(35);	//J HEARTS
		testHands[17][2] = new Card(24);	//K DIAMONDS
		testHands[17][3] = new Card(26);	//2 HEARTS
		testHands[17][4] = new Card(9);		//J CLUBS
		testHands[17][5] = new Card(48);	//J SPADES
		testHands[17][6] = new Card(14);	//3 DIAMONDS
		
		testHands[18][0] = new Card(26);	//2 HEARTS 		Three-of-a-kind jacks, five, four kicker
		testHands[18][1] = new Card(40);	//3 SPADES
		testHands[18][2] = new Card(9);		//J CLUBS
		testHands[18][3] = new Card(48);	//J SPADES
		testHands[18][4] = new Card(15);	//4 DIAMONDS
		testHands[18][5] = new Card(35);	//J HEARTS
		testHands[18][6] = new Card(16);	//5 DIAMONDS
		
		testHands[19][0] = new Card(47);	//10 SPADES		Three of a kind, tens, king, five kicker
		testHands[19][1] = new Card(34);	//10 HEARTS
		testHands[19][2] = new Card(8);		//10 CLUBS
		testHands[19][3] = new Card(13);	//2 DIAMONDS
		testHands[19][4] = new Card(14);	//3 DIAMONDS
		testHands[19][5] = new Card(29);	//5 HEARTS
		testHands[19][6] = new Card(50);	//K SPADES
		
		testHands[20][0] = new Card(29);	//5 HEARTS		Two pair, kings and queens, jack kicker
		testHands[20][1] = new Card(24);	//K DIAMONDS
		testHands[20][2] = new Card(9);		//J CLUBS
		testHands[20][3] = new Card(49);	//Q SPADES
		testHands[20][4] = new Card(11);	//K CLUBS
		testHands[20][5] = new Card(36);	//Q HEARTS
		testHands[20][6] = new Card(0);		//2 CLUBS
		
		testHands[21][0] = new Card(47);	//10 SPADES		Two-Pair, kings and queens, ten kicker
		testHands[21][1] = new Card(24);	//K DIAMONDS
		testHands[21][2] = new Card(11);	//K CLUBS
		testHands[21][3] = new Card(23);	//Q DIAMONDS
		testHands[21][4] = new Card(41);	//4 SPADES
		testHands[21][5] = new Card(4);		//6 CLUBS
		testHands[21][6] = new Card(36);	//Q HEARTS
		
		testHands[22][0] = new Card(14);	//3 DIAMONDS 	Two-Pair, kings and queens, three kicker
		testHands[22][1] = new Card(39);	//2 SPADES
		testHands[22][2] = new Card(11);	//K CLUBS
		testHands[22][3] = new Card(13);	//2 DIAMONDS
		testHands[22][4] = new Card(37);	//K HEARTS
		testHands[22][5] = new Card(23);	//Q DIAMONDS
		testHands[22][6] = new Card(49);	//Q SPADES
		
		testHands[23][0] = new Card(21);	//10 DIAMONDS	Two pair tens and nines, eight kicker
		testHands[23][1] = new Card(8);		//10 CLUBS
		testHands[23][2] = new Card(44);	//7 SPADES
		testHands[23][3] = new Card(19);	//8 DIAMONDS
		testHands[23][4] = new Card(45);	//8 SPADES
		testHands[23][5] = new Card(20);	//9 DIAMONDS
		testHands[23][6] = new Card(7);		//9 CLUBS
		
		testHands[24][0] = new Card(47);	//10 SPADES		Two Pair tens and eights, ace kicker
		testHands[24][1] = new Card(37);	//K HEARTS
		testHands[24][2] = new Card(6);		//8 CLUBS
		testHands[24][3] = new Card(38);	//A HEARTS
		testHands[24][4] = new Card(0);		//2 CLUBS
		testHands[24][5] = new Card(19);	//8 DIAMONDS
		testHands[24][6] = new Card(21);	//10 DIAMONDS
		
		testHands[25][0] = new Card(32);	//8 HEARTS		Two pair eights and threes, ten kicker
		testHands[25][1] = new Card(6);		//8 CLUBS
		testHands[25][2] = new Card(26);	//2 HEARTS
		testHands[25][3] = new Card(14);	//3 DIAMONDS
		testHands[25][4] = new Card(40);	//3 SPADES
		testHands[25][5] = new Card(34);	//10 HEARTS
		testHands[25][6] = new Card(0);		//2 CLUBS
		
		testHands[26][0] = new Card(33);	//9 HEARTS		Two pair, eights and threes, nine kicker
		testHands[26][1] = new Card(6);		//8 CLUBS
		testHands[26][2] = new Card(27);	//3 HEARTS
		testHands[26][3] = new Card(39);	//2 SPADES
		testHands[26][4] = new Card(45);	//8 SPADES
		testHands[26][5] = new Card(14);	//3 DIAMONDS
		testHands[26][6] = new Card(41);	//4 SPADES
		
		testHands[27][0] = new Card(3);		//5 CLUBS		Pair of fives, ace, jack, nine kicker
		testHands[27][1] = new Card(27);	//3 HEARTS
		testHands[27][2] = new Card(35);	//J HEARTS
		testHands[27][3] = new Card(13);	//2 DIAMONDS
		testHands[27][4] = new Card(46);	//9 SPADES
		testHands[27][5] = new Card(42);	//5 SPADES
		testHands[27][6] = new Card(12);	//A CLUBS
		
		testHands[28][0] = new Card(29);	//5 HEARTS		Pair of fives, ace, jack, eight kicker
		testHands[28][1] = new Card(3);		//5 CLUBS
		testHands[28][2] = new Card(51);	//A SPADES
		testHands[28][3] = new Card(9);		//J CLUBS
		testHands[28][4] = new Card(15);	//4 DIAMONDS
		testHands[28][5] = new Card(6);		//8 CLUBS
		testHands[28][6] = new Card(13);	//2 DIAMONDS
		
		testHands[29][0] = new Card(16);	//5 DIAMONDS	Pair of fives ace, ten, nine kicker
		testHands[29][1] = new Card(38);	//A HEARTS
		testHands[29][2] = new Card(42);	//5 SPADES
		testHands[29][3] = new Card(5);		//7 CLUBS
		testHands[29][4] = new Card(21);	//10 DIAMONDS
		testHands[29][5] = new Card(20);	//9 DIAMONDS
		testHands[29][6] = new Card(6);		//8 CLUBS
		
		testHands[30][0] = new Card(37);	//K HEARTS		Pair of fives, king, queen, jack kicker
		testHands[30][1] = new Card(16);	//5 DIAMONDS
		testHands[30][2] = new Card(49);	//Q SPADES
		testHands[30][3] = new Card(6);		//8 CLUBS
		testHands[30][4] = new Card(15);	//4 DIAMONDS
		testHands[30][5] = new Card(22);	//J DIAMONDS
		testHands[30][6] = new Card(29);	//5 HEARTS
		
		testHands[31][0] = new Card(15);	//4 DIAMONDS	Pair of twos, king, ten, nine kicker
		testHands[31][1] = new Card(50);	//K SPADES
		testHands[31][2] = new Card(26);	//2 HEARTS
		testHands[31][3] = new Card(8);		//10 CLUBS
		testHands[31][4] = new Card(13);	//2 DIAMONDS
		testHands[31][5] = new Card(7);		//9 CLUBS
		testHands[31][6] = new Card(14);	//3 DIAMONDS
		
		testHands[32][0] = new Card(0);		//2 CLUBS		High card, ace, ten, nine, eight, four
		testHands[32][1] = new Card(1);		//3 CLUBS
		testHands[32][2] = new Card(2);		//4 CLUBS
		testHands[32][3] = new Card(6);		//8 CLUBS
		testHands[32][4] = new Card(33);	//9 HEARTS
		testHands[32][5] = new Card(38);	//A HEARTS
		testHands[32][6] = new Card(34);	//10 HEARTS
		
		testHands[33][0] = new Card(0);		//2 CLUBS		High card, queen, ten, nine, eight, five
		testHands[33][1] = new Card(29);	//5 HEARTS
		testHands[33][2] = new Card(1);		//3 CLUBS
		testHands[33][3] = new Card(45);	//8 SPADES
		testHands[33][4] = new Card(20);	//9 DIAMONDS
		testHands[33][5] = new Card(21);	//10 DIAMONDS
		testHands[33][6] = new Card(23);	//Q DIAMONDS
		
		testHands[34][0] = new Card(26);	//2 HEARTS		High card, nine, eight, seven, five, four
		testHands[34][1] = new Card(40);	//3 SPADES
		testHands[34][2] = new Card(2);		//4 CLUBS
		testHands[34][3] = new Card(3);		//5 CLUBS
		testHands[34][4] = new Card(18);	//7 DIAMONDS
		testHands[34][5] = new Card(19);	//8 DIAMONDS
		testHands[34][6] = new Card(46);	//9 SPADES
		
		for(int i = 0; i < testHands.length; i++){
			results[i] = HandAnalyzer.getPokerHand(testHands[i]);
		}
	}//static block
	
	@Test
	public void testCheckHand(){
		PokerHand expected[] = new PokerHand[]{
				new StraightFlush(new Card[]{		//Royal Flush
						new Card(51),
						new Card(50),
						new Card(49),
						new Card(48),
						new Card(47)
				}), new StraightFlush(new Card[]{	//Straight Flush (9 high)
						new Card(33),
						new Card(32),
						new Card(31),
						new Card(30),
						new Card(29)
				}),new StraightFlush(new Card[]{	//Straight Flush (8 high)
						new Card(32),
						new Card(31),
						new Card(30),
						new Card(29),
						new Card(28)
				}),new StraightFlush(new Card[]{	//Straight Flush (5 high)
						new Card(13),
						new Card(14),
						new Card(15),
						new Card(16),
						new Card(25)
				}), new FourOfAKind(new Card[]{		//Four-of-a-Kind (sixes)
						new Card(38),
						new Card(4),
						new Card(17),
						new Card(30),
						new Card(43)
				}), new FourOfAKind(new Card[]{		//Four-of-a-Kind (sixes)
						new Card(17),
						new Card(4),
						new Card(30),
						new Card(43),
						new Card(11)
				}), new FullHouse(new Card[]{		//Full House (A's full of 2's)
						new Card(38),
						new Card(51),
						new Card(12),
						new Card(0),
						new Card(26)
				}), new FullHouse(new Card[]{		//Full House (9's full of A's)
						new Card(33),
						new Card(7),
						new Card(20),
						new Card(12),
						new Card(38)
				}), new FullHouse(new Card[]{		//Full House (9's full of K's)
						new Card(46),
						new Card(33),
						new Card(7),
						new Card(11),
						new Card(37)
				}), new FullHouse(new Card[]{		//Full House (5's full of 8's)
						new Card(3),
						new Card(29),
						new Card(42),
						new Card(6),
						new Card(19)
				}), new Flush(new Card[]{			//Flush (A high)
						new Card(33),
						new Card(34),
						new Card(35),
						new Card(37),
						new Card(38)
				}), new Flush(new Card[]{			//Flush (K high)
						new Card(24),
						new Card(16),
						new Card(15),
						new Card(14),
						new Card(13)
				}), new Flush(new Card[]{			//Flush (8 high)
						new Card(0),
						new Card(1),
						new Card(3),
						new Card(4),
						new Card(6)
				}), new Straight(new Card[]{		//Straight (7 to J)
						new Card(5),
						new Card(45),
						new Card(20),
						new Card(21),
						new Card(35)
				}), new Straight(new Card[]{		//Straight (6 to 10)
						new Card(8),
						new Card(33),
						new Card(6),
						new Card(31),
						new Card(17)
				}), new Straight(new Card[]{		//Straight (A to 5)
						new Card(12),
						new Card(13),
						new Card(40),
						new Card(41),
						new Card(3)
				}), new ThreeOfAKind(new Card[]{	//Three-of-a-kind (J's)
						new Card(35),
						new Card(9),
						new Card(48),
						new Card(36),
						new Card(11)
				}), new ThreeOfAKind(new Card[]{	//Three-of-a-kind (J's)
						new Card(35),
						new Card(9),
						new Card(48),
						new Card(24),
						new Card(34)
				}), new ThreeOfAKind(new Card[]{	//Three-of-a-kind (J's)
						new Card(9),
						new Card(48),
						new Card(35),
						new Card(16),
						new Card(15)
				}), new ThreeOfAKind(new Card[]{	//Three-of-a-kind (10's)
						new Card(47),
						new Card(34),
						new Card(8),
						new Card(29),
						new Card(50)
				}), new TwoPair(new Card[]{			//Two-Pair (K's and Q's)
						new Card(24),
						new Card(11),
						new Card(49),
						new Card(36),
						new Card(9)
				}), new TwoPair(new Card[]{			//Two-Pair (K's and Q's)
						new Card(24),
						new Card(11),
						new Card(23),
						new Card(36),
						new Card(47)
				}), new TwoPair(new Card[]{			//Two-Pair (K's and Q's)
						new Card(11),
						new Card(37),
						new Card(23),
						new Card(49),
						new Card(14)
				}), new TwoPair(new Card[]{			//Two-Pair (10's and 9's)
						new Card(21),
						new Card(8),
						new Card(20),
						new Card(7),
						new Card(19)
				}), new TwoPair(new Card[]{			//Two-Pair (10's and 8's)
						new Card(47),
						new Card(21),
						new Card(6),
						new Card(19),
						new Card(38)
				}), new TwoPair(new Card[]{			//Two-Pair (8's and 3's)
						new Card(32),
						new Card(6),
						new Card(14),
						new Card(40),
						new Card(34)
				}), new TwoPair(new Card[]{			//Two-Pair (8's and 3's)
						new Card(6),
						new Card(45),
						new Card(27),
						new Card(14),
						new Card(33)
				}), new Pair(new Card[]{			//Pair (5's)
						new Card(3),
						new Card(42),
						new Card(12),
						new Card(35),
						new Card(46)
				}), new Pair(new Card[]{			//Pair (5's)
						new Card(29),
						new Card(3),
						new Card(51),
						new Card(9),
						new Card(6)
				}), new Pair(new Card[]{			//Pair (5's)
						new Card(16),
						new Card(42),
						new Card(38),
						new Card(21),
						new Card(20)
				}), new Pair(new Card[]{			//Pair (5's)
						new Card(16),
						new Card(29),
						new Card(37),
						new Card(49),
						new Card(22)
				}), new Pair(new Card[]{			//Pair (2's)
						new Card(13),
						new Card(26),
						new Card(50),
						new Card(8),
						new Card(7)
				}), new HighCard(new Card[]{		//High-Card (A)(Best possible high card)
						new Card(2),
						new Card(6),
						new Card(33),
						new Card(38),
						new Card(34)
				}),	new HighCard(new Card[]{		//High-Card (Q)
						new Card(23),
						new Card(21),
						new Card(20),
						new Card(45),
						new Card(29)
				}), new HighCard(new Card[]{		//High-Card (9)(Worst possible high card)
						new Card(2),
						new Card(3),
						new Card(18),
						new Card(19),
						new Card(46)
				})
		};
		
		for(int i = 0; i < testHands.length; i++){
			assertEquals(0, expected[i].compareTo(results[i]));
		}
	}//testCheckHand()
	
	@Test
	public void testCompareTo(){
		for(int i = 0; i < results.length; i++){
			for(int j = 0; j < results.length; j++){
				if(i == j){									//Same hand, should be 0.
					assertEquals(0, results[i].compareTo(results[j]));
				}else if(i < j){							//If j is further down the list, it's a worse hand.
					assertEquals(1, results[i].compareTo(results[j]));
				}else{										//If j is further up the list, it's the better hand.
					assertEquals(-1, results[i].compareTo(results[j]));
				}
			}
		}
	}//testCompareTo()
	
	@Test
	public void testGetHandMessage(){
		String expected[] = new String[]{
				"royal flush",
				"straight flush, nine high",
				"straight flush, eight high",
				"straight flush, five high",
				"four of a kind, sixes",
				"four of a kind, sixes",
				"full house, aces full of twos",
				"full house, nines full of aces",
				"full house, nines full of kings",
				"full house, fives full of eights",
				"flush, ace high",
				"flush, king high",
				"flush, eight high",
				"straight, jack high",
				"straight, ten high",
				"straight, five high",
				"three of a kind, jacks",
				"three of a kind, jacks",
				"three of a kind, jacks",
				"three of a kind, tens",
				"two pair, kings and queens",
				"two pair, kings and queens",
				"two pair, kings and queens",
				"two pair, tens and nines",
				"two pair, tens and eights",
				"two pair, eights and threes",
				"two pair, eights and threes",
				"pair of fives",
				"pair of fives",
				"pair of fives",
				"pair of fives",
				"pair of twos",
				"high card, ace",
				"high card, queen",
				"high card, nine"	
		};
		
		for(int i = 0; i < testHands.length; i++){
			assertEquals(expected[i], results[i].toString());
		}
	}//testGetHandMessage()
	
	@Test
	public void testGetShuffledDeck(){
		final int numDecks = 10;
		
		Card[][] decks = new Card[numDecks][];
		Card currentCard;
		int samePosition;
		boolean found;
		
		for(int i = 0; i < numDecks; i++){
			decks[i] = Toolbox.getShuffledDeck();
		}
		
		if(decks[0].length != 52){							//Ensures deck has 52 cards.
			fail("The deck isn't the correct size!");
		}
		
		for(int i = 0; i < numDecks; i++){
			for(int j = 0; j < 52; j++){					//Ensures every card is within every deck.
				currentCard = new Card(j);
				found = false;
				for(int k = 0; k < 52; k++){
					if(decks[i][k].equals(currentCard)){
						k = 52;
						found = true;
					}
				}
				if(!found){
					fail("The card " + currentCard + " was not found.");
				}
			}
		}
		
		for(int i = 0; i < decks.length; i++){				//Ensures the decks are different enough from one another.
			for(int j = i + 1; j < decks.length; j++){
				samePosition = 0;
				for(int k = 0; k < 52; k++){
					if(decks[i][k].equals(decks[j][k])){
						samePosition++;
					}
				}
				if(samePosition > 10){
					fail("The decks were too similar, they had " + samePosition + " of their cards in identical locations.");
				}
			}
		}
	}//testGetShuffledDeck()
	
	@Test
	public void testRndNumGen(){		//TODO Better define what spread is acceptable?
		final int runs = 2000;
		int count[] = new int[11], rnd;
		boolean[] found = new boolean[11];
		
		for(int i = 0; i < found.length; i++){
			found[i] = false;
		}
		
		for(int i = 0; i < runs; i++){			//Runs the random number generator as many times as specified for 0-2.
			rnd = Toolbox.rndNumGen(2);
			count[rnd]++;
			if(rnd > 2 || rnd < 0){
				fail("The number was outside of the given range.");
			}
		}
		
		for(int i = 0; i < 3; i++){				//Verifies the distribution.
			if(count[i] > runs/3 + runs/20){
				fail("One of the numbers from the first set occurred too often ");
			}if(count[i] < runs/3 - runs/20){
				fail("One of the numbers from the first set didn't occur often enough.");
			}
			count[i] = 0;						//Resets the counts.
		}
		
		for(int i = 0; i < runs; i++){			//Runs the random number generator as many times as specified for 0-10.
			rnd = Toolbox.rndNumGen(10);
			count[rnd]++;
		}
		
		for(int i = 0; i < count.length; i++){	//Verifies the distribution.
			if(count[i] > runs/11 + runs/30){
				fail("One of the numbers from the second set occurred too often");
			}if(count[i] < runs/11 - runs/30){
				fail("One of the numbers from the second set didn't occur often enough.");
			}
		}
		
	}//testRndNumGen()
	
}//HandAnalyzerTest

/*
 * Located and fixed bugs:
 * 1. Straight wasn't handling A2345 correctly (2345A instead, giving A high).
 * 2. Straight wasn't handling duplicates correctly (233456 giving 23345 instead of 23456).
 * 3. Four of a kind was comparing Cards instead of their value.
 * 4. Small changes to the toString() methods, not really bugs though, that was planned.
 * 5. GetShuffledDeck() was always leaving the ace of spades on the top of the deck.
 * 6. checkStraightFlush() was flawed. 12, 13, 14, 15, 16 returned true. (Ac,2d,3d,4d,5d)
 * 
 * Existing bugs:
 * None that I'm aware of!
 */ 
