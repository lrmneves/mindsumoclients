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
		
		for(int i = 1966; i < 2015;i++){
			yearRevenueMap.put(i, (long) 0);
		}
		
		String file = "/Users/lrmneves/workspace/MindSumo/clientData/"
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

	}

}
