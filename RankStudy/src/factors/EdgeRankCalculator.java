package factors;

import model.User;
import model.Weibo;

public class EdgeRankCalculator {

	//I'm wondering what is the "familiarity" in a Weibo, while not defining the reader
	public static double getEdgeRank(Weibo weibo){
		return weibo.getFamiliarity() * weibo.getPopularity() * weibo.getTimeDecay();
	}
}
