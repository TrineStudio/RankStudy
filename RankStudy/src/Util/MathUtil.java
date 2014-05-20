package Util;

public class MathUtil {
	public static double getAverage(double[] numbers) {
		double total = 0;
		
		for (int i = 0; i != numbers.length; i++)
			total += numbers[i];
		
		return total / numbers.length;
	}
	
	public static double getS(double[] numbers, double average) {
		double total = 0;
		
		for (int i = 0; i != numbers.length; i++) {
			total += Math.pow(numbers[i] - average, 2);
		}
		
		return Math.sqrt(total / (numbers.length - 1));
	}
	
	
}
