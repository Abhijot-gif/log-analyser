import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class LogAnalyser {
    public static void main(String[] args) {
        // The log file we analyse.
        File logFile = new File("sample.log");

        // Tracks how many times each IP address failed to log in
        HashMap<String,Integer> failedAttempts = new HashMap<>();

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
               // System.out.println("IP: " + ip + " | user: " + username  + "| status: " + status);
                // Only care about failed logins for brute-force detection
                if (status.equals("FAILED")) {
                    // Get this IP's current count (0 if never seen before), then add 1
                    int current = failedAttempts.getOrDefault(ip,0);
                    failedAttempts.put(ip,current+1);
                }
            }
            scanner.close(); // close the file once complete

            // An IP with too many failures looks like a brute-force attack
            int threshold = 5;

            System.out.println("--- Security Report ---");
            for (String ip: failedAttempts.keySet()) {
                int failures = failedAttempts.get(ip);
                if (failures >= threshold) {
                    System.out.println("ALERT: " + ip + " had " + failures
                            + " failed logins - possible brute-force attack."
                    );
                } else {
                    System.out.println("OK: " + ip + " had " + failures + " failed login(s)");
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find the log file: " + logFile.getName());
        }
    }
}
