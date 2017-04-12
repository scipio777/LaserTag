import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttException;

public class GameServerApp {

	public static void main(String[] args) throws MqttException {

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		// Instatiates a MQTT communication object for sending messages.
		MqttSender sender = new MqttSender();

		// Instatiates a MQTT communication object for receiving messages.
		// MqttListener listner = new MqttListener();

		while (true) {

			// Requests a communication option from user.
			System.out.println("Select communication option:\n[1] Send Message [2] Receive Message");
			int communicationType = scan.nextInt();

			// Skip the newLine
			scan.nextLine();

			if (communicationType == 1) {
				// Sends a message via the MQTT protocol
				System.out.print("Please enter message: ");
				String message = scan.nextLine();
				sender.send(message);
			}
			// if (communicationType == 2) {
			// Listens for a message via the MQTT protocol
			// listener.listens();
			// }
			
			//Pushed to github4

		}

	}
}
