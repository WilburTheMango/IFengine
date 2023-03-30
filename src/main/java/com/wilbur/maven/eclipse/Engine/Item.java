package com.wilbur.maven.eclipse.Engine;

import java.nio.file.Path;
import java.util.*;


public class Item {
	// name
	// description
	// 
	// boolean isContainer
	// if isContainer, transparent or not.
	// TODO boolean isUseable
	// TODO if isUseable add use
	// TODO getters/setters, toString.
	private String itemName; 
	private String itemDesc;
	private String itemExamine;
	private boolean isContainer; 
	private boolean transparent;
	private boolean isOpen;
	private boolean isLocked;
	private boolean isStatic;
	ArrayList<Item> containedItems;
	public Item() {
		this.itemName = null;
		this.itemDesc = null;
		this.itemExamine = null;
		this.isContainer = false;
		this.transparent = false;
		this.isOpen = false;
		this.isLocked = false;
		this.containedItems = null;
	}
	public Item(String itemName, String itemDesc, String itemExamine, boolean isContainer, boolean transparent, boolean open, boolean isLocked, boolean isStatic, ArrayList<Item> containedItems) {
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemExamine = itemExamine;
		this.isContainer = isContainer;
		this.transparent = transparent;
		this.isOpen = open;
		this.isLocked = isLocked;
		this.isStatic = isStatic;
		this.containedItems = containedItems;
	}
	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", itemDesc=" + itemDesc + ", itemExamine=" + itemExamine
				+ ", isContainer=" + isContainer + ", transparent=" + transparent + ", isOpen=" + isOpen + ", isLocked="
				+ isLocked + ", isStatic=" + isStatic + ", containedItems=" + containedItems + "]";
	}
	public String toStringFancy() {
		if (isContainer == true) {
			if (isOpen == true && containedItems.size() > 0) {return itemName + ": " + getItemDesc() + "; " + "Open." + "\n Inside is a: " + getContainedItemsFancy();} 
			else if (isOpen == false) {return itemName + ": " + getItemDesc() + "; " + "Closed."; }
		} else if (!isContainer) { return itemName + ": " + getItemDesc() + "; "; }
		return itemName + ": " + itemDesc;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemExamine() {
		return itemExamine;
	}
	public void setItemExamine(String itemExamine) {
		this.itemExamine = itemExamine;
	}
	public boolean isContainer() {
		return isContainer;
	}
	public void setContainer(boolean isContainer) {
		this.isContainer = isContainer;
	}
	public boolean isTransparent() {
		return transparent;
	}
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	public ArrayList<Item> getContainedItems() {
		return containedItems;
	}
	public void setContainedItems(ArrayList<Item> containedItems) {
		this.containedItems = containedItems;
	}
	public String getContainedItemsFancy() {
		String ret = "";
		for (int i = 0; i < containedItems.size(); i++) {
			if (i == containedItems.size() || i == 1) {
				ret += containedItems.get(i).getItemName() + ".";
			} else {
				ret += containedItems.get(i).getItemName() + ", ";
			}
		}
		return ret;
	}
	public Item getContainedItem(String item) {
		for (int i = 0; i < containedItems.size(); i++) {
			if (item.equals(containedItems.get(i).getItemName().toLowerCase())) {
				return containedItems.get(i);
			}
		}
		return null;
	}
	public void removeContainedItem(String item) {
		for (int i = 0; i < containedItems.size(); i++) {
			if (item.equals(containedItems.get(i).getItemName().toLowerCase())) {
				containedItems.remove(i);
			}
		}
	}
	

	
	
}
