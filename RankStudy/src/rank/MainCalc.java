package rank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Util.FileWriter;
import model.Keyword;
import model.User;
import model.Weibo;
import network.UserNetwork;
import network.WeiboNetwork;
import factors.FamiliarityParser;
import factors.HomogeneityParser;
import factors.PageRankCalculator;
import factors.PopularityParser;
import factors.SimilarityParser;
import factors.TimeDecayParser;

public class MainCalc {
	private static HashMap<String, Double> userPageRanks = new HashMap<String, Double>();
	private static HashMap<String, Keyword> userKeywords = new HashMap<String, Keyword>();
	

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		User rootUser = new UserNetwork().getRootUser();
		
		List<User> users = new UserNetwork().getUserFocus(rootUser.getId());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 3);
		Date date = calendar.getTime();
		
		for (int i = 0; i != users.size(); i++) {
			List<Weibo> weiboList = new UserNetwork().getUserAvailableWeibo(users.get(i).getId());
			
			Keyword userKeyword = new UserNetwork().getUserKeyword(users.get(i).getId());
			
			if (userKeyword == null) {
				continue;
			}
			
			userKeywords.put(users.get(i).getName(), userKeyword);
			
			List<Weibo> realWeiboList = new ArrayList<Weibo>();
			
		    for (int j = 0; j != weiboList.size(); j++) {
		    	Date tempDate = new Date(weiboList.get(j).getCreatedAt());
		    	
		    	if (tempDate.getTime() > date.getTime()) {

		    		User user = new UserNetwork().getUserInfo(weiboList.get(j).getUid());
		    		
		    		if (user == null)
		    			continue;

		    		boolean isBiFollowed = new UserNetwork().isBiFollowed(users.get(i).getUid(), user.getUid());
		    		
		    		weiboList.get(j).setBiFollowing(isBiFollowed);
		    		
		    		if (!userPageRanks.containsKey(user.getName())) {
		    			List<User> followerList = new UserNetwork().getUserFollowers(user.getId()); 
		    			double pageRankValue = PageRankCalculator.getPageRank(followerList);
		    			Keyword keyword = new UserNetwork().getUserKeyword(user.getId());
		    			
		    			userPageRanks.put(user.getName(), pageRankValue);
		    			userKeywords.put(user.getName(), keyword);
		    		}

                    user.setPageRankValue(userPageRanks.get(user.getName()));
		    		weiboList.get(j).setPublisher(user);
		    		
		    		weiboList.get(j).setHomogeneity(new HomogeneityParser().calcHomogeneity(1, users.get(i).getFriendsCount(), user.getFriendsCount()));
		    		
		    		if (weiboList.get(j).isForwarded()) {
		    			Weibo weibo = new WeiboNetwork().getWeiboForwardInfo(users.get(i).getId(), weiboList.get(j).getId());
		    			
		    			if (weibo == null)
		    				weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    			else
		    				weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weibo.getRepostsCount(), weibo.getCommentsCount(), weibo.getAttitudesCount(), weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    		}
		    		else {
		    			weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    		}
		    		
		    		weiboList.get(j).setFamiliarity(new FamiliarityParser().calcFamiliarity(weiboList.get(j).isBiFollowing(), users.get(i).getFollowersCount(), user.getFollowersCount()));
		    		weiboList.get(j).setTimeDecay(new TimeDecayParser().calcTimeDecay(weiboList.get(j).getCreatedAt()));
		    		
		    		Keyword keyword = userKeywords.get(user.getName());
		    		
		    		int length = userKeyword.getLength() > keyword.getLength() ? keyword.getLength() : userKeyword.getLength();
		    		
		    		length = length * 3;
		    		
		    		weiboList.get(j).setSimilarity(new SimilarityParser(length).calcSimilarity(userKeyword.getKeyWords(), keyword.getKeyWords()));

		    		System.out.println(isBiFollowed);
		    		realWeiboList.add(weiboList.get(j));
		    	}
		    }
		    
		    FileWriter.writeUserReport("/Users/trinezealot/maggie/result", users.get(i), realWeiboList);
		}
	}
}
