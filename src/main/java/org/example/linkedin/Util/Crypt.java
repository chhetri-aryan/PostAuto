package org.example.linkedin.Util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Crypt {
    public static String Encrypt(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean Decrypt(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
