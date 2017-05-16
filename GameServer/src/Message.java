
public class Message {
	private String topic = "";
	private String message = "";
	
	public Message(String topic, String message){
		this.topic = topic;
		this.message = message;
	}
	
	public String getTopic(){
		return topic;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setTopic(String topic){
		this.topic = topic;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
}