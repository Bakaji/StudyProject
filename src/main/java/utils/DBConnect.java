package utils;

import java.io.*;

public class DBConnect {
    private static final String filename = "dbconnect";
    private static DBConnect instance = null;

    public static DBConnect getInstance() {
        if (instance == null)
            instance = new DBConnect();
        return instance;
    }


    private DBConnect() {
        super();
        try {
            if (new File(filename).exists()) {
                fileLoaded = loadCredentials();
            }
        } catch (Exception ignored) {

        }
    }

    boolean fileLoaded = false;

    public boolean isFileLoaded() {
        return fileLoaded;
    }

    private static String url = null;
    private static String port = null;
    private static String username = null;
    private static String password = "";

    public String getDbUrl(String dbname) {
        return "jdbc:mysql://" + url + ":" + port + "/" + dbname;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return username;
    }

    private static final String URL_KEYWORD = "URL";
    private static final String PORT_KEYWORD = "PORT";
    private static final String USERNAME_KEYWORD = "USERNAME";
    private static final String PASSWORD_KEYWORD = "PASSWORD";

    private static synchronized boolean loadCredentials() {
        try {
            String content = Tools.readInputStream(new FileInputStream(new File(filename)));
            String[] lines = content.split(System.lineSeparator());
            for (String line : lines) {
                if (line.startsWith(URL_KEYWORD) && line.length() > (URL_KEYWORD.length() + 1)) {
                    url = line.substring(URL_KEYWORD.length() + 1);
                    continue;
                }
                if (line.startsWith(PORT_KEYWORD) && line.length() > (PORT_KEYWORD.length() + 1)) {
                    port = line.substring(PORT_KEYWORD.length() + 1);
                    continue;
                }
                if (line.startsWith(USERNAME_KEYWORD) && line.length() > (USERNAME_KEYWORD.length() + 1)) {
                    username = line.substring(USERNAME_KEYWORD.length() + 1);
                    continue;
                }
                if (line.startsWith(PASSWORD_KEYWORD) && line.length() > (PASSWORD_KEYWORD.length() + 1)) {
                    password = line.substring(PASSWORD_KEYWORD.length() + 1);
                    continue;
                }
            }
            return username != null && url != null && password != null & port != null;
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
        return false;
    }

    public void setConfigurations(String u, String p, String usr, String pwd) {
        url = u;
        port = p;
        username = usr;
        password = pwd;
        File f = new File(filename);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            writer.write(URL_KEYWORD + "=" + url + System.lineSeparator());
            writer.write(PORT_KEYWORD + "=" + port + System.lineSeparator());
            writer.write(USERNAME_KEYWORD + "=" + username + System.lineSeparator());
            writer.write(PASSWORD_KEYWORD + "=" + password + System.lineSeparator());
            writer.flush();
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
    }
}
