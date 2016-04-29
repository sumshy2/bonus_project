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
	String extraColors;
	ArrayList<Pegs> history;
	
	public Game(){
		numGuesses = 12;  //DEFAULT
		numPegs = 4; //DEFAULT
		numColors = 6; //DEFAULT
		secretCode = generateRandomCode();
		extraColors = new String();
	}
	public Game(int numPegs, int numGuesses, int numColors, String extraColors){
		this.numGuesses = numGuesses;
		this.numPegs = numPegs;
		this.extraColors = extraColors;
		this.numColors = extraColors.length() + 6;
	}
	
	
	//called by MastermindDriver class and will run as many times as the user wants it to run
	//WHAT IT DOES: starts the game
	public int[] runGame(String userInput){
		int[] pegs = new int[3]; 
		Pegs p = new Pegs(userInput);
		p.processInput(secretCode);
		pegs = new int[]{p.getBlackPegs(),p.getWhitePegs()}; //print p.getBlackPegs() and p.getWhitePegs() to user give integer array (black, white) 
		history.add(p); //will add user's move to history of his moves
		return pegs;
	}
	
	//if user wants to get history of his moves and the pegs with the move
	public String getHistory(){
		String print = new String();
		for(int i = 0; i < history.size(); i ++)
		{
			String summary = "Guess " + (i+1) + history.get(i).getGuess() + 
			". Black pegs: " + history.get(i).getBlackPegs() + " White pegs: " + history.get(i).getWhitePegs();
			print = print + summary + "\n";
		}
		print.trim();
		return print;
	}
	
	
	//generates random secret code of specified length
	public String generateRandomCode(){
		String secretCode = "";
		//maroon, cyan {"M","C"}
		String array = "BGOPRY"; //DEFAULT: blue, green, orange, purple, red, yellow
		//if(extraColors)
		String temp = new String();
		for(int i = 0; i < array.length(); i++){
			temp += array.charAt(i);
			if(i+1 == array.length()){
				if(extraColors.length() != 0){
					for(int j = 0; j < extraColors.length(); j++){
						temp += extraColors.charAt(j);
					}
				}
			}
		}
		array = temp;
		int min = 0; 
		int max = numColors - 1;
		Random rand = new Random();
		for(int i = 0; i < numPegs; i++){
			int r = rand.nextInt((max - min) + 1) + min;
			secretCode+=array.charAt(i);
		}
		return secretCode;
	}
}
