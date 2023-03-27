package com.wilbur.maven.eclipse.Engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoryParser {
    
    // Method that extracts a given zip file to a directory in the user's home directory
    public static void extractZip(String zipFilePath) throws IOException {
        // Create a file object representing the user's home directory
        String userHome = System.getProperty("user.home");
        File homeDir = new File(userHome);
        
        // Create a new directory in the home directory to store the extracted files
        File ifEngineDir = new File(homeDir, ".IFengine");
        if (!ifEngineDir.exists()) {
            if (!ifEngineDir.mkdir()) {
                throw new IOException("Failed to create directory: " + ifEngineDir.getAbsolutePath());
            }
        }
        
        // Get the name of the zip file without the .zip extension
        String zipFileName = new File(zipFilePath).getName().replaceAll("\\.zip$", "");
        
        // Create a new directory under the .IFengine directory with the same name as the zip file
        File zipDir = new File(ifEngineDir, zipFileName);
        if (!zipDir.exists()) {
            if (!zipDir.mkdir()) {
                throw new IOException("Failed to create directory: " + zipDir.getAbsolutePath());
            }
        }
        
        // Open the zip file and iterate through its entries
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                InputStream inputStream = zipFile.getInputStream(entry);
                
                // Create a new file under the zip directory with the same name as the zip entry
                File outputFile = new File(zipDir, entryName);
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                    // Copy the contents of the zip entry to the new file
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
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
    public static JsonNode getJsonNodeFromFile(String storyPath, String filename) {
        File file = new File(storyPath, filename);
        if (!file.exists()) {
            System.out.println("Error: file not found.");
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(file);
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }

}
