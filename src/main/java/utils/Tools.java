package utils;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.util.UUID;

public class Tools {
    public static String getPasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getToken() {
        return UUID.randomUUID().toString();
    }

    public static void printError(String message) {
        System.err.println("/////////////////////////////////");
        System.err.println(message != null ? message.replace('\n', ' ') : "something went wrong");
        System.err.println("/////////////////////////////////");
    }

    public static String readInputStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        int i = -1;
        StringBuilder input = new StringBuilder();
        do {
            try {
                i = reader.read();
            } catch (IOException ignored) {
                continue;
            }
            if (i > -1)
                input.append((char) i);
        } while (i != -1);
        return input.toString();
    }
}
