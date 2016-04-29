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
		int option;
		while(true){
			option = JOptionPane.showOptionDialog(null, "Would you like to play Mastermind?","MasterMind",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION)
			{
				System.exit(-1);
			}
			String extraOptions = JOptionPane.showInputDialog("How many guesses would you like?");
			int guesses = Integer.parseInt(extraOptions);
			extraOptions = JOptionPane.showInputDialog("How many pegs would you like?");
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

			}
//			JPanel pegGrid = new JPanel (new GridLayout(1,pegs));
			DrawCircle[][] circle = new DrawCircle[guesses][pegs];
			DrawCircle[][] pegCircle = new DrawCircle[guesses][pegs];

			for(int i = 0; i < guesses; i ++)
			{

				for(int j = 0; j < pegs; j ++)
				{
					DrawCircle c = new DrawCircle(12, 12, Color.BLACK);
					circle[i][j] = c;
					grid[i].add(c);
					DrawCircle pc = new DrawCircle(4,4, Color.gray);
					pegCircle[i][j] = pc;
					pegGrid[i].add(pc);
					
				}
			}
	//		grid.add(pegGrid);
			for(int i = 0; i < guesses; i ++)
			{
				grid[i].add(pegGrid[i]);
				mainFrame.add(grid[i]);
			}

//			mainFrame.pack();
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
	    					grid[turn - 1].getRootPane().revalidate();
	            		}
	            		int [] clues = g.runGame(guess);
	            		for (int i = 0; i < clues[0]; i ++)
	            		{
	            			pegGrid[turn - 1].remove(i);
	    					DrawCircle c = new DrawCircle(8, 8, Color.BLACK);
	    					pegGrid[turn - 1].add(c, i);
	    					pegGrid[turn - 1].getRootPane().revalidate();

	            		}
	            		for (int i = 0; i < clues[1]; i ++)
	            		{
	            			pegGrid[turn - 1].remove(i);
	    					DrawCircle c = new DrawCircle(8, 8, Color.WHITE);
	    					pegGrid[turn - 1].add(c, i);
	    					pegGrid[turn - 1].getRootPane().revalidate();

	            		}
	            		if(clues[0] == 4)
	            		{
		            		String options[] = {"OK"};
							JOptionPane.showOptionDialog(null, "YOU WIN!!! \n" + guess,"Winner Over Here!",JOptionPane.NO_OPTION,JOptionPane.ERROR_MESSAGE,null, options, options[0] );            			
							break;
	            		}
	            		turn -= 1;
	            	}
	            }  
	        }
			option = JOptionPane.showOptionDialog(null, "Play Again?","Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, null, null );
			if(option == JOptionPane.NO_OPTION)
			{
				System.exit(-1);
			}
		}

	}
}
