package Util;

import static Comparator.WeiboComparatorViaSHTFP.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.User;
import model.Weibo;
import Comparator.WeiboComparatorViaEdgeRank;
import Comparator.WeiboComparatorViaPageRank;
import Comparator.WeiboComparatorViaSHTFP;
import Comparator.WeiboComparatorViaTime;
import Comparator.WeiboComparatorViaTwitterRank;

public class FileWriter {
	private static int[] FACTORS = new int[]
	{
		NONE, POPULARITY, FAMILIARITY, TIME_DECAY, HOMOGENEITY, SIMILARITY
	};

	public static void writeUserReport(String folderLocation, User user, List<Weibo> realWeiboList) {
		String[] writeContents = new String[realWeiboList.size()];

		List<Weibo> weiboList = new ArrayList<Weibo>();
		
		for (int j = 0; j != realWeiboList.size(); j++)
			weiboList.add(null);

		Collections.copy(weiboList, realWeiboList); 
		
		double friendsFollowerRatio = (double)((double)user.getFollowersCount() / user.getFriendsCount());
		
		int i = 0;
		
		for (Weibo weibo : weiboList) {
			writeContents[i] = user.getId() + "," + weibo.getPublisher().getId() + "," + weibo.getId() + "," + 
					weibo.getCreatedAt() + "," + weibo.getSimilarity() + "," + weibo.getHomogeneity() + "," + 
	 				weibo.getTimeDecay() + "," + weibo.getFamiliarity() + "," + weibo.getPopularity() + "," +
					weibo.getSimilarity() * SWEIGHT + "," + weibo.getHomogeneity() * HWEIGHT + "," + 
	 				weibo.getTimeDecay() * TWEIGHT + "," + weibo.getFamiliarity() * FWEIGHT + "," + 
					weibo.getPopularity() * PWEIGHT + "," + weibo.getPublisher().getPageRankValue() + "," + 
	 				weibo.getEdgeRankValue() + "," + weibo.getPublisher().getTwitterRank() + ",";
			
			for (int j = 0; j != FACTORS.length; j++) {
				writeContents[i] += weibo.caluclateFactors(FACTORS[j]) + ",";
			}
			
			i++;
  		}
		
		Collections.sort(realWeiboList, new WeiboComparatorViaTime());

		double pageValuesViaTime = getRankValues(realWeiboList);
		
		for (int j = 0; j != realWeiboList.size(); j++) {
			i = weiboList.indexOf(realWeiboList.get(j));
			writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
		}
		
        Collections.sort(realWeiboList, new WeiboComparatorViaPageRank());
        
        double pageValuesViaPageRank = getRankValues(realWeiboList);

		for (int j = 0; j != realWeiboList.size(); j++) {
			i = weiboList.indexOf(realWeiboList.get(j));
			writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
		}
		
		Collections.sort(realWeiboList, new WeiboComparatorViaEdgeRank());
		
		double pageValuesViaEdgeRank = getRankValues(realWeiboList);

		for (int j = 0; j != realWeiboList.size(); j++) {
			i = weiboList.indexOf(realWeiboList.get(j));
			writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
		}
		
		Collections.sort(realWeiboList, new WeiboComparatorViaTwitterRank());
		
		double pageValuesViaTwitterRank = getRankValues(realWeiboList);
		
		for (int j = 0; j != realWeiboList.size(); j++) {
			i = weiboList.indexOf(realWeiboList.get(j));
			writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
		}

        double[] pageValuesViaFactors = new double[6];
            
        for (int z = 0; z != FACTORS.length; z++) {
            Collections.sort(realWeiboList, new WeiboComparatorViaSHTFP(FACTORS[z]));
            pageValuesViaFactors[z] = getRankValues(realWeiboList);

            for (int j = 0; j != realWeiboList.size(); j++) {
            	i = weiboList.indexOf(realWeiboList.get(j));
            	writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
            	
            	System.out.println("(i, j): " + i + ", " + j);
            }
        }
        
        i = 0;
        
        for (Weibo weibo : weiboList) {
        	writeContents[i++] += weibo.isCommented() + "," + weibo.isForwarded();
        }
        
        PrintWriter writer;
		try {
			writer = new PrintWriter(folderLocation + "/" + user.getName() + ".csv", "UTF-8");
			
			writer.println("UserId,SenderId,WeiboId,Time,Similarity,Homogeneity,TimeDecay,Familiarity,Popularity,Similarity * " + SWEIGHT + ",Homogeneity * " + HWEIGHT + ",TimeDecay * " + TWEIGHT + ",Familarity * " + FWEIGHT + ",Popularity * " + PWEIGHT + ",PageRank Value,EdgeRank Value,Twitter Rank Value,SHTFP Value,SHTF value,SHTP Value,SHFP Value,STFP Value,HTFP Value,Time Position Value,PageRank Position Value,EdgeRank Position Value,Twitter Rank Position Value,SHTFP Position Value,SHTF Position Value,SHTP Position Value,SHFP Position Value,STFP Position Value,HTFP Position Value,has Commented,has reposted");
			
			for (int j = 0; j != writeContents.length; j++) {
				writer.println(writeContents[j]);
			}
			
			writer.write(",,,,,,,,,,,,,,,,," + pageValuesViaTime + "," + pageValuesViaPageRank + "," + pageValuesViaEdgeRank + "," + pageValuesViaTwitterRank + ",");
			
			for (int j = 0; j != pageValuesViaFactors.length; j++)
				writer.write(pageValuesViaFactors[j] + ",");
			
			writer.println(user.getFollowersCount() + "," + user.getFriendsCount() + "," + friendsFollowerRatio);
			
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static double getRankValues(List<Weibo> weiboList) {
		double rankValues = 0;
		
		int i = 0;
		int totalCount = weiboList.size();
		
		int operationCount = 0;
		
		for (Weibo weibo: weiboList) {
			int times = 0;
			
			if (weibo.isCommented()) {
				times += 1;
			}
			
			if (weibo.isForwarded()) {
				times += 2; 
			}
			
			if (weibo.isForwarded() || weibo.isCommented())
				operationCount++;
			
			double rankValue = (double)((double)(totalCount - i) / totalCount);
			
			weiboList.get(i).setCurrentPositionValue(rankValue);

			rankValues += rankValue * times;
			
			System.out.println(rankValue);

			i++;
		}
		
		return (double)(rankValues / (double)(operationCount));
	}
}
