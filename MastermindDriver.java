package bonus_project; 

/*MAIN DRIVER CLASS
 *Purpose: Handles user input (will ask user if they want to play).
 *If user wants to play, it will create a Game object WHICH REPRESENTS
 *ONE GAME. */

public class MastermindDriver {
    public static void main(String[] args)
    {   
    	//while user wants to keep playing 
	    	//Game g = new Game(true);
	    	//g.runGame();
    	//end while loop
    	
    	//IGNORE THIS CODE - USED FOR TESTING
/*    	String code = "BWRW";
    	String guess = "TMRW";
    	Pegs p = new Pegs(guess); //guess
    	p.processInput(code); //code
*/    
    	Game g = new Game();
    	System.out.println(g.generateRandomCode());
    }
}
