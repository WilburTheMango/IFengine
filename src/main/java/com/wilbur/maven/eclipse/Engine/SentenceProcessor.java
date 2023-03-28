package com.wilbur.maven.eclipse.Engine;

import java.io.File;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

public class SentenceProcessor {
	
	static File pos = new File("src/main/resources/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
	
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
//	OLD:	
//public static String[] chunk(String[] tokens, String [] tags) throws IOException {
//File file = new File("src/main/resources/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
//ChunkerModel chunkerModel = new ChunkerModel(file);  
//ChunkerME chunkerME = new ChunkerME(chunkerModel); 
//String result[] = chunkerME.chunk(tokens, tags);
//return result; 
//}
}
