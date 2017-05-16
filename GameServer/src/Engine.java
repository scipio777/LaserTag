import java.util.ArrayList;
import java.util.Scanner;

public class Engine {

	public void arrayReset(MqttSubscriber subscriber, ArrayList<Player> playerArray) {
		subscriber.messageArray.clear();
		playerArray.clear();
	}

	public void arrayReset(MqttSubscriber subscriber) {
		subscriber.messageArray.clear();
	}

	/*
	 * Takes messages from the messageList array, checks to make sure the
	 * message is in the correct format, checks if the message will create a
	 * duplicate player and then uses the information to add players to the
	 * player array
	 */

	public void signUp(MqttSubscriber subscriber, MqttPublisher publisher, ArrayList<Player> playerArray,
			Scanner scan) {

		System.out.println("Sign-up process has started.  \nRequesting players to register...");
		
		int sentinel = 0;
		boolean isNotSubscribed = true;
		
		while (sentinel == 0) {
			
			if (isNotSubscribed){
				isNotSubscribed = false;
			}
			
			subscriber.subscribes("interComm/#");

			if (subscriber.messageArray.size() > 0) {

				/*
				 * Takes the message from the first entry in the messageList
				 * array and stores it in the String message.
				 */

				String message = subscriber.messageArray.get(0).getMessage();
				String topic = subscriber.messageArray.get(0).getTopic();

				
				// Removes the message from 1st entry in the messageList array.
				subscriber.messageArray.remove(0);

				/*
				 * Parses the clientID from the topic. The parsedMessage[1] contains the ClientID (MAC address)
				 */

				String parsedMessage[] = topic.split("/");

				// Checks if the correct number of elements are present in the
				// message.

				int numOfParsedElementsExpected = 2;
				boolean hasCorrectNumberOfElements = false;

				String clientID = "";

				if ((parsedMessage.length) == numOfParsedElementsExpected) {
					clientID = parsedMessage[1];
					hasCorrectNumberOfElements = true;
				}

				// Checks if the message will create a duplicate player.

				boolean duplicate = false;

				for (int i = 0; i < playerArray.size(); i++) {
					if (clientID.contentEquals(playerArray.get(i).getClientID())) {
						duplicate = true;
					}
				}

				if (!duplicate && hasCorrectNumberOfElements) {

					// Creates a new player and adds them to the player array
					// list.
					playerArray.add(new Player(clientID, topic, message));

					// Notifies the player that they have been added.
					publisher.publishes(topic, "You have been added to the game.");

					System.out.println("These player(s) are signed-in:  ");

					for (int i = 0; i < playerArray.size(); i++) {

						System.out.println(playerArray.get(i).getName() + "  ~  " + playerArray.get(i).getTopic());
					}

					System.out.println("Would you like to add another player? [0] Yes or [1] No");
					sentinel = scan.nextInt();
				}
			}
		}
	}

	public void subscribePlayers(MqttSubscriber subscriber, ArrayList<Player> playerArray) {

		for (int i = 0; i < playerArray.size(); i++) {
			subscriber.subscribes(playerArray.get(i).getTopic());
		}
		System.out.println("All players have been subscribed.");
	}

	public void setFrequency(ArrayList<Player> playerArray) {

		for (int i = 0; i < playerArray.size(); i++) {
			playerArray.get(i).setFreqKey("shooter_" + i);
		}
		System.out.println("All frequencies are set.");
	}
}