package utilities;

import constants.AppConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class WriteToCSV {

    public void writeCSV(List<String> data, String filePath){

        File file = new File(AppConstants.USER_DIR + filePath);

        try (FileWriter writer = new FileWriter(file , true)) {
            Iterator<String> iterator = data.iterator();
            while (iterator.hasNext()){
                writeLine(writer,iterator.next());
            }

            System.out.println("CSV file written successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    // Helper method to write a single line to the CSV
    private static void writeLine(FileWriter writer, String values) throws IOException {
        String line = String.join(",", values);
        writer.write(line + "\n");
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
