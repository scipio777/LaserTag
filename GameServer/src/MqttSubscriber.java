import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MqttSubscriber implements MqttCallback {

	ArrayList<Message> messageArray = new ArrayList<Message>();
	
	/** The broker url. */
	private static final String brokerUrl = "tcp://192.168.1.149:1883";
	private static final String clientId = "GameServer";

	
	/**
	 * Subscribe.
	 *
	 * @param topic
	 *            the topic
	 * 
	 */

	public void subscribes(String topic) {
			MemoryPersistence subscriberPersistence = new MemoryPersistence();

		try {
			MqttClient subscriberClient = new MqttClient(brokerUrl, clientId, subscriberPersistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(false);
			subscriberClient.connect(connOpts);
			subscriberClient.setCallback(this);
			subscriberClient.subscribe(topic);

		} catch (MqttException me) {

			System.out.println("Mqtt reason " + me.getReasonCode());
			System.out.println("Mqtt msg " + me.getMessage());
			System.out.println("Mqtt loc " + me.getLocalizedMessage());
			System.out.println("Mqtt cause " + me.getCause());
			System.out.println("Mqtt excep " + me);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
	 * Throwable)
	 */
	public void connectionLost(Throwable arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.
	 * paho.client.mqttv3.IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.
	 * String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	public void messageArrived(String topic, final MqttMessage mqttMessage) throws Exception {

		//System.out.println("");
		//System.out.println("");
		//System.out.println("Mqtt topic : " + topic);
		//System.out.println("Mqtt msg : " + mqttMessage.toString());
       
		messageArray.add(new Message(topic, mqttMessage.toString()));
	}
}
