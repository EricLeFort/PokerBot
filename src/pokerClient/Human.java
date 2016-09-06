package pokerClient;
/**
 * @author Eric Le Fort
 * @version 01
 */

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Human extends Player{
	private String playingStyle = "N";
	private double percentShowdownsWon = 0, percentFlops = 0, percentFlopsBB = 0, percentFlopsSB = 0;
	private int handsSeen = 0, handsSeenBB = 0, handsSeenSB = 0, flopsSeen = 0, flopsSeenBB = 0,
			flopsSeenSB = 0, showdownsWon = 0, showdownsSeen = 0;
	
	public Human(String name, int stack){
		super(name, stack);
	}//Human(String name, int stack)
	
	public Human(String name, String playingStyle, int stack, int handsSeen, int handsSeenBB, int handsSeenSB, int flopsSeen, int flopsSeenBB, int flopsSeenSB, int showdownsWon, int showdownsSeen){
		super(name, stack);
		this.playingStyle = playingStyle;
		this.handsSeen = handsSeen;
		this.flopsSeen = flopsSeen;
		this.flopsSeenBB = flopsSeenBB;
		this.flopsSeenSB = flopsSeenSB;
		this.showdownsWon = showdownsWon;
		this.showdownsSeen = showdownsSeen;
		getPercentShowdownsWon();
		getPercentPlayed();
		getPercentBB();
		getPercentSB();
	}
	
	public String getPlayingStyle(){
		return this.playingStyle;
	}//getPlayingStyle()
	
	public int getFlopsSeen(){
		return this.flopsSeen;
	}//getFlopsSeen()
	
	/**
	 * Receives the value of the bet that the user wishes to place or lets them just call if they changed their mind.
	 * @param currentBetSize
	 * @return betSize
	 */	
	public double getBetSize(double currentBetSize){
		Scanner text = new Scanner(System.in);
		double betSize = -1;
		boolean valid = false;
		
		System.out.println("Enter the size of the bet you wish to place");
		
		do{
			try{
				String input = text.nextLine();
				betSize = Integer.parseInt(input);
			
				if(betSize == -1 || betSize >= currentBetSize * 2){
					valid = true;
				}else{
					JOptionPane.showMessageDialog(null, "Please make sure to enter a bet at least double the last bet's size or"
							+ " -1 if you wish to change your decision.", "", JOptionPane.PLAIN_MESSAGE);
				}
			}catch(Exception e){
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Please make sure to enter a bet at least double the last bet's size or"
						+ " -1 if you wish to change your decision.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}while(!valid);
		text.close();
		return betSize;
	}//getBetSize(double currentBetSize)
	
	public int getFlopsSeenBB(){
		return this.flopsSeenBB;
	}//getFlopsSeenBB()
	
	public int getFlopsSeenSB(){
		return this.flopsSeenSB;
	}//getFlopsSeenSB()
	
	public int getShowdownsWon(){
		return this.showdownsWon;
	}//getShowdownsWon()
	
	public int getHandsSeen(){
		return this.handsSeen;
	}//getHandsSeen()

	public int getHandsSeenBB(){
		return this.handsSeenBB;
	}//getHandsSeenBB()

	public int getHandsSeenSB(){
		return this.handsSeenSB;
	}//getHandsSeenSB()
	
	public double getPercentShowdownsWon(){
		if(this.showdownsSeen == 0){
			return 0;
		}else{
			this.percentShowdownsWon = this.showdownsWon / this.showdownsSeen;
			return this.percentShowdownsWon;
		}
	}//getPercentShowdownsWon()
	
	public double getPercentPlayed(){
		if(this.handsSeen == 0){
			return 0;
		}else{
			this.percentFlops = this.flopsSeen / this.handsSeen;
			return this.percentFlops;
		}
	}//getPercentPlayed()
	
	public double getPercentBB(){
		if(this.handsSeenBB == 0){
			return 0;
		}else{
			this.percentFlopsBB = flopsSeenBB / handsSeenBB;
			return this.percentFlopsBB;	
		}
	}//getPercentBB()
	
	public double getPercentSB(){
		if(this.handsSeenSB == 0){
			return 0;
		}else{
			this.percentFlopsSB = flopsSeenSB / handsSeenSB;
			return this.percentFlopsSB;
		}
	}//getPercentSB()
	
	public void setPlayingStyle(String playingStyle){
		this.playingStyle = playingStyle;
	}//setPlayingStyle()
	
	public void flopSeen(){
		this.flopsSeen++;
		this.percentFlops = flopsSeen/handsSeen;		
	}//flopSeen()
	
	public void flopSeenBB(){
		this.flopsSeenBB++;
		this.percentFlopsBB = flopsSeen/flopsSeenBB;
	}//flopSeenBB()
	
	public void flopSeenSB(){
		this.flopsSeenSB++;
		this.percentFlopsSB = flopsSeen/flopsSeenSB;
	}//flopSeenSB()
	
	public int getShowdownsSeen(){
		return this.showdownsSeen;
	}//getShowdownsSeen()
	
	public void showdownWon(){
		this.showdownsWon++;
	}//showdownWon()
	
	public void showdownSeen(){
		this.showdownsSeen++;
		this.percentShowdownsWon = (showdownsWon/showdownsSeen) * 100;
	}//showdownSeen()
	
	public void handSeen(){
		this.handsSeen++;
	}//flopSeen()
	
	public void handSeenBB(){
		this.handsSeen++;
		this.handsSeenBB++;
	}//flopSeenBB()
	
	public void handSeenSB(){
		this.handsSeen++;
		this.handsSeenBB++;
	}//handSeenSB()
	
}
