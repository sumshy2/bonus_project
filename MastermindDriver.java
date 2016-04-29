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

    
	public MastermindDriver()
	{
		inputT = new InputText();
		Colors = "BGOPRY";
		regex = "[" + Colors + "]*";
		
	}
	public static void main(String[] args)
	{ 
		MastermindDriver master = new MastermindDriver();
		master.GUIMaker();
	}
	public void GUIMaker()
	{
		
		
		String rules = "The computer picks a sequence of colors. The number of colors is the code length. The default code\n" +
		"length is typically 4 but you can specify what code length you want. The objective of the game is to guess\n"
		+ "the exact positions of the colors in the computer's sequence. After filling a line with your guesses and \n" +
		"clicking on the 'Check' button, the computer responses with the result of your guess. For each color in your\n"+
		"guess that is in the correct color and correct position in the code sequence, the computer display a small\n"+
		"black color on the right side of the current guess. For each color in your guess that is in the correct color\n"+
		"but is NOT in the correct position in the code sequence, the computer display a small white color on the right\n" + 
		"side of the current guess. You win the game when you manage to guess all the colors in the code sequence and\n" +
		"when they all in the right position. You lose the game if you use all attempts without guessing the computer\n" +
		"code sequence. When asked to input the number of guesses and number of pegs that you would like you can hit\n" +
		"cancel on each pop-up to get a default of 12 guesses and 4 pegs. Continue?\n";
		
		ErrorCheck e = new ErrorCheck();
		int option;
		while(true){
			Color background = new Color(230,230,250);
			option = JOptionPane.showOptionDialog(null, "Would you like to play Mastermind?","MasterMind",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION)
			{
				System.exit(-1);
			}
			option = JOptionPane.showOptionDialog(null,rules, "RULES",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null);
			if(option == JOptionPane.NO_OPTION)
			{
				System.exit(-1);
			}
			String extraOptions = JOptionPane.showInputDialog("How many guesses would you like?");
			while(!e.checkGuess(extraOptions)){
				JOptionPane.showMessageDialog(null, "Improper Input. Try again.");
				extraOptions = JOptionPane.showInputDialog("How many guesses would you like?");
			}
			int guesses = Integer.parseInt(extraOptions);
			
			extraOptions = JOptionPane.showInputDialog("How many pegs would you like?");
			while(!e.checkPegs(extraOptions)){
				JOptionPane.showMessageDialog(null, "Improper Input. Try again.");
				extraOptions = JOptionPane.showInputDialog("How many guesses would you like?");
			}
			int pegs = Integer.parseInt(extraOptions);
			
			option = JOptionPane.showOptionDialog(null, "Would you like add Maroon to your color options?\nM will stand for Maroon","Color Options",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			String extra = "";
			if(option == JOptionPane.YES_OPTION)
			{
				Colors += "M";
				extra += "M";
			}
			option = JOptionPane.showOptionDialog(null, "Would you like add Cyan to your color options?\nC will stand for Maroon","Color Options",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.YES_OPTION)
			{
				Colors += "C";
				extra += "C";
			}
			Game g = new Game(pegs, guesses, Colors.length(), extra);
			JFrame mainFrame = new JFrame("Mastermind");
			mainFrame.setSize(500, 500);
			mainFrame.setLayout(new GridLayout (12,1));
			JPanel[] grid = new JPanel[guesses];
			JPanel[] pegGrid = new JPanel[guesses];
			for(int i = 0; i < guesses; i ++)
			{
				grid[i] = new JPanel (new GridLayout(1,pegs));
				pegGrid[i] = new JPanel (new GridLayout(1,pegs));
				grid[i].setOpaque(true);
				pegGrid[i].setOpaque(true);
				grid[i].setBackground(background);
				pegGrid[i].setBackground(background);
			}

			for(int i = 0; i < guesses; i ++)
			{
				for(int j = 0; j < pegs; j ++)
				{
					DrawCircle c = new DrawCircle(12, 12, Color.BLACK);
					grid[i].add(c);
					DrawCircle pc = new DrawCircle(4,4, Color.gray);
					pegGrid[i].add(pc);					
				}
			}
			for(int i = 0; i < guesses; i ++)
			{
				grid[i].add(pegGrid[i]);
				mainFrame.add(grid[i]);
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
	        	JPanel input = new JPanel();
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
	            input.add(userText);
	            input.add(checkButton);
	            newInput.add(input);
	            newInput.pack();
	            newInput.setVisible(true);
	            
	            try {
					sema.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            String guess = inputT.getInput();
	            if(guess.length() == pegs)
	            {
	            	if(guess.matches(regex) == false)
	            	{
	            		String options[] = {"OK"};
						JOptionPane.showOptionDialog(null, "Incorrect Guess!","WRONG!",JOptionPane.NO_OPTION,JOptionPane.ERROR_MESSAGE,null, options, options[0] );
	            	}else if(guess.matches("history")){
	            		String options[] = {"OK"};
						JOptionPane.showOptionDialog(null, g.getHistory(),"History",JOptionPane.NO_OPTION,JOptionPane.ERROR_MESSAGE,null, options, options[0] );
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
	    					grid[turn - 1].remove(i);
	    					grid[turn - 1].add(c, i);
//	    					grid[turn - 1].setBackground(background);
	    					grid[turn - 1].revalidate();
	    					grid[turn - 1].repaint();
	            		}

	            		int [] clues = g.runGame(guess);
	            		for (int i = 0; i < clues[0]; i ++)
	            		{
	            			pegGrid[turn - 1].remove(i);
	    					DrawCircle c = new DrawCircle(8, 8, Color.BLACK);
	    					pegGrid[turn - 1].add(c, i);
	//    					pegGrid[turn - 1].setBackground(background);
	    					pegGrid[turn - 1].revalidate();
	    					pegGrid[turn - 1].repaint();
	            		}
	            		for (int i = 0; i < clues[1]; i ++)
	            		{
	            			pegGrid[turn - 1].remove(i + clues[0]);
	    					DrawCircle c = new DrawCircle(8, 8, Color.WHITE);
	    					pegGrid[turn - 1].add(c, i + clues[0]);
//	    					pegGrid[turn - 1].setBackground(background);
	    					pegGrid[turn - 1].revalidate();
	    					pegGrid[turn - 1].repaint();
	            		}

	            		if(clues[0] == 4)
	            		{
		            		String options[] = {"OK"};
							JOptionPane.showOptionDialog(null, "YOU WIN!!! \n" + guess,"Winner Over Here!",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null, options, options[0] );            			
							break;
	            		}
	            		turn -= 1;
	            	}
	            }  
	        }
			option = JOptionPane.showOptionDialog(null, "Play Again?","Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION)
			{
				JOptionPane.showMessageDialog(null, "Thanks for playing! Goodbye.");
				System.exit(-1);
			}
		}

	}
}
