package bonus_project;

/*Purpose: Takes in user input and secret code, 
 *checks for matches, and returns appropriate pegs*/

public class Pegs {
	private String userGuess; //user guess
	private int blackNum = 0; //number of black pegs
	private int whiteNum = 0; //number of white pegs
	
	public Pegs(String userGuess){
		this.userGuess = userGuess;
	}
	
	//getter methods
	public int getBlackPegs(){
		return blackNum;
	}
	
	public int getWhitePegs(){
		return whiteNum;
	}
	
	public String getGuess(){
		return userGuess;
	}
	
	//called by Game class and will run 12 times for 12 tries AT MOST (or until user gets it right)
	//WHAT IT DOES: takes in secretCode and compares it with userGuess and changes the blackNum and whiteNum variables
	public void processInput(String secretCode){
		String tempUserGuess = userGuess;
		
		//CHECK FOR BLACK PEGS
		for(int i = 0; i <secretCode.length(); i++){
			if(tempUserGuess.charAt(i) == secretCode.charAt(i)){
				secretCode = updateWord(secretCode, i);
				tempUserGuess = updateWord(tempUserGuess, i);
				blackNum++;
			}
		}
		
		//System.out.println("CODE AFTER BLACK PEG ITERATION: " + secretCode);
		//System.out.println("Number of black pegs: " + blackNum);
		
		//CHECK FOR WHITE PEGS
		for(int i = 0; i <tempUserGuess.length(); i++){
			for(int j = 0; j <secretCode.length(); j++){
				if(tempUserGuess.charAt(i) != '-'){
					if(tempUserGuess.charAt(i) == secretCode.charAt(j)){
						secretCode = updateWord(secretCode, j);
						tempUserGuess = updateWord(tempUserGuess, i);
						whiteNum++;
						break;
					}
				}
			}
		}
		
		//System.out.println(secretCode);System.out.println("CODE AFTER WHITE PEG ITERATION: " + secretCode);
		//System.out.println("Number of white pegs: " + whiteNum);
	}
	
	//processInput calls this
	public String updateWord(String code, int index){
		StringBuilder newCode = new StringBuilder(code);
		newCode.setCharAt(index, '-');
		return newCode.toString();
	}
	
	

}
