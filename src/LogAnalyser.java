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
                System.out.println(line);
            }
            scanner.close(); // close the file once complete
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find the log file: " + logFile.getName());
        }
    }
}
