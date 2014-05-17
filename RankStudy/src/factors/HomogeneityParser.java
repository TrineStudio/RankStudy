package factors;

import java.util.List;

import model.User;
import network.UserNetwork;

public class HomogeneityParser {
	
	public HomogeneityParser(){
		
	}
	
	public double calcHomogeneity(int common, int friendCount1, int friendCount2){
		double ratio1 = (double)common / friendCount1;
		double ratio2 = (double)common / friendCount2;
		//System.out.println(Double.toString(ratio1 * ratio2));
		return ratio1 * ratio2;
		//no time to modify this.
	}
	
	public double calcHomogeneity(int readerId, int authorId) {
		double homogeneity = new UserNetwork().getUserHomogeneity(readerId, authorId);

		System.out.println("Homogeneity is: " + homogeneity);

		return homogeneity;
	}
	
	public double calcHomogeneity(User reader, User author) {
		int readerId = reader.getId();
		int authorId = author.getId();
		
		int readerFollowerCounts = reader.getFollowersCount();
		int authorFollowerCounts = author.getFollowersCount();
		
		int readerValue = 0;
		int authorValue = 0;
		
		List<User> readerFollowers = new UserNetwork().getUserFollowers(readerId);
		List<User> authorFollowers = new UserNetwork().getUserFollowers(authorId);

		readerValue += (authorFollowerCounts - authorFollowers.size()) * 2;
		authorValue += (readerFollowerCounts - readerFollowers.size()) * 4;
		
		for (int i = 0; i != authorFollowers.size(); i++) {
			User authorFollower = authorFollowers.get(i);
			
			boolean hasFound = false;

			for (int j = 0; j != readerFollowers.size(); j++) {
				User readerFollower = readerFollowers.get(j);
				
				if (readerFollower.getId() == authorFollower.getId()) {
					hasFound = true;
					break;
				}
			}
			
			if (hasFound)
				readerValue += 1;
			else
				readerValue += 2;
			
		}
		
		for (int i = 0; i != readerFollowers.size(); i++) {
			User readerFollower = readerFollowers.get(i);
			
			for (int j = 0; j != authorFollowers.size(); j++) {
				User authorFollower = authorFollowers.get(j);
				
				if (readerFollower.getId() == authorFollower.getId()) {
					authorValue++;
					readerFollowerCounts--;
					readerFollowers.remove(i);
					i--;
					break;
				}
			}
		}

		for (int i = 0; i != authorFollowers.size(); i++) {
			User authorFollower = authorFollowers.get(i);
			
			int id = authorFollower.getId();
			
			List<User> indirectFollowers = new UserNetwork().getUserFollowers(id);
			
			for (int z = 0; z != indirectFollowers.size(); z++) {
				User indirectFollower = indirectFollowers.get(z);
				
				for (int j = 0; j != readerFollowers.size(); j++) {
					User readerFollower = readerFollowers.get(j);
				
					if (readerFollower.getId() == indirectFollower.getId()) {
						authorValue += 2;
						readerFollowers.remove(j);
						break;
					}
				}
			}
		}
		
		authorValue += 4 * readerFollowers.size();
		
		
		return (double)(authorFollowerCounts + readerFollowerCounts) / (double)(readerValue + authorValue);
	}
}
