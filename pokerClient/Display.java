package pokerClient;
/**
 * @author Eric Le Fort
 * @version 1
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JFrame{
	private static final long serialVersionUID = -8908495105559992514L;

	public Display(){
		initUI();
	}//Display()

	private void initUI(){
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton startBtn = new JButton("Start");
		startBtn.setBounds(100, 60, 80, 30);
		startBtn.setToolTipText("Press here to start the game!");

		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {        	   
				PokerBot bot = new PokerBot();
			}
		});

		panel.add(startBtn);

		setTitle("Poker Bot Lobby");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}//initUI()
	 
	}
