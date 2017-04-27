  import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.ArrayList;

public class GameServerApp {

	public static void main(String[] args) throws MqttException {

		Scanner scan = new Scanner(System.in);

		// Instantiates a MQTT communication object for sending messages.
		MqttPublisher publisher = new MqttPublisher();

		// Instantiates a MQTT communication object for receiving messages.
		MqttSubscriber subscriber = new MqttSubscriber();

		// Instantiates an Engine object to control the flow of the application.
		Engine engine = new Engine();

		// Instantiates a Free-For-All object that manages the gameplay.
		Free4All f4A = new Free4All();
		
		//Instantiates a player object to represent players in the game.
		Player player = new Player(null, null, null);
		
		//Array of player objects and its methods and variables	
		ArrayList<Player> playerArray = new ArrayList<Player>();
				
		//Beginning of application
		
		while (true) {

			// Clears all entries from the messsageArray and the player array.
			engine.arrayReset(subscriber, playerArray);

			/* Notifies all players that the game is ready to add new players.
			 * All clients must subscribe to the topic "InterComm" by default.
			 * The topic "InterComm" broadcasts across all clients.
			 */
			publisher.publishes("InterComm", "Game ready.  Please join.");

			// Begins the player sign-up process
			engine.signUp(subscriber, publisher, playerArray, scan);
			
			// Sets the frequency for each player
			  engine.setFrequency(playerArray);
			
			// Clears all messages from the messsageArray.
			engine.arrayReset(subscriber);
			
			// Begins the game.
			f4A.startGame();
			
			//Unsubscribes all players.
			//engine.unsubscribePlayers();
			
			//Display stats
			//engine.stats();
			
		}
	}
}
