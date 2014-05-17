package factors;

public class FamiliarityParser {

	public FamiliarityParser(){
		
	}
	
	public double calcFamiliarity(boolean bifriend, int friendCount, int bifriendCount){
		if(bifriend){
			return (double) 1 / Math.log(bifriendCount + 1);
		}
		else
			return (double) 1 / Math.log(friendCount + 1);
	}
}
