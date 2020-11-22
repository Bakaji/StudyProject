package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LibraryDbUtils {
    private static LibraryDbUtils __instance = null;
//    static String url = "jdbc:mysql://localhost:3306/";
//    static String user = "root";
//    static String password = "";

    public Connection getDatabaseConnection() {
        try {
            return DriverManager.getConnection(DBConnect.getInstance().getDbUrl("library"),
                    DBConnect.getInstance().getUser(),
                    DBConnect.getInstance().getPassword());
        } catch (Exception e) {
            return null;
        }
    }

    public static LibraryDbUtils getInstance() {
        if (__instance == null) {
            try {
                //load driver
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Tools.printError(e.getMessage());
            }
            __instance = new LibraryDbUtils();
        }
        return __instance;
    }

    private LibraryDbUtils() {
        initDatabase();
    }

    private void initDatabase() {
        try (Connection c = DriverManager.getConnection(DBConnect.getInstance().getDbUrl("mysql"),
                DBConnect.getInstance().getUser(),
                DBConnect.getInstance().getPassword()); Statement s = c.createStatement()) {
            s.execute("create database if not exists library CHARACTER SET utf8mb4;");
            s.execute("use library;");
            ResultSet set = s.executeQuery("SELECT COUNT(*)\n" +
                    "FROM information_schema.tables \n" +
                    "WHERE table_schema = 'library' \n" +
                    "AND table_name = 'Author';");
            set.next();
            int count = set.getInt(1);
            if (count == 0) {
                s.execute("create table Author( authorId int primary key auto_increment, authorFirstName varchar(50) not null, authorLastName varchar(50) not null, authorBirthDay date not null);");
                s.execute("create table Book( ISSN varchar(25) primary key, titre varchar(100) not null, resume varchar(2000) default 'no résumé', nbPages int not null, domaine varchar(50), authorId int, FOREIGN KEY (authorId) references Author (authorId) ON DELETE CASCADE);");

            }
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
    }
}
