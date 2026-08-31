import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Control {
    private static final String USERS_FILE = "users.txt";
    private static final String PROTECTED_FILE = "protected_data.txt";

    public static void main(String[] args) {
        UserAccount user = authenticateUser();

        if (user == null) {
            System.out.println("LOGIN FAILED");
            return;
        }

        System.out.println("LOGIN SUCCESSFUL");
        displayAuthorizedInformation(user.role);
    }

    private static UserAccount authenticateUser() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = console.nextLine().trim();
        System.out.print("Enter password: ");
        String password = console.nextLine().trim();

        List<UserAccount> users = loadUsers(USERS_FILE);
        for (UserAccount user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }

    private static List<UserAccount> loadUsers(String fileName) {
        List<UserAccount> users = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("User data file not found.");
            return users;
        }

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                if (data.length != 3) {
                    System.out.println("Skipping malformed user record: " + line);
                    continue;
                }

                String username = data[0].trim();
                String password = data[1].trim();
                String role = data[2].trim().toUpperCase();

                if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                    System.out.println("Skipping incomplete user record: " + line);
                    continue;
                }

                users.add(new UserAccount(username, password, role));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open user data file.");
        }

        return users;
    }

    private static void displayAuthorizedInformation(String role) {
        List<String> records = loadProtectedData(PROTECTED_FILE);
        if (records.isEmpty()) {
            System.out.println("No protected information available.");
            return;
        }

        Set<String> authorizedLevels = new HashSet<>();
        authorizedLevels.add("PUBLIC");

        switch (role.toUpperCase()) {
            case "STUDENT":
                authorizedLevels.add("STUDENT");
                break;
            case "TEACHER":
                authorizedLevels.add("STUDENT");
                authorizedLevels.add("TEACHER");
                break;
            case "ADMIN":
                authorizedLevels.add("STUDENT");
                authorizedLevels.add("TEACHER");
                authorizedLevels.add("ADMIN");
                break;
            default:
                break;
        }

        for (String record : records) {
            String[] parts = record.split("\\|", 2);
            if (parts.length != 2) {
                continue;
            }

            String level = parts[0].trim().toUpperCase();
            String message = parts[1].trim();

            if (authorizedLevels.contains(level)) {
                System.out.println(message);
            }
        }
    }

    private static List<String> loadProtectedData(String fileName) {
        List<String> records = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return records;
        }

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|", 2);
                if (data.length != 2) {
                    System.out.println("Skipping malformed protected record: " + line);
                    continue;
                }

                records.add(data[0].trim().toUpperCase() + "|" + data[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open protected data file.");
        }

        return records;
    }

    private static class UserAccount {
        private final String username;
        private final String password;
        private final String role;

        private UserAccount(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}
