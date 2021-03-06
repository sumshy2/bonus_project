/*
 * Dung Le
 * Sumedh Shah
 */
package bonus_project; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.concurrent.Semaphore;

/*MAIN DRIVER CLASS
 *Purpose: Handles user input (will ask user if they want to play).
 *If user wants to play, it will create a Game object WHICH REPRESENTS
 *ONE GAME. */

public class MastermindDriver{

	private String Colors;
	private String regex;
	private InputText inputT;
	private Semaphore sema = new Semaphore(0);

	private Color B = Color.BLUE;
	private Color G = Color.GREEN;
	private Color O = Color.ORANGE;
	private Color P = Color.MAGENTA;
	private Color R = Color.RED;
	private Color Y = Color.YELLOW;
	private Color C = Color.CYAN;
	private Color M = new Color(128, 0, 0);
	private String keyColor = new String();

	public MastermindDriver()
	{
		inputT = new InputText();
		Colors = "BGOPRY";
		regex = "[" + Colors + "]*";
		keyColor = "<html>B - Blue<br>G - Green<br>O - Orange<br>R - Red<br>Y - Yellow<br>P - Purple<html>";
	}
	public static void main(String[] args)
	{ 
		MastermindDriver master = new MastermindDriver();
		master.GUIMaker();
	}
	public void GUIMaker()
	{
		ErrorCheck e = new ErrorCheck();
		int option;
		while(true){
			Colors = "BGOPRY";
			regex = "[" + Colors + "]*";
			keyColor = "<html>B - Blue<br>G - Green<br>O - Orange<br>R - Red<br>Y - Yellow<br>P - Purple<html>";

			Color background = new Color(230,230,250);
			option = JOptionPane.showOptionDialog(null, "Would you like to play Mastermind?","MasterMind",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION)
			{
				System.exit(-1);
			}
			String rules = "The computer picks a sequence of colors.\nThe objective of the game is to guess the exact "
					+ "positions of the colors in the computer's sequence.\nAfter filling a line with your guesses and clicking on the 'Check' button,\n"
					+ "the computer responds with the result of your guess.\nFor each color in your"
			+ " guess that is in the correct color and correct position in the code sequence,\n"
			+ "the computer displays black on the right side.\nFor each color that is in the correct color but is NOT in the correct position, the computer displays white.\nYou win if you guess all the colors correctly and if they all in the right "
			+ "position.\nYou lose the game if you use all attempts without guessing correctly.\n"
			+ "NOTE: When asked to input the number of guesses and number of pegs that you would like\n"
			+ "you can hit 'Cancel' on each pop-up to get a default of 12 guesses and 4 pegs.\n"
			+ "Continue?\n";

			option = JOptionPane.showOptionDialog(null,rules,"RULES!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION)
			{
				System.exit(-1);
			}

			String extraOptions = JOptionPane.showInputDialog("How many guesses would you like?\nYou may have 5 to 20 guesses");

			while(!e.checkGuess(extraOptions)){
				JOptionPane.showMessageDialog(null, "Improper Input. Try again.");
				extraOptions = JOptionPane.showInputDialog("How many guesses would you like?\nYou may have 5 to 20 guesses");
			}
			int guesses = 0;
			if(extraOptions == null)
			{
				guesses = 12;
			}else{
				guesses = Integer.parseInt(extraOptions);
			}

			extraOptions = JOptionPane.showInputDialog("How many pegs would you like?\nYou may have 4 to 8 pegs");
			while(!e.checkPegs(extraOptions)){
				JOptionPane.showMessageDialog(null, "Improper Input. Try again.");
				extraOptions = JOptionPane.showInputDialog("How many guesses would you like?\nYou may have 5 to 8 pegs");
			}
			int pegs = 0;
			if(extraOptions == null)
			{
				pegs = 4;
			}else{
				pegs = Integer.parseInt(extraOptions);
			}

			option = JOptionPane.showOptionDialog(null, "Would you like add Maroon to your color options?\nM will stand for Maroon","Color Options",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			String extra = "";
			if(option == JOptionPane.YES_OPTION)
			{
				Colors += "M";
				extra += "M";
				keyColor = keyColor.substring(0, keyColor.lastIndexOf('<'));
				keyColor += "<br>M - Maroon<html>";
			}
			option = JOptionPane.showOptionDialog(null, "Would you like add Cyan to your color options?\nC will stand for Maroon","Color Options",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.YES_OPTION)
			{
				Colors += "C";
				extra += "C";
				keyColor = keyColor.substring(0, keyColor.lastIndexOf('<'));
				keyColor += "<br>C - Cyan<html>";
			}
			regex = "[" + Colors + "]*";
			Game g = new Game(pegs, guesses, Colors.length(), extra);
			JFrame mainFrame = new JFrame("Mastermind");
			mainFrame.setSize(500, 500);
			mainFrame.setLayout(new GridLayout (guesses + 1,1));
			JPanel[] grid = new JPanel[guesses + 1];
			JPanel[] pegGrid = new JPanel[guesses];
			grid[0] = new JPanel (new GridLayout(1,pegs));
			grid[0].setOpaque(true);
			grid[0].setBackground(background);

			for(int i = 0; i < guesses; i ++)
			{
				grid[i + 1] = new JPanel (new GridLayout(1,pegs));
				pegGrid[i] = new JPanel (new GridLayout(1,pegs));
				grid[i + 1].setOpaque(true);
				pegGrid[i].setOpaque(true);
				grid[i + 1].setBackground(background);
				pegGrid[i].setBackground(background);
			}
			for(int j = 0; j < pegs; j ++)
			{
				DrawCircle c = new DrawCircle(12, 12, Color.BLACK);
				grid[0].add(c);			
			}
			for(int i = 0; i < guesses; i ++)
			{
				for(int j = 0; j < pegs; j ++)
				{
					DrawCircle c = new DrawCircle(12, 12, Color.BLACK);
					grid[i + 1].add(c);
					DrawCircle pc = new DrawCircle(4,4, Color.gray);
					pegGrid[i].add(pc);					
				}
			}
			mainFrame.add(grid[0]);
			for(int i = 0; i < guesses; i ++)
			{
				grid[i + 1].add(pegGrid[i]);
				mainFrame.add(grid[i + 1]);
			}

			//			mainFrame.pack();
			mainFrame.getContentPane().setBackground(background);
			mainFrame.setVisible(true);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			int turn = guesses;
			while(turn > 0)
			{
				inputT.setInput("");
				JFrame newInput = new JFrame("Guess");
				newInput.setLayout(new GridLayout(2,1));
				JPanel input = new JPanel();
				JPanel input2 = new JPanel();
				JLabel keys = new JLabel(keyColor, JLabel.CENTER);
				final JTextField userText = new JTextField(6);
				JButton checkButton = new JButton("Check");

				checkButton.addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(e.getSource() == checkButton)
						{
							String data = userText.getText();  
							inputT.setInput(data);
							newInput.dispose();
							sema.release();
						}
					}
				});
				input.add(keys);
				input2.add(userText);
				input2.add(checkButton);
				newInput.add(input);
				newInput.add(input2);
				newInput.pack();
				newInput.setVisible(true);

				try {
					sema.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String guess = inputT.getInput();
				if(guess.matches("history")){
					String options[] = {"OK"};
					JOptionPane.showOptionDialog(null, g.getHistory(),"History",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null, options, options[0] );
				}else if(guess.matches(regex) == false || guess.length() != pegs)
				{
					String options[] = {"OK"};
					JOptionPane.showOptionDialog(null, "Incorrect Guess!","WRONG!",JOptionPane.NO_OPTION,JOptionPane.ERROR_MESSAGE,null, options, options[0] );
				}else{
					Color color = Color.BLACK;
					for(int i = 0; i < pegs; i ++)
					{
						if(guess.charAt(i) == 'B')
						{
							color = B;
						}else if(guess.charAt(i) == 'O')
						{
							color = O;
						}else if(guess.charAt(i) == 'G')
						{
							color = G;
						}else if(guess.charAt(i) == 'P')
						{
							color = P;
						}else if(guess.charAt(i) == 'M')
						{
							color = M;
						}else if(guess.charAt(i) == 'C')
						{
							color = C;
						}else if(guess.charAt(i) == 'Y')
						{
							color = Y;
						}else if(guess.charAt(i) == 'R')
						{
							color = R;
						}
						DrawCircle c = new DrawCircle(12, 12, color);
						grid[turn].remove(i);
						grid[turn].add(c, i);
						grid[turn].revalidate();
						grid[turn].repaint();
					}

					int [] clues = g.runGame(guess);
					for (int i = 0; i < clues[0]; i ++)
					{
						pegGrid[turn - 1].remove(i);
						DrawCircle c = new DrawCircle(8, 8, Color.BLACK);
						pegGrid[turn - 1].add(c, i);
						pegGrid[turn - 1].revalidate();
						pegGrid[turn - 1].repaint();
					}
					for (int i = 0; i < clues[1]; i ++)
					{
						pegGrid[turn - 1].remove(i + clues[0]);
						DrawCircle c = new DrawCircle(8, 8, Color.WHITE);
						pegGrid[turn - 1].add(c, i + clues[0]);
						pegGrid[turn - 1].revalidate();
						pegGrid[turn - 1].repaint();
					}

					if(clues[0] == 4)
					{
						for(int i = 0; i < pegs; i ++)
						{
							if(guess.charAt(i) == 'B')
							{
								color = B;
							}else if(guess.charAt(i) == 'O')
							{
								color = O;
							}else if(guess.charAt(i) == 'G')
							{
								color = G;
							}else if(guess.charAt(i) == 'P')
							{
								color = P;
							}else if(guess.charAt(i) == 'M')
							{
								color = M;
							}else if(guess.charAt(i) == 'C')
							{
								color = C;
							}else if(guess.charAt(i) == 'Y')
							{
								color = Y;
							}else if(guess.charAt(i) == 'R')
							{
								color = R;
							}
							DrawCircle c = new DrawCircle(12, 12, color);
							grid[0].remove(i);
							grid[0].add(c, i);
							grid[0].revalidate();
							grid[0].repaint();
						}

						String options[] = {"OK"};
						JOptionPane.showOptionDialog(null, "YOU WIN!!! \n" + guess,"Winner Over Here!",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null, options, options[0] );            			
						break;
					}
					turn -= 1;
				}
			}
			Color color = Color.BLACK;
			String secretCode = g.getSecretCode();
			for(int i = 0; i < pegs; i ++)
			{
				if(secretCode.charAt(i) == 'B')
				{
					color = B;
				}else if(secretCode.charAt(i) == 'O')
				{
					color = O;
				}else if(secretCode.charAt(i) == 'G')
				{
					color = G;
				}else if(secretCode.charAt(i) == 'P')
				{
					color = P;
				}else if(secretCode.charAt(i) == 'M')
				{
					color = M;
				}else if(secretCode.charAt(i) == 'C')
				{
					color = C;
				}else if(secretCode.charAt(i) == 'Y')
				{
					color = Y;
				}else if(secretCode.charAt(i) == 'R')
				{
					color = R;
				}
				DrawCircle c = new DrawCircle(12, 12, color);
				grid[0].remove(i);
				grid[0].add(c, i);
				grid[0].revalidate();
				grid[0].repaint();
			}
			String options[] = {"OK"};
			JOptionPane.showOptionDialog(null, "YOU LOST!!! \n","LOSER OVER HERE!",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null, options, options[0] );            			

			option = JOptionPane.showOptionDialog(null, "Play Again?","Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION)
			{
				System.exit(-1);
			}
			mainFrame.dispose();
		}

	}
}
