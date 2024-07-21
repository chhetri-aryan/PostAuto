package org.example.linkedin.Model;

public class Session {
    private static User currentUser;

    public static void startSession(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void endSession() {
        currentUser = null;
    }
}
