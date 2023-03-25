package com.wilbur.maven.eclipse.Engine;

import java.util.*;
import com.groupdocs.search.*;

public class TokenWord {
	public String word = "";
	public String pos = "";
	public ArrayList<String> Synonyms = new ArrayList<String>();
	
	public TokenWord(String word, String pos) {
		this.word = word.toLowerCase();
		this.pos = pos;
		this.Synonyms = synoymsParse(word);
	}

	@SuppressWarnings("resource")
	private ArrayList<String> synoymsParse(String query) {
		// IF synonyms != pos as word do not add!!!
		// FIXME
		String[] synonyms = new Index().getDictionaries().getSynonymDictionary().getSynonyms(query);
		ArrayList<String> ret = new ArrayList<String>();
		String[] synTags = Engine.parseTags(synonyms);
		
		for (int i = 0; i < synonyms.length; i++) {
			if (synTags[i].equals(pos)) {
				ret.add(synonyms[i]);
				System.out.println(synTags[i]);
			}
		}
		return ret;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public ArrayList<String> getSynonyms() {
		return Synonyms;
	}

	public void setSynonyms(ArrayList<String> synonyms) {
		Synonyms = synonyms;
	}

	@Override
	public String toString() {
		return "TokenWord [word=" + word + ", pos=" + pos + ", Synonyms=" + Synonyms + "]";
	}
	
	
}
