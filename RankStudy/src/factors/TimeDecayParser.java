package factors;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDecayParser {
	
	SimpleDateFormat sdf;
	
	public TimeDecayParser(){
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}
	
	public double calcTimeDecay(String timeString){
		double timeDecay = 0.0d;
		
		try {
			@SuppressWarnings("deprecation")
			Date date = new Date(timeString);
			Date dateNow = new Date();
			
			long secDecay = (dateNow.getTime()/ 1000) - (date.getTime()/ 1000);
			if(secDecay == 0){
				secDecay = 1;
			}
			System.out.println(secDecay);
			timeDecay = (double) 1.0f / secDecay / 60.0f;
			
			System.out.println(Double.toString(timeDecay));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeDecay; 
	}
	
	
	
	
}
