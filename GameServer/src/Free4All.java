
public class Free4All {

	int killLimit;

	public void startGame(MqttSubscriber subscriber) {

		// accept messages

		// Use the same setup as the signUp method.

		// Depending on the case. Do something

		// Basic message premise. "I got hit by this frequency."

		int sentinel = 0;

		while (sentinel == 0) {

			subscriber.subscribes("InterComm/#");

			if (subscriber.messageArray.size() > 0) {

				/*
				 * Takes the message from the first entry in the messageList
				 * array and stores it in the String message.
				 */

				String messageContent = subscriber.messageArray.get(0).getMessage();
				String topic = subscriber.messageArray.get(0).getTopic();

				// Removes the message from 1st entry in the messageList array.
				subscriber.messageArray.remove(0);

				/*
				 * Parses the clientID from the topic. The parsedMessage[1]
				 * contains the ClientID (MAC address)
				 */

				String topicArray[] = topic.split("/");

				// Checks if the correct number of elements are present in the
				// message.

				int numOfParsedElementsExpected = 2;
				boolean hasCorrectNumberOfElements = false;

				String clientID = "";

				if ((topicArray.length) == numOfParsedElementsExpected) {
					clientID = topicArray[1];
					hasCorrectNumberOfElements = true;
				}

				/*
				 * Parses the commandID and the command details from the
				 * messageContent. The messageContentArray[0] contains the
				 * commandID. The rest of the array will provide the command
				 * details.
				 */

				if (hasCorrectNumberOfElements) {
					
					String messageContentArray[] = messageContent.split("/");

					String commandID = messageContentArray[0];

					switch (commandID) {

					case "HIT":

						// Use the message topic to determine who got shot
						// Use the frequency to determine the shooter.
						// Increase the shooter's kill score.
						// Increase the shot player's death score.
						// Publish to all players: "Player X was killed by
						// Player Y"

						// Did the shooter reach the killLimit?
						// If no, continue. If yes, break the while loop.
						
						break;

					}
				}

			}
		}
	}
}
