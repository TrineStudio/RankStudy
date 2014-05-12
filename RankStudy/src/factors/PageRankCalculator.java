package factors;

import java.util.List;
import model.User;

public class PageRankCalculator {
	public static double getPageRank(List<User> users) {
		double pageRankValue = 0;
		
		for (int i = 0; i != users.size(); i++) {
			pageRankValue += 1.0f / (users.get(i).getFriendsCount() + 1);
		}
		
		return pageRankValue;	
	}
}