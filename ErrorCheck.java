package bonus_project;
/*
 * Dung Le
 * Sumedh Shah
 */

public class ErrorCheck {
	public ErrorCheck(){
		
	}
	public boolean isNumeric(String input){
		String regex = "\\d+";
		if (input.matches(regex))
			return true;
		return false;
	}
	
	public boolean checkGuess(String input){
		if(input == null)
		{
			return true;
		}
		if(!isNumeric(input))
			return false;
		else if(Integer.parseInt(input) == 0 || Integer.parseInt(input) > 20 || Integer.parseInt(input) < 5)
			return false;
		else
			return true;
	}
	
	public boolean checkPegs(String input){
		if(input == null)
		{
			return true;
		}
		if(!isNumeric(input))
			return false;
		else if(Integer.parseInt(input) == 0 || Integer.parseInt(input) > 8 || Integer.parseInt(input) < 4)
			return false;
		else
			return true;
	}
	
}
