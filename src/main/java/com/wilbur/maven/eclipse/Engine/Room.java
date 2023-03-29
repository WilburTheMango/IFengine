package com.wilbur.maven.eclipse.Engine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.LinkedHashMap;

public class Room {
	public String roomName;
	public String roomDesc;
	public ArrayList<Item> Items = new ArrayList<Item>();
	public ArrayList<Character> Characters = new ArrayList<Character>();
	LinkedHashMap<String, String> exits = new LinkedHashMap<String, String>();
	
	
	
	public Room(String roomName, String roomDesc, ArrayList<Item> Items, ArrayList<Character> Characters, LinkedHashMap<String, String> Exits) {
		
		this.roomName = roomName;
		this.roomDesc = roomDesc;
		this.Items = Items;
		this.Characters = Characters;
		this.exits = Exits;
		
		// ADD THE DAMN ITEMS AND CHARACTERS AND EXITS ARRAY
	}
	public String getItemsFancy() {
		String itemsInRoom = "";
		for (int i = 0; i < Items.size(); i++) {
			itemsInRoom = Items.get(i).toStringFancy() + ", ";
		}
		return itemsInRoom;
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}
	public ArrayList<Item> getItems() {
		return Items;
	}
	public void setItems(ArrayList<Item> items) {
		Items = items;
	}
	public ArrayList<Character> getCharacters() {
		return Characters;
	}
	public void setCharacters(ArrayList<Character> characters) {
		Characters = characters;
	}
	public LinkedHashMap<String, String> getExits() {
		return exits;
	}
	public void setExits(LinkedHashMap<String, String> exits) {
		this.exits = exits;
	}
	public String toStringFancy() {
		// The formatting for a room being put to console (window later...)
		return roomName + "\n\n" + roomDesc + "\n\n" + "Here is a: \n" + getItemsFancy();
	}
}