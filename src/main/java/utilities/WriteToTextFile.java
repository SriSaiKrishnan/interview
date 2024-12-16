package utilities;

import constants.AppConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToTextFile {

    public void writeToTextFile(String content, String filePath){
        // Write to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AppConstants.USER_DIR+filePath, true))) { // `true` for appending
            writer.write(content); // Write content to the file
            writer.newLine(); // Add a new line
            writer.newLine(); // Add a new line
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public void deleteFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Existing file deleted successfully.");
            } else {
                System.out.println("Failed to delete the existing file.");
            }
        }
        System.out.println("File does not exist.");
    }

}
