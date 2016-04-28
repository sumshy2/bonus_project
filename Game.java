package bonus_project;

import java.awt.List;

/*GAME CLASS 
 * Purpose: Handles overall game and ends once the
 * user is done with 12 tries or wins the game. runGame()
 * is the main function in this class it'll create a new Pegs object 
 * and call the Pegs' processInput() function every time the
 * user makes a guess*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
	String secretCode;
	int numPegs; 
	int numGuesses; 
	int numColors; 
	String[] extraColors;
	
	public Game(){
		numGuesses = 12;  //DEFAULT
		numPegs = 4; //DEFAULT
		numColors = 6; //DEFAULT
		secretCode = generateRandomCode();
		extraColors = new String[0];
	}
	public Game(int numPegs, int numGuesses, int numColors, String[] extraColors){
		this.numGuesses = numGuesses;
		this.numPegs = numPegs;
		this.extraColors = extraColors;
		this.numColors = extraColors.length + 6;
	}
	
	
	//called by MastermindDriver class and will run as many times as the user wants it to run
	//WHAT IT DOES: starts the game
	public void runGame(){
		
		//while 12 guesses
			//Prompt user for guess (get it into String variable userInput)
			//Pegs p = new Pegs(userInput)
			//p.processInput(secretCode)
			//print p.getBlackPegs() and p.getWhitePegs() to user
			//addToHistory(p); will add user's move to history of his moves
			//IF USER GOT 4 BLACK PEGS, BREAK OUT OF WHILE LOOP!!!!
			//decrement guesses by 1 if user didn't get 4 black pegs
		//end while loop
	}
	
/*	//if user wants to get history of his moves and the pegs with the move
	public void getHistory(){
		
	}
	
	//adds move to history of moves
	public void addToHistory(Pegs p){
		
	}*/
	
	//generates random secret code of specified length
	public String generateRandomCode(){
		String secretCode = "";
		//magenta, violet, auburn {"M","V","A"}
		ArrayList<String> array = new ArrayList<String>(Arrays.asList(new String[] {"B","G","O","P","R","Y"}));
		//String[] array = {"B","G","O","P","R","Y"}; //DEFAULT: blue, green, orange, purple, red, yellow
/*		for(int i = 0; i < extraColors.length; i++){
			array.add(extraColors[i]);
		}*/

		int min = 0; 
		int max = numColors - 1;
		Random rand = new Random();
		for(int i = 0; i < numPegs; i++){
			int r = rand.nextInt((max - min) + 1) + min;
			secretCode+=array.get(i);
		}
		return secretCode;
	}
}
