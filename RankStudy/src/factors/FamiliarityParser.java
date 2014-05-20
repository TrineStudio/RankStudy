package factors;

import network.UserNetwork;
import model.User;

public class FamiliarityParser {

	public FamiliarityParser(){
		
	}
	
	public double calcFamiliarity(boolean bifriend, User user1, User user2){
		int interactionCount = new UserNetwork().getUserInteractionCount(user1.getId(), user2.getId());

		if(bifriend){
			return interactionCount / (double)user1.getBiFollowersCount();
		}
		else
			return interactionCount / (double)user1.getFriendsCount();
	}
}
