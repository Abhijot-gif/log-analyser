import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogAnalyser {
    public static void main(String[] args) {
        // The log file we analyse.
        File logFile = new File("sample.log");

        try {
            Scanner scanner = new Scanner(logFile);

            // Read through the file one line at a time
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Split the line into pieces when there's a space.
                String[] parts = line.split(" ");

                // Check in case lines are malformed:
                // Skip any line that doesn't have all 4 fields.
                if (parts.length < 4) {
                    continue;
                }

                //Pull out each field by its positions
                String timestamp = parts[0];
                String ip = parts[1];
                String username = parts[2];
                String status = parts[3];

                // Print them back out to check parsing worked.
                System.out.println("IP: " + ip + " | user: " + username  + "| status: " + status);
            }
            scanner.close(); // close the file once complete
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find the log file: " + logFile.getName());
        }
    }
}
