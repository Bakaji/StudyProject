package models;

import utils.LibraryDbUtils;
import utils.Tools;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class Administrator {
    private static Administrator instance = null;
    private String passwordHash;
    private String username;
    private String token;

    public static Administrator getInstance() {
        if (instance == null)
            instance = new Administrator();
        return instance;
    }

    private static final String filename = "admin";

    private Administrator() {
        File f = new File(filename);
        if (!f.exists()) {
            setCredentials("admin", "admin");
        }
        loadCredentials(f);
    }

    private static final String USER_KEYWORD = "USER";
    private static final String PASS_KEYWORD = "PASS";
    private static final String TOKEN_KEYWORD = "TOKEN";

    private void loadCredentials(File f) {
        try {
            String content = Tools.readInputStream(new FileInputStream(f));
            String[] lines = content.split(System.lineSeparator());
            for (String line : lines) {
                if (line.startsWith(USER_KEYWORD) && line.length() > (USER_KEYWORD.length() + 1)) {
                    username = line.substring(USER_KEYWORD.length() + 1);
                    continue;
                }
                if (line.startsWith(PASS_KEYWORD) && line.length() > (PASS_KEYWORD.length() + 1)) {
                    passwordHash = line.substring(PASS_KEYWORD.length() + 1);
                    continue;
                }
                if (line.startsWith(TOKEN_KEYWORD) && line.length() > (TOKEN_KEYWORD.length() + 1)) {
                    token = line.substring(TOKEN_KEYWORD.length() + 1);
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            Tools.printError(e.getMessage());
        }
    }

    private boolean setCredentials(String username, String password) {
        this.username = username;
        passwordHash = Tools.getPasswordHash(password);
        token = Tools.getToken();
        File f=new File(filename);
        try {
            if(!f.exists())
                f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            writer.write(USER_KEYWORD + "=" + this.username);
            writer.write(System.lineSeparator());
            writer.write(PASS_KEYWORD + "=" + this.passwordHash);
            writer.write(System.lineSeparator());
            writer.write(TOKEN_KEYWORD + "=" + this.token);
            writer.write(System.lineSeparator());
            writer.flush();
            return true;
        } catch (Exception ignored) {

        }
        return false;
    }
    public boolean validToken(String t){
        return token.equals(t);
    }

    public String validCredentials(String username, String password) {
        if (username.equals(this.username) && Objects.equals(Tools.getPasswordHash(password), this.passwordHash))
            return token;
        return null;
    }

    public boolean updateUsernameAndPassword(String username, String password) {
        return setCredentials(username, password);
    }
}
