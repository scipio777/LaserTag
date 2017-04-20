
import java.util.Scanner;

public class Engine {

	public void arrayReset(MqttSubscriber subscriber, Player player) {
		subscriber.messageArray.clear();
		player.playerArray.clear();
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

	public void signUp(MqttSubscriber subscriber, MqttPublisher publisher, Player player, Scanner scan) {

		System.out.println("Waiting for players to sign-in...");

		int sentinel = 0;

		while (sentinel == 0) {

			subscriber.subscribes("InterComm");

			if (subscriber.messageArray.size() > 0) {

				/*
				 * Takes the message from the first entry in the messageList
				 * array and stores it in the String message.
				 */

				String message = subscriber.messageArray.get(0).getMessage();

				// Removes the message from first entry in the messageList
				// array.
				subscriber.messageArray.remove(0);

				/*
				 * Parses the clientID, topic and name from the message. The
				 * parsed elements will be placed in the parsedMessage String
				 * array. The parsedMessage[0] will contain the clientID,
				 * parsedMessage[1] will contain the topic, parsedMessage[2]
				 * will contain the name
				 */

				String parsedMessage[] = message.split("~");

				// Checks if the correct number of elements are present in the
				// message.

				int numOfParsedElementsExpected = 3;
				boolean hasCorrectNumberOfElements = false;

				String clientID = "";
				String topic = "";
				String name = "";

				if ((parsedMessage.length) == numOfParsedElementsExpected) {
					clientID = parsedMessage[0];
					topic = "InterComm/" + parsedMessage[1];
					name = parsedMessage[2];
					hasCorrectNumberOfElements = true;
				}

				// Checks if the message will create a duplicate player.

				boolean duplicate = false;

				for (int i = 0; i < player.playerArray.size(); i++) {
					if (clientID.contentEquals(player.playerArray.get(i).getClientID())) {
						duplicate = true;
					}
				}

				if (!duplicate && hasCorrectNumberOfElements) {

					// Creates a new player and adds them to the player array
					// list.
					player.playerArray.add(new Player(clientID, topic, name));

					// Notifies the player that they have been added.
					publisher.publishes(topic, "You have been added to the game.");

					System.out.println("These player(s) are signed-in:  ");

					for (int i = 0; i < player.playerArray.size(); i++) {

						System.out.println(
								player.playerArray.get(i).getName() + "   " + player.playerArray.get(i).getClientID());
					}

					System.out.println("Would you like to add another player? [0] Yes or [1] No");
					sentinel = scan.nextInt();
					if (sentinel == 0) {
						System.out.println("Waiting for players to sign-in...");
					}
				}
			}
		}
	}

	public void subscribePlayers(MqttSubscriber subscriber, Player player) {
		
		for (int i = 0; i < player.playerArray.size(); i++) {
			subscriber.subscribes(player.playerArray.get(i).getTopic());
		}
		System.out.println("All players have been subscribed.");
	}
}
