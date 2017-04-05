import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttComm {

	private String topic = "GameCom";
	private int qos = 2;
	private String broker = "tcp://192.168.1.147:1883";
	private String clientId = "GameServer";
	private String content = "";

	MemoryPersistence persistence = new MemoryPersistence();
	MqttConnectOptions connOpts = new MqttConnectOptions();
	MqttClient client = new MqttClient(broker, clientId, persistence);

	public MqttComm() throws MqttException {

	}

	public void send(String content) {

		setContent(content);
		try {
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			client.publish(topic, message);
			System.out.println("Message published");
			client.disconnect();
			System.out.println("Disconnected");

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();

		}
	}

	public String listen() {
		String something = "";
		return something;

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}