package com.wilbur.maven.eclipse.run;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilbur.maven.eclipse.Engine.Engine;
import com.wilbur.maven.eclipse.Engine.TokenWord;
import com.wilbur.maven.eclipse.Engine.Item;
import com.wilbur.maven.eclipse.Engine.Room;
import com.wilbur.maven.eclipse.Engine.Character;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException {
		Engine.tell("Version: " + Engine.getVersion());
		String in = "Look";
		
		 // hello from git!
//		TokenWord[] myToks = Engine.sentenceParse(in);
//		for (int i = 0; i < myToks.length; i++) {
//			System.out.println(myToks[i].toString());
//		}
//		Engine.roomPath("Test");
		
		
//		Engine.loadRoom(Engine.roomPath("Test"));
//		
//		Engine.parseCommand(in);
//		Engine.parseCommand("examine mailbox");
//		Engine.parseCommand("take mailbox");
//		Engine.parseCommand("open mailbox");
//		Engine.parseCommand("open mailingbox");
//		Engine.parseCommand("take leafinglet");
//		Engine.parseCommand("take leaflet");
//		Engine.parseCommand(in);
//		Engine.parseCommand("what");
//		
		String homies = System.getProperty("user.home");
		Engine.loadGame("/home/tyler/Desktop/zipityzipzip.zip");
		
	}
	
	
	
	
}

//NN – noun, singular or mass
//DT – determiner
//VB – verb, base form
//VBD – verb, past tense
//VBZ – verb, third person singular present
//IN – preposition or subordinating conjunction
//NNP – proper noun, singular
//TO – the word “to”
//JJ – adjective