package factors;

public class FamiliarityParser {

	public FamiliarityParser(){
		
	}
	
	public double calcFamiliarity(boolean bifriend, int friendCount, int bifriendCount){
		if(bifriend){
			return (double) 1 / bifriendCount;
		}
		else
			return (double) 1 / friendCount;
	}
}
