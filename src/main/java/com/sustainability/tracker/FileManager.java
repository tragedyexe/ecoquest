package com.sustainability.tracker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
// This class manages the user data upon account creation and login. It handles saving and loading user information to and from a file (users.dat) using Java's serialization mechanism. 
/*  The saveUsers method writes the list of User objects to the file, while the loadUsers method reads the list back into memory when the application starts or when needed. 
*This allows for persistent storage of user data across sessions.
*/
public class FileManager {
    private static final String FILE_NAME = "users.dat";

    // Save the entire list of users to a file
    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the list of users from the file
    public static List<User> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>(); // Return empty list if no file yet

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}