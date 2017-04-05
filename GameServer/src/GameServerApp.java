import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttException;


public class GameServerApp {

	public static void main(String[] args) throws MqttException  {

		
		while(true) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// Instatiates a MQTT communication object for sending and receiving messages.
		MqttComm mqtt = new MqttComm();

		// Requests a message from user.
		System.out.print("Please enter message:  ");
		String message = scan.nextLine();
		
		// Sends a message via the MQTT protocol
		mqtt.send(message);
		}

	}
}
