
public class Player {

	private String clientID = "";
	private String topic = "";
	private String name = "";
	private String frequency = "";
	//private int health;
	//private int lives;
	

	public Player(String clientID, String topic, String name) {
		this.clientID = clientID;
		this.topic = topic;
		this.name = name;
	}
	
	public String getClientID() {
		return clientID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTopic(){
		return topic;
	}
		 
	public void setFrequency(int frequency){
		this.frequency = Integer.toString(frequency);
	}	
}
