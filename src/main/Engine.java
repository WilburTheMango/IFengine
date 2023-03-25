package interactiveFictionEngine;
import java.util.*;
import org.json.simple.*;

public class Engine {
	
	private String game;
	private static String version = "0.01";
	
	public Engine(String game) {
		this.game = game; 
	}
	
	public static void tell(String in) throws InterruptedException {
		for (int i = 0; i < in.length(); i++) {
			System.out.print(in.charAt(i));
			Thread.sleep(25);
		}
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int random = rand.nextInt(max + 1);
		return random;	
	}
	
	public static String parseLang() {
		Map<String, String> mapDict = new HashMap<>();
		String[] splited = str.split("\\s+");
		search(splited);
		String command = "";
		return command;
	}
	
	public static void search(String[] splited) {
		// iterate hashmap of commands to synonyms
		
		
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
		return "IFengine version: " + version + ". Playing: " + game;
	}
	
}
