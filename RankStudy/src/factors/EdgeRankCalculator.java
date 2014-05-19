package factors;

import java.util.List;

import model.User;
import model.Weibo;
import network.WeiboNetwork;

public class EdgeRankCalculator {
	
	public static int k = 1;

	public static double getEdgeRank(Weibo weibo){
		List<User> users = new WeiboNetwork().getWeiboInteractionUsers(weibo.getId());
		
		double value = 0;
		
		for (int i = 0; i != users.size(); i++) {
			User user = users.get(i);
			value += (double)(user.getWeiboCount() * k) / (double)user.getFriendsCount() * (double)user.getInteractionType() * new TimeDecayParser().calcTimeDecay(user.getInteractionTime());
		}
		
		weibo.setEdgeRankValue(value);
		return value;
	}
}
