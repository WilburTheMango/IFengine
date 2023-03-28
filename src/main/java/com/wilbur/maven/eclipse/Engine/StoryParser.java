package com.wilbur.maven.eclipse.Engine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoryParser {
    
    // Method that extracts a given zip file to a directory in the user's home directory
	public static String extractZip(String zipFilePath) {
	    try {
	        File zipFile = new File(zipFilePath);
	        String zipFileName = zipFile.getName();
	        String unzipDirName = zipFileName.substring(0, zipFileName.lastIndexOf("."));

	        String userHomeDir = System.getProperty("user.home");
	        String IFengineDir = userHomeDir + File.separator + ".IFengine";
	        File IFengineDirFile = new File(IFengineDir);

	        if (!IFengineDirFile.exists()) {
	            IFengineDirFile.mkdir();
	        }

	        String unzipDirPath = IFengineDir + File.separator + unzipDirName;
	        File unzipDir = new File(unzipDirPath);
	        if (unzipDir.exists()) {
	            return unzipDirPath;
	        }
	        unzipDir.mkdir();

	        ZipFile zip = new ZipFile(zipFile);
	        Enumeration<? extends ZipEntry> entries = zip.entries();

	        while (entries.hasMoreElements()) {
	            ZipEntry entry = entries.nextElement();
	            File entryDestination = new File(unzipDir, entry.getName());

	            if (entry.isDirectory()) {
	                entryDestination.mkdirs();
	            } else {
	                InputStream in = zip.getInputStream(entry);
	                OutputStream out = new FileOutputStream(entryDestination);

	                byte[] buffer = new byte[1024];
	                int len;
	                while ((len = in.read(buffer)) > 0) {
	                    out.write(buffer, 0, len);
	                }

	                in.close();
	                out.close();
	            }
	        }
	        zip.close();
	        System.out.println(unzipDirPath);
	        return unzipDirPath;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    
    public static Path getPathToStory(String storyName) {
        File extractedFolder = new File(System.getProperty("user.home"), ".IFengine");
        if (!extractedFolder.exists()) {
            System.out.println("Error: extracted folder does not exist.");
            return null;
        }
        
        File storyFolder = new File(extractedFolder, storyName);
        if (!storyFolder.exists()) {
            System.out.println("Error: story folder does not exist in extracted folder.");
            return null;
        }
        
        return Paths.get(storyFolder.getAbsolutePath());
    }
    public static JsonNode getJsonNodeFromFile(String unzippedDir, String filename) {
        String filePath = unzippedDir + "/" + filename;
        try {
            // Read JSON file into string
            String fileContent = Files.readString(Paths.get(filePath));

            // Parse JSON string into JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fileContent);

            // Check if the node can be created
            if (jsonNode == null || jsonNode.isMissingNode()) {
                System.err.println("Failed to create JsonNode for file: " + filename);
                return null;
            }

            return jsonNode;
        } catch (JsonParseException e) {
            System.err.println("Malformed JSON in file: " + filename);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("Failed to read file: " + filename);
            e.printStackTrace();
            return null;
        }
    }
    public static JsonNode getJsonNodeFromFile(String absPath) {
        //String filePath = unzippedDir + "/" + filename;
        try {
            // Read JSON file into string
            String fileContent = Files.readString(Paths.get(absPath));

            // Parse JSON string into JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fileContent);

            // Check if the node can be created
            if (jsonNode == null || jsonNode.isMissingNode()) {
                System.err.println("Failed to create JsonNode for file: " + absPath);
                return null;
            }

            return jsonNode;
        } catch (JsonParseException e) {
            System.err.println("Malformed JSON in file: " + absPath);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("Failed to read file: " + absPath);
            e.printStackTrace();
            return null;
        }
    }

    


}
