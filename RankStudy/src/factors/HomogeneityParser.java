package factors;

public class HomogeneityParser {
	
	public HomogeneityParser(){
		
	}
	
	public double calcHomogeneity(int common, int friendCount1, int friendCount2){
		double ratio1 = (double)common / friendCount1;
		double ratio2 = (double)common / friendCount2;
		System.out.println(Double.toString(ratio1 * ratio2));
		return ratio1 * ratio2;
	}
}
