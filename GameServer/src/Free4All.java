import java.util.ArrayList;
import java.util.Scanner;

public class Free4All {

	private int killLimit;
	private int shotPlayer;
	private int shooter;
	String clientID;
	int sentinel;

	public void runGame(MqttSubscriber subscriber, MqttPublisher publisher, ArrayList<Player> playerArray,
			Player player, Scanner scan) {

		killLimit = 0;
		shotPlayer = -1;
		shooter = -1;
		clientID = "";
		sentinel = 0;
		
		
		setKillLimit(scan);
		System.out.println("Free for all game is now runnning.");

		
		while (sentinel == 0){
			
			subscriber.subscribes("interComm/#");
			
			if (subscriber.messageArray.size() > 0){
				//Get message from arrayList
				Message message = getMessage(subscriber);
		
				//Get Sender's ClientID from the message
				clientID = getSenderClientID(message, subscriber);
		
				//Get messageContent from the message;
				String [] messageContent = getMessageContent(message, subscriber);
		
				//Remove the message from the messageArray arrayList.
				removeMessage(subscriber);
		
				//Validate the sender's clientID.  Returns true if known. Returns false if not known.
				boolean isKnown = validateClientID(clientID, playerArray);
				
				//If known, then run and validate the command. 
				if (isKnown) {
					runCommand(messageContent, playerArray);
				}
			}
		}
	}

	public void setKillLimit(Scanner scan) {
		System.out.print("Enter the kill limit for the game:  ");
		this.killLimit = scan.nextInt();
	}

	public Message getMessage(MqttSubscriber subscriber) {
		Message messageContent = subscriber.messageArray.get(0);
		return messageContent;
	}

	public String getSenderClientID(Message message, MqttSubscriber subscriber) {
		String topic = subscriber.messageArray.get(0).getTopic();
		String[] parsedMessage = parseMessage(topic);
		// The 2nd string in the parsedMessage array should be the ClientID
		return parsedMessage[1];
	}

	public String[] getMessageContent(Message message, MqttSubscriber subscriber) {
		String messageContent = subscriber.messageArray.get(0).getMessage();
		String[] parsedMessage = parseMessage(messageContent);
		return parsedMessage;
	}

	public String[] parseMessage(String message) {
		String[] parsedMessage = message.split("/");
		return parsedMessage;
	}

	public void removeMessage(MqttSubscriber subscriber) {
		// Removed the 1st entry and moves the rest up.
		subscriber.messageArray.remove(0);
	}

	public boolean validateClientID(String clientID, ArrayList<Player> playerArray) {
		boolean isKnownClientID = false;
		for (int i = 0; i < playerArray.size(); i++) {
			if (clientID.equals(playerArray.get(i).getClientID())) {
				isKnownClientID = true;
			}
		}
		return isKnownClientID;
	}

	public boolean validateFreqKey(String freqKey, ArrayList<Player> playerArray) {
		boolean isKnownFreqKey = false;
		for (int i = 0; i < playerArray.size(); i++) {
			if (freqKey.equals(playerArray.get(i).getFreqKey())) {
				isKnownFreqKey = true;
			}
		}
		return isKnownFreqKey;
	}

	public boolean identifyWinnerFound(ArrayList<Player> playerArray) {
		boolean hasWinner = false;
		if (playerArray.get(shooter).getKillScore() >= killLimit) {
			hasWinner = true;
		}
		return hasWinner;
	}

	public void runCommand(String[] messageContent, ArrayList<Player> playerArray) {

		// Get the commandID. Its should be the first element in the array.
		String commandID = messageContent[0];

		// Depending on the commandID, certain actions will take place.
		switch (commandID) {
		case "hit":
			// Determine if the shooter's frequency key is known.
			boolean isKnownFreqKey = validateFreqKey(messageContent[1], playerArray);
				//System.out.println(isKnownFreqKey);
			
			if (isKnownFreqKey) {
				String freqKey = messageContent[1];

				// Determine the shooter and shotPlayer
				for (int i = 0; i < playerArray.size(); i++) {
					if (clientID.contentEquals(playerArray.get(i).getClientID())) {
						shotPlayer = i;
					}
					if (freqKey.contentEquals(playerArray.get(i).getFreqKey())) {
						shooter = i;
					}
				}
				// If a suicide, death increases for the player, but no
				// kills are awarded.
				if (shotPlayer == shooter) {
					playerArray.get(shotPlayer).increaseDeath();
					// publisher.publishes("interComm/" + clientID(),
					// "increaseDeath");
					System.out.println(
							playerArray.get(shooter).getName() + " killed " + playerArray.get(shotPlayer).getName());
				} else {
					// Increase kill score of the shooter
					playerArray.get(shooter).increaseKill();
					// Increase death count of the shotPlayer
					playerArray.get(shotPlayer).increaseDeath();
					
					System.out.println(playerArray.get(shooter).getName() + " killed " + playerArray.get(shotPlayer).getName());
					// publisher.publishes("interComm/" +
					// clientID,"increaseDeath");
					// publisher.publishes("interComm/" +
					// playerArray.get(shooter).getClientID(),"increaseKill");
					//Determine if kill limit has been reached.
					
					boolean hasWinner = identifyWinnerFound(playerArray);
			
					if(hasWinner){
						System.out.println(playerArray.get(shooter).getName() + " is the winner.");
						sentinel = 1;
					}
				}
			}
		}
	}
}