package com.wilbur.maven.eclipse.Engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilbur.maven.eclipse.Engine.Character;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import java.util.*;
//import java.io.IOException;
//import com.google.common.collect.ArrayListMultimap;
//import opennlp.tools.chunker.ChunkerME;
//import opennlp.tools.chunker.ChunkerModel;
//import java.util.LinkedHashMap;


//TODO change all methods to static, create zip reading functionality

public class Engine {
	
	private String gamePath;
	private static String version = "0.01";
	static File pos = new File("src/main/resources/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
	
	// game variables
	public static Room currentRoom;
	public static ArrayList<Item> inventory = new ArrayList<Item>();
	
	
	public Engine(String game) {
		gamePath = game; 
	}

	
	public static void tell(String in) throws InterruptedException {
		for (int i = 0; i < in.length(); i++) {
			System.out.print(in.charAt(i));
			Thread.sleep(25);
		}
		System.out.println();
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int random = rand.nextInt(max + 1);
		return random;	
	}
	
	public static String[] parseTokens(String str) {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
	    String[] tokens = tokenizer.tokenize(str);
	    return tokens;
	}
	
	public static String[] parseTags(String[] tokens) {
		 
	    POSModel model = new POSModelLoader().load(pos);
	    POSTaggerME tagger = new POSTaggerME(model);  
	    String[] tags = tagger.tag(tokens);    
		return tags;
	}

	public static TokenWord[] sentenceParse(String in) {
		String[] tokens = parseTokens(in);
		String[] tags = parseTags(tokens);
		TokenWord[] ret1 = new TokenWord[in.length()];
		for (int i = 0; i < tokens.length; i++) {
//			if (tags[i].equals("PUNCT")) {
//				break;
//			}
			TokenWord word = new TokenWord(tokens[i], tags[i]);
			ret1[i] = word;
		}
		return ret1;
		
	}
	
	public static Room loadRoom(Path roomJson) throws IOException {
		// read to string
		ObjectMapper objectMapper = new ObjectMapper();
		String roomStr = Files.readString(roomJson);
		JsonNode roomNode = objectMapper.readTree(roomStr);
		ArrayList<Item> inItems = new ArrayList<Item>();
		for (int i = 0; i < roomNode.get("items").size(); i++) { 
			//get items
			//System.out.println(itemLoc);
			Path itemPath = Paths.get(roomNode.get("items").get(i).asText());
			inItems.add(loadItem(itemPath));
		}
		System.out.println(inItems);
		//TODO ADD characters ArrayList<Character>
		ArrayList<Character> characters = new ArrayList<Character>();
		//TODO ADD EXITS MAP
		LinkedHashMap<String, String> exits = new LinkedHashMap<String, String>();
		Room ret = new Room(roomNode.get("name").asText(), roomNode.get("description").asText(), inItems, characters, exits);
		currentRoom = ret;
		return ret;
	}
	
	public static Item loadItem(Path itemJson) throws IOException {
		Item ret = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String itemStr = Files.readString(itemJson);
		JsonNode itemNode = objectMapper.readTree(itemStr);
		ArrayList<Item> containing = new ArrayList<Item>();
		
		if ((itemNode.get("isContainer").asBoolean())) {	
//			System.out.println("container is true!");
			for (int i = 0; i < itemNode.get("containing").size(); i++) {
				String containingPath = itemNode.get("containing").get(i).asText();
//				System.out.println(containingPath);
				containing.add(loadItem(Paths.get(containingPath)));
			}
		}	
		// recursive create array of contained obj in Item class, with internal item objects calling this method too... :( sadge recursion
			
		ret = new Item(itemNode.get("name").asText(), itemNode.get("description").asText(),itemNode.get("examineText").asText(), itemNode.get("isContainer").asBoolean(), itemNode.get("transparent").asBoolean(), itemNode.get("isOpen").asBoolean(), itemNode.get("isLocked").asBoolean(), itemNode.get("isStatic").asBoolean(), containing);
		return ret;
	}
	
	
	public static String roomToString() {
		// TODO .json get a room elements and use them for the "look" command
		
		String roomText = "A large open void of white";
		return roomText;
	}
	
	public static String getVersion() {
		return version;
	}
	public String toString() {
		return "IFengine version: " + version + ". Playing: " + gamePath;
	}


	public static void parseCommand(String in) throws InterruptedException {
		// TODO Auto-generated method stub
		TokenWord[] tokenizedSentence = sentenceParse(in);
			commandGrab(tokenizedSentence);
		// TODO check if leading word is a verb. if it isnt, are its synonyms verbs? if no, dont proceed. 
		// check leading word synonyms run down the block to catch known commands. then pass in known commands' arguments and manipulate them.
	}


	private static void commandGrab(TokenWord[] tokenizedSentence) throws InterruptedException {
		// TODO create all commands
		
			if (tokenizedSentence[0].getWord().equals("look")) {
				System.out.println("look invoked");
				look();
			} else if (tokenizedSentence[0].getWord().equals("take")) {
				System.out.println("take invoked");
			} else if (tokenizedSentence[0].getWord().equals("examine")) {
				System.out.println("examine invoked");
			} else if (tokenizedSentence[0].getWord().equals("say")) {
				System.out.println("say invoked");
			}
		}


	private static void look() throws InterruptedException {
		// TODO Auto-generated method stub
		tell(currentRoom.toString());
	}


	public static Path roomPath(String string) {
		return Paths.get("src/main/resources/Room-" + string + ".json");
	}
	public static Path itemPath(String string) {
		return Paths.get("src/main/resources/Item-" + string + ".json");
	}
	public static void loadGame(String gameZip) throws IOException {
		File zip = new File(gameZip);
		System.out.println(zip.toString());
		makeAppDir();
		Files.copy(Paths.get(gameZip), Paths.get(System.getProperty("user.home") + "/.IFengine"));
	}
	public static void makeAppDir() {
		try {
			Files.createDirectory(Paths.get(System.getProperty("user.home") + "/.IFengine/"));
		} catch (IOException e) {
			//do nothing.
		}
	}
	
//			OLD:	
//	public static String[] chunk(String[] tokens, String [] tags) throws IOException {
//		File file = new File("src/main/resources/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
//		ChunkerModel chunkerModel = new ChunkerModel(file);  
//		ChunkerME chunkerME = new ChunkerME(chunkerModel); 
//		String result[] = chunkerME.chunk(tokens, tags);
//		return result; 
//	}
}

