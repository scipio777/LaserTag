public class Player {

	private String clientID = "";
	private String topic = "";
	private String name = "";
	private String freqKey = "";
	private int killScore = 0;
	private int death = 0;
	
	
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
	
	public void increaseDeath(){
		this.death = death+=death;
	}
	
	public int getKillScore(){
		return killScore;
	}
	
	public void increaseKill(){
		this.killScore = killScore+1;
	}
	
	public String getFreqKey(){
		return freqKey;
	}	
	
	public void setFreqKey(String freq){
		this.freqKey = freq;
	}	
}