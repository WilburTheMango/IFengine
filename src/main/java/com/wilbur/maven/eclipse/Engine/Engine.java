package com.wilbur.maven.eclipse.Engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilbur.maven.eclipse.Engine.Character;

import java.util.*;
//import java.io.IOException;
//import com.google.common.collect.ArrayListMultimap;
//import opennlp.tools.chunker.ChunkerME;
//import opennlp.tools.chunker.ChunkerModel;
//import java.util.LinkedHashMap;


//TODO change all methods to static, create zip reading functionality

public class Engine {
	
	private static String gameName = null;
	private String gamePath;
	private static String version = "0.01";
	static String unzippedPath;
	// game variables
	public static Room currentRoom;
	public static ArrayList<Item> inventory = new ArrayList<Item>();
	static JsonNode manifest = null;
	static String appDir = System.getProperty("user.home")+"/.IFengine/";
	private String lastCommand = "";
	
	public Engine(String game) {
		gamePath = game;
		try {
			loadGame(game);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void tell(String in) {
		for (int i = 0; i < in.length(); i++) {
			System.out.print(in.charAt(i));
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int random = rand.nextInt(max + 1);
		return random;	
	}
	

	
	public Room loadRoom(String room) throws IOException {
		// read to string
		JsonNode roomNode = StoryParser.getJsonNodeFromFile(unzippedPath, roomPath(room));
		ArrayList<Item> inItems = new ArrayList<Item>();
		for (int i = 0; i < roomNode.get("items").size(); i++) { 
			//get items
			//System.out.println(itemLoc);
			inItems.add(loadItem(roomNode.get("items").get(i).asText()));
		}
		//System.out.println(inItems);
		//TODO ADD characters ArrayList<Character>
		ArrayList<Character> characters = new ArrayList<Character>();
		//TODO ADD EXITS MAP
		LinkedHashMap<String, String> exits = new LinkedHashMap<String, String>();
		Room ret = new Room(roomNode.get("name").asText(), roomNode.get("description").asText(), inItems, characters, exits);
		return ret;
	}
	
	public Item loadItem(String itemName) throws IOException {
		Item ret = null;
		JsonNode itemNode = StoryParser.getJsonNodeFromFile(unzippedPath, itemPath(itemName));
		ArrayList<Item> containing = new ArrayList<Item>();
		
		if ((itemNode.get("isContainer").asBoolean())) {	
//			System.out.println("container is true!");
			for (int i = 0; i < itemNode.get("containing").size(); i++) {
				String containingPath = itemNode.get("containing").get(i).asText();
//				System.out.println(containingPath);
				containing.add(loadItem(containingPath));
			}
		}	
		// recursive create array of contained obj in Item class, with internal item objects calling this method too... :( sadge recursion
			
		ret = new Item(itemNode.get("name").asText(), itemNode.get("description").asText(),itemNode.get("examineText").asText(), itemNode.get("isContainer").asBoolean(), itemNode.get("transparent").asBoolean(), itemNode.get("isOpen").asBoolean(), itemNode.get("isLocked").asBoolean(), itemNode.get("isStatic").asBoolean(), containing);
		return ret;
	}
	
	
	public String roomToString() {
		// TODO .json get a room elements and use them for the "look" command
		
		String roomText = "A large open void of white";
		return roomText;
	}
	
	public String getVersion() {
		return version;
	}
	public String toString() {
		return "IFengine version: " + version + ". Playing: " + gamePath;
	}


	public void parseCommand(String in) {
		// TODO Auto-generated method stub
		TokenWord[] tokenizedSentence = SentenceProcessor.sentenceParse(in);
			try {
				commandGrab(tokenizedSentence);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// TODO check if leading word is a verb. if it isnt, are its synonyms verbs? if no, dont proceed. 
		// check leading word synonyms run down the block to catch known commands. then pass in known commands' arguments and manipulate them.
	}


	private void commandGrab(TokenWord[] tokenizedSentence) throws InterruptedException {
		// TODO create all commands
		
			if (tokenizedSentence[0].getWord().equals("look")) {
				System.out.println("look invoked");
				look();
			} else if (tokenizedSentence[0].getWord().equals("take")) {
				System.out.println("take invoked");
				take(tokenizedSentence[1].getWord().toLowerCase());
			} else if (tokenizedSentence[0].getWord().equals("examine")) {
				System.out.println("examine invoked");
			} else if (tokenizedSentence[0].getWord().equals("say")) {
				System.out.println("say invoked");
			} else if (tokenizedSentence[0].getWord().equals("inventory")) {
				System.out.println(inventory);
				gameLoop();
			} else if (tokenizedSentence[0].getWord().equals("open")) {
				open(tokenizedSentence[1].getWord().toLowerCase());
			}
		}


	private void open(String item) {
		// TODO Auto-generated method stub
		if (currentRoom.getItem(item).isContainer() == true && currentRoom.getItem(item).isLocked() == false && currentRoom.getItem(item).isOpen() == false) {
			currentRoom.open(item);
			tell("Opened " + item + ".");
			gameLoop();
		} else if (currentRoom.getItem(item).isContainer() == true && currentRoom.getItem(item).isLocked() == true && currentRoom.getItem(item).isOpen() == false) {
			tell("It is locked.");
			gameLoop();
		} else if (currentRoom.getItem(item).isContainer() && currentRoom.getItem(item).isOpen() == true) {
			tell("You want to open an already open " + item + "?");
			gameLoop();
		} else if (currentRoom.getItem(item).isContainer() == false) {
			tell("You can't open this " + item + ".");
			gameLoop();
		}
	}


	private void take(String item) {
		// TODO Auto-generated method stub
		if (currentRoom.getItem(item).isStatic() == false) {
			inventory.add(currentRoom.getItem(item));
			currentRoom.removeItem(item);
			tell ("Taken " + item + ".");
		} else {
			tell("That is sturdily affixed in place!");
		}
		gameLoop();
	}


	private void look() throws InterruptedException {
		// TODO Auto-generated method stub
		tell(currentRoom.toStringFancy());
		gameLoop();
	}


	public String roomPath(String string) {
		return "/Room-" + string + ".json";
	}
	public String itemPath(String string) {
		return "/Item-" + string + ".json";
	}
	public String characterPath(String string) {
		return "/Character-" + string + ".json";
	}
	public void loadGame(String gameZip) throws IOException {
		//create some more instance variables for the Engine.
		unzippedPath = StoryParser.extractZip(gameZip);
		manifest = StoryParser.getJsonNodeFromFile(unzippedPath, "Manifest.json");
		// creates a JsonNode with the json of Manifest from a zip.
		currentRoom = loadRoom(manifest.get("Start").asText());
		gameLoop();
	}


	private void gameLoop() {
		//TODO currentRoom.toStringFancy() prompt user, parse command. do events, loop again.
		Scanner scan = new Scanner(System.in);
		System.out.print("> ");
		String inText = scan.nextLine();
		parseCommand(inText);
	}
}
	



