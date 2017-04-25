import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {

	private int qos = 1;
	private String broker = "tcp://192.168.1.149:1883";
	private String clientId = "GameServer2";
	
	MemoryPersistence publisherPersistence = new MemoryPersistence();
	MqttConnectOptions connOpts = new MqttConnectOptions();
	MqttClient publisherClient = new MqttClient(broker, clientId, publisherPersistence);

	public MqttPublisher() throws MqttException {

	}

	public void publishes(String topic, String content) {
		
		
		try {
			connOpts.setCleanSession(true);
			publisherClient.connect(connOpts);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			
			//where it sends message
			publisherClient.publish(topic, message);
			publisherClient.disconnect();

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();

		}
	}
}