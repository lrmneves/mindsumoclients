import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;


public class Main {
	
	
	public static void main(String[] args) throws ParseException {
		
		HashMap<String,Subscription> subscriptionMap = new HashMap<>();
		LinkedHashMap<Integer,Long> yearRevenueMap = new LinkedHashMap<>();
		//Initialize revenue array
		for(int i = 1966; i < 2015;i++){
			yearRevenueMap.put(i, (long) 0);
		}
		
		String file = "files/"
				+ "subscription_report.csv";
		BufferedReader br = null;
		String line;
		final String DELIMITER = ",";
		DateFormat format = new SimpleDateFormat("mm/dd/yy", Locale.ENGLISH);
		Calendar calendar = Calendar.getInstance();
		String id;
		
		try {
			
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String[] subscriptionArray = line.split(DELIMITER);
				
				Date date = null;
				long amount = Long.parseLong(subscriptionArray[2]);
				try {
					date = format.parse(subscriptionArray[3]);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			    calendar.setTime(date);
			    int year = calendar.get(Calendar.YEAR);
			    yearRevenueMap.put(year,yearRevenueMap.get(year)+amount);
			    id = subscriptionArray[1];
			    if(subscriptionMap.containsKey(id)){
					
					subscriptionMap.get(id).
					updateSubscription(date,
							amount );
				}else{
		
						subscriptionMap.put(id, new Subscription(
								id,
								date,
								amount));
				}
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		for(String sub : subscriptionMap.keySet()){
			System.out.println(subscriptionMap.get(sub));
		}
		//Cmpute Bonus question 1.
		long maxLoss = 0;
		long maxGrowth = 0;
		
		int maxLossYear = 0;
		int maxGrowthYear = 0;
		int count =0;
		int lastYear = 0;
		double percentageLoss = 0;
		double percentageGrowth= 0;
		
		for(Integer revYear : yearRevenueMap.keySet()){
			if(count >0){
				long diff = yearRevenueMap.get(revYear) - 
						yearRevenueMap.get(lastYear);
				if(diff > 0){
					if(maxGrowth < diff) {
						maxGrowth = diff;
						maxGrowthYear = revYear;
						percentageGrowth = 100 * diff / yearRevenueMap.get(lastYear);;
					}
				}else if (diff < 0){
					if(maxLoss < Math.abs(diff)) {
						maxLoss =  Math.abs(diff);
						maxLossYear = revYear;
						percentageLoss = 100 * Math.abs(diff)/yearRevenueMap.get(lastYear);
					}
				}
			}
			lastYear = revYear;
			count++;
		}
		
		System.out.println("Highest Revenue Growth was in " +
		maxGrowthYear+ " with a growth of "+percentageGrowth+"% and highest "
				+ "revenue loss was in " +maxLossYear +" with a loss of "
				+percentageLoss + "%");
		
		//Estimate Bonus Question 2
		double growthSum =0;
		int start = 2005;
		int target = 2015;
		for( int i = start; i < target ; i ++){
			growthSum += (double)(yearRevenueMap.get(i) - yearRevenueMap.get(i-1))/ yearRevenueMap.get(i-1);
		}
		double averageGrowth = (double) growthSum/(target-start);
		
		System.out.println("Annual Revenue for " +target + " will be " + 
				((int) (yearRevenueMap.get(target-1) + 
				yearRevenueMap.get(target-1)*averageGrowth)));

	}

}
