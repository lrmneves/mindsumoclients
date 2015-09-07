
public enum SubscriptionType {
	DAILY("daily"),
	MONTHLY("monthly"),
	YEARLY("yearly"),
	ONE_OFF("one-off");
	
	private final String value;
	
	private SubscriptionType(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
	
	public String getElapsedTime(){
		if(this == SubscriptionType.DAILY || this == SubscriptionType.ONE_OFF){
			return "days";
		}else if (this == SubscriptionType.MONTHLY){
			return "months";
		}
		return "years";
	}
}
