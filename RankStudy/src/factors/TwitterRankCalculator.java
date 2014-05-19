package factors;

import java.util.List;

import model.Keyword;
import model.TwitterRankInfo;
import network.UserNetwork;

public class TwitterRankCalculator {
	public double getTwitterRank(int id) {
		double result = 0;
		
		Keyword mainKeyword = new UserNetwork().getUserKeyword(id);
		
		List<TwitterRankInfo> twitterRankInfoList = new UserNetwork().getUserTwitterRankInfoList(id);
		
		for (int i = 0; i != twitterRankInfoList.size(); i++) {
			TwitterRankInfo rankInfo = twitterRankInfoList.get(i);
			int length = mainKeyword.getLength() > rankInfo.getKeyword().getLength() ? rankInfo.getKeyword().getLength() : mainKeyword.getLength();

			result += (double)rankInfo.getWeiboCount() / (double)rankInfo.getWeiboCounts() * new SimilarityParser(length).calcSimilarity(mainKeyword.getKeyWords(), rankInfo.getKeyword().getKeyWords());
		}
		
		new UserNetwork().setUserTwitterRank(id, result);
		
		return result;
	}
}
