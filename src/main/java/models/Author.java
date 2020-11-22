package models;

import org.json.JSONObject;
import utils.LibraryDbUtils;
import utils.Tools;
import utils.serializer.JsonObjectBuilder;
import utils.serializer.SerializedClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Author extends SerializedClass<Author> {
    //la methode responsable de creation des auteurs (ajoute au BDD)
    public static boolean create(String firstname, String lastName, String birthDay) {
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            int i = statement.executeUpdate(String.format("insert into author(authorFirstName, authorLastName, authorBirthDay) VALUES ('%s','%s','%s')", firstname, lastName, birthDay));
            if (i > 0)
                return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    //les données des auteurs
    String firstname, lastName, birthDay;
    int authorId;

    //constricteur
    public Author(int authorId, String firstname, String lastName, String birthDay) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.authorId = authorId;
    }

    //recuperation des auteurs
    public static ArrayList<Author> getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery("select authorId,authorFirstName,authorLastName,authorBirthDay from author;");
            while (results.next()) {
                authors.add(new Author(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4)
                ));
            }
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
        return authors;
    }

    //la methode responsable de modification des auteurs (modification au BDD)
    public static String updateAuthor(String json) {
        try {
            Author author;
            JSONObject jsonObject = new JSONObject(json);
            int i = jsonObject.getInt("author_id");
            if ((author = authorExists(i)) != null) {
                return author.update(jsonObject);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String update(JSONObject jsonObject) {
        try {
            String fn = jsonObject.getString("first_name");
            if (fn != null && fn.length() > 0) {
                this.firstname = fn;
            }
        } catch (Exception ignored) {

        }
        try {
            String ln = jsonObject.getString("last_name");
            if (ln != null && ln.length() > 0) {
                this.lastName = ln;
            }
        } catch (Exception ignored) {

        }
        try {
            String bd = jsonObject.getString("birth_day");
            if (bd != null && bd.length() > 0) {
                this.birthDay = bd;
            }
        } catch (Exception ignored) {

        }
        int i = -1;
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            i = statement.executeUpdate(String.format("update author set authorFirstName='%s', authorLastName='%s', authorBirthDay='%s'where authorId=%d;",
                    this.firstname, this.lastName, this.birthDay, this.authorId));
        } catch (Exception ignored) {
            return null;
        }
        return i > 0 ? buildObjectJson() : null;
    }

    //une methode qui verifie l'existance d'un auteur par son identifient
    private static Author authorExists(int i) {
        Author exists = null;
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select authorFirstName, authorLastName, authorBirthDay from author where authorId=" + i + ";");
            if (rs.next()) {
                exists = new Author(i,
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
            }

        } catch (Exception ignored) {

        }
        return exists;
    }

    @Override
    public String buildObjectJson() {
        return new JsonObjectBuilder()
                .addInt("author_id", authorId)
                .addString("first_name", firstname)
                .addString("last_name", lastName)
                .addString("birth_day", birthDay)
                .buildObject();
    }
}
