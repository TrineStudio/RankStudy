package rank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Keyword;
import model.User;
import model.Weibo;
import network.UserNetwork;
import Comparator.WeiboComparatorViaSHTFP;
import Util.FileWriter;
import Util.MathUtil;
import factors.EdgeRankCalculator;
import factors.FamiliarityParser;
import factors.HomogeneityParser;
import factors.PageRankCalculator;
import factors.SimilarityParser;
import factors.TimeDecayParser;
import factors.TwitterRankCalculator;

public class MainCalc {
	private static HashMap<String, Double> userPageRanks = new HashMap<String, Double>();
	private static HashMap<String, Keyword> userKeywords = new HashMap<String, Keyword>();
	private static HashMap<String, Double> userTwitterRanks = new HashMap<String, Double>();
	

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		User rootUser = new UserNetwork().getRootUser();
		List<User> users = new UserNetwork().getUserFocus(rootUser.getId());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 3);
		Date date = calendar.getTime();
		
		for (int i = 36; i != users.size(); i++) {
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

		    			double tempValue;
		    			 
		    			if ((tempValue = new UserNetwork().getUserTwitterRank(user.getId())) == -1) {
		    				userTwitterRanks.put(user.getName(), new TwitterRankCalculator().getTwitterRank(user.getId()));
		    			}
		    			else
		    				userTwitterRanks.put(user.getName(),tempValue);
		    		}

                    user.setPageRankValue(userPageRanks.get(user.getName()));
                    user.setTwitterRank(userTwitterRanks.get(user.getName()));
		    		weiboList.get(j).setPublisher(user);
		    		
		    		weiboList.get(j).setHomogeneity(new HomogeneityParser().calcHomogeneity(users.get(i).getId(), user.getId()));
		    		
		    		//if (weiboList.get(j).isForwarded()) {
		    		//	Weibo weibo = new WeiboNetwork().getWeiboForwardInfo(users.get(i).getId(), weiboList.get(j).getId());
		    		//	
		    		//	if (weibo == null)
		    		//		weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    		//	else
		    		//		weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weibo.getRepostsCount(), weibo.getCommentsCount(), weibo.getAttitudesCount(), weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    		//}
		    		//else {
		    		//	weiboList.get(j).setPopularity(new PopularityParser().calcPopularity(weiboList.get(j).getRepostsCount(), weiboList.get(j).getCommentsCount(), weiboList.get(j).getAttitudesCount()));
		    		//}

		    		weiboList.get(j).setTimeDecay(new TimeDecayParser().calcTimeDecay(weiboList.get(j).getCreatedAt()));
		    		
		    		weiboList.get(j).setFamiliarity(new FamiliarityParser().calcFamiliarity(weiboList.get(j).isBiFollowing(), users.get(i), user));
		    		
		    		Keyword keyword = userKeywords.get(user.getName());
		    		
		    		int length = userKeyword.getLength() > keyword.getLength() ? keyword.getLength() : userKeyword.getLength();
		    		
		    		length = length * 3;
		    		
		    		weiboList.get(j).setSimilarity(new SimilarityParser(length).calcSimilarity(userKeyword.getKeyWords(), keyword.getKeyWords()));

		    		EdgeRankCalculator.getEdgeRank(weiboList.get(j));

		    		System.out.println(isBiFollowed);
		    		realWeiboList.add(weiboList.get(j));
		    	}
		    }

		    double[] similarityArray = new double[realWeiboList.size()];
		    double[] homogeneityArray = new double[realWeiboList.size()];
		    double[] timeDecayArray = new double[realWeiboList.size()];
		    double[] popularityArray = new double[realWeiboList.size()];
		    double[] familarityArray = new double[realWeiboList.size()];
		    
		    for (int j = 0; j != realWeiboList.size(); j++) {
		    	Weibo tmp = realWeiboList.get(j);

		    	similarityArray[j] = tmp.getSimilarity();
		    	homogeneityArray[j] = tmp.getHomogeneity();
		    	timeDecayArray[j] = tmp.getTimeDecay();
		    	popularityArray[j] = tmp.getPopularity();
		    	familarityArray[j] = tmp.getFamiliarity();
		    }
		    
		    double[][] indexArrays = new double[][] 
		    {
		    		similarityArray, timeDecayArray, popularityArray, homogeneityArray, familarityArray
		    };
		    
		    for (int j = 0; j != indexArrays.length; j++) {
		    	WeiboComparatorViaSHTFP.AVG[j] = MathUtil.getAverage(indexArrays[j]);
		    	WeiboComparatorViaSHTFP.S[j] = MathUtil.getS(indexArrays[j], WeiboComparatorViaSHTFP.AVG[j]);
		    }
		    
		    FileWriter.writeUserReport("/Users/trinezealot/maggie/result", users.get(i), realWeiboList);
		}
	}
}
