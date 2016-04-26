package bonus_project;

/*GAME CLASS 
 * Purpose: Handles overall game and ends once the
 * user is done with 12 tries or wins the game. runGame()
 * is the main function in this class it'll create a new Pegs object 
 * and call the Pegs' processInput() function every time the
 * user makes a guess*/

import java.util.ArrayList;

public class Game {
	
	ArrayList<Pegs> history;
	boolean check;
	String secretCode;
	public Game(boolean check){
		this.check = check;
		history = new ArrayList<Pegs>();
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
	
	//if user wants to get history of his moves and the pegs with the move
	public void getHistory(){
		
	}
	
	//adds move to history of moves
	public void addToHistory(Pegs p){
		
	}
	
	//generates random secret code
	public void generateRandomCode(){
		
	}
}
