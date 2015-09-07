import java.util.Date;


public class Subscription {
	String id;
	long totalAmount;
	Date startDate;
	Date endDate;
	SubscriptionType type;

	public Subscription(String id,  Date date, long amount){
		this.id = id;
		this.totalAmount = amount;
		this.startDate = date;
		this.endDate = date;
		type = SubscriptionType.ONE_OFF;
	}

	public void updateSubscription(Date date, long amount){
		this.totalAmount+= amount;

		if(type == SubscriptionType.ONE_OFF){
			updateType(date);
		}

		if(startDate.compareTo(date) > 0){
			startDate = date;
		}
		else if(endDate.compareTo(date) < 0){
			endDate = date;
		}
	}
	private void updateType(Date date){
		long duration = Math.abs(endDate.getTime() - date.getTime());

		int days = convertDuration(duration);
		
		if(days < 30){
			type = SubscriptionType.DAILY;
		}else if (days < 365){
			type =SubscriptionType.MONTHLY;
		}else{
			type =  SubscriptionType.YEARLY;
		}
	}

	
	private int convertDuration(long duration ){
		
		int days =  (int)  Math.ceil(duration / (1000*60*60*24));
		if(type == SubscriptionType.DAILY || 
				type == SubscriptionType.ONE_OFF) return days;
		else if (type == SubscriptionType.MONTHLY) return days/30;
		return days/365;
	}
	@Override
	public String toString(){
		return id + " had a " + type.toString() + " subscription for " + 
				String.valueOf(convertDuration(endDate.getTime() - startDate.getTime()))
				+ " " + type.getElapsedTime();

	}
}
