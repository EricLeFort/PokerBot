package Testing;

import static org.junit.Assert.*;
import org.junit.Test;
import handStorage.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class HandDataParserTest{
	static File cashGameFiles[] = new File[21];
	static File tournamentFiles[] = new File[32];
	
	static{
		for(int i = 0; i < cashGameFiles.length; i++){
			cashGameFiles[i] = new File("./resources/handsForTesting/cashHands/" + i + ".txt");
		}
		for(int i = 0; i < tournamentFiles.length; i++){
			tournamentFiles[i] = new File("./resources/handsForTesting/tournamentHands/" + i + ".txt");
		}
	}
	
	@Test
	public void testReadCashHandFromFile(){
		Hand hand;
		Scanner reader;
		String fileContents, newline = System.getProperty("line.separator");
		
		for(int i = 0; i < cashGameFiles.length; i++){
			fileContents = "";
			System.out.println(cashGameFiles[i]);
			try{
				reader = new Scanner(cashGameFiles[i]);
				while(reader.hasNextLine()){
					fileContents += reader.nextLine() + newline;
				}
				fileContents = fileContents.substring(0, fileContents.length() - 1);		//Removes the extra newline being added.
				reader.close();
			}catch(FileNotFoundException fnfe){
				fnfe.printStackTrace();
			}
			
			hand = HandDataParser.readCashGameHandFromFile(cashGameFiles[i]);
			//System.out.println(hand);
			assertEquals(fileContents, hand.toString());
		}
	}//testReadCashHandFromFile()
	
}//HandDataParserTest
