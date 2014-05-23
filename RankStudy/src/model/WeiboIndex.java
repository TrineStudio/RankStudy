package model;

public class WeiboIndex {
	private double similarity;
	private double homogeneity;
	private double familarity;
	private double timeDecay;
	private double popularity;
	private double edgeRank;

	public WeiboIndex(double similarity, double homogeneity, double familarity,
			double timeDecay, double popularity, double edgeRank) {
		super();
		this.similarity = similarity;
		this.homogeneity = homogeneity;
		this.familarity = familarity;
		this.timeDecay = timeDecay;
		this.popularity = popularity;
		this.edgeRank = edgeRank;
	}

	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public double getHomogeneity() {
		return homogeneity;
	}
	public void setHomogeneity(double homogeneity) {
		this.homogeneity = homogeneity;
	}
	public double getFamilarity() {
		return familarity;
	}
	public void setFamilarity(double familarity) {
		this.familarity = familarity;
	}
	public double getTimeDecay() {
		return timeDecay;
	}
	public void setTimeDecay(double timeDecay) {
		this.timeDecay = timeDecay;
	}
	public double getPopularity() {
		return popularity;
	}
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public double getEdgeRank() {
		return edgeRank;
	}

	public void setEdgeRank(double edgeRank) {
		this.edgeRank = edgeRank;
	}
	
}
