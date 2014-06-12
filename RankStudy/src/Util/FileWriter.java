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
		NONE, POPULARITY,TIME_DECAY,FAMILIARITY,HOMOGENEITY, SIMILARITY
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
					weibo.getPublisher().getPageRankValue() + "," + weibo.getEdgeRankValue() + "," + weibo.getPublisher().getTwitterRank() + ",";
			
					writeContents[i] += weibo.caluclateFactors(NONE, true) + ",";
			
			i++;
  		}
		
		
		String[] contents = new String[realWeiboList.size()];
		
		i = 0;
		
		for (Weibo weibo: weiboList) {
			contents[i] = user.getId() + "," + weibo.getPublisher().getId() + "," + weibo.getId() + "," + 
					weibo.getCreatedAt() + ",";
			
					for (int j = 1; j != FACTORS.length; j++) {
						contents[i] += weibo.caluclateFactors(FACTORS[j], true) + ",";
					}
			
					for (int j = 1; j != FACTORS.length; j++) {
						contents[i] += weibo.caluclateFactors(FACTORS[j], false) + ",";
					}
					
			i++;
		}

        PrintWriter writer;
		//try {
		//	writer = new PrintWriter(folderLocation + "/" + user.getName() + "_procedure.csv", "UTF-8");
		//	
		//	writer.println("uid,author id,weibo id,time,normal popularity, normal time decay, normal familarity decay, normal homogeneity, normal similarity,popularity,time decay,familarity decay,homogeneity,similarity");
		//	
		//	for (int j = 0; j != contents.length; j++)
		//		writer.println(contents[j]);

		//	writer.close();
		//	
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		
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

        double[] normalPageValuesViaFactors = new double[6];
            
        for (int z = 0; z != FACTORS.length; z++) {
            Collections.sort(realWeiboList, new WeiboComparatorViaSHTFP(FACTORS[z], true));
            normalPageValuesViaFactors[z] = getRankValues(realWeiboList);

            for (int j = 0; j != realWeiboList.size(); j++) {
            	i = weiboList.indexOf(realWeiboList.get(j));
            	writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
            }
        }

        double[] pageValuesViaFactors = new double[6];
            
        for (int z = 0; z != FACTORS.length; z++) {
            Collections.sort(realWeiboList, new WeiboComparatorViaSHTFP(FACTORS[z], false));
            pageValuesViaFactors[z] = getRankValues(realWeiboList);

            for (int j = 0; j != realWeiboList.size(); j++) {
            	i = weiboList.indexOf(realWeiboList.get(j));
            	writeContents[i] += realWeiboList.get(j).getCurrentPositionValue() + ",";
            }
        }
        
        i = 0;
        
        for (Weibo weibo : weiboList) {
        	writeContents[i++] += weibo.isCommented() + "," + weibo.isForwarded();
        }

		try {
			writer = new PrintWriter(folderLocation + "/" + user.getName() + ".csv", "UTF-8");
			
			writer.println("UserId,SenderId,WeiboId,Time,Similarity,Homogeneity,TimeDecay,Familiarity,Popularity,PageRank Value,EdgeRank Value,Twitter Rank Value,SHTFP Value,Time Position Value,PageRank Position Value,EdgeRank Position Value,Twitter Rank Position Value,SHTFP Normal Position Value,P Normal Position Value, T Normal Position Value,F Normal Position Value,H Normal Position Value,S Normal Position Value,SHTFP Position Value,P Position Value, T Position Value,F Position Value,H Position Value,S Position Value,has Commented,has reposted");
			
			for (int j = 0; j != writeContents.length; j++) {
				writer.println(writeContents[j]);
			}
			
			writer.write(",,,,,,,,,,,,," + pageValuesViaTime + "," + pageValuesViaPageRank + "," + pageValuesViaEdgeRank + "," + pageValuesViaTwitterRank + ",");

			for (int j = 0; j != normalPageValuesViaFactors.length; j++)
				writer.write(normalPageValuesViaFactors[j] + ",");

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
		
		return (double)(rankValues / (double)operationCount);
	}
}
