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

public class Book extends SerializedClass<Book> {
    String issn, title, resume, domain;
    int pagesNumber, authorId;

    public static boolean create(String issn, String title, String resume, int pagesNumber, String domain, int authorId) {
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            String q = String.format("insert into book(ISSN, titre, resume, nbPages, domaine, authorId) VALUES ('%s','%s','%s',%d,'%s',%d)",
                    issn,
                    title,
                    resume,
                    pagesNumber,
                    domain,
                    authorId);
            int c = statement.executeUpdate(q);
            return c > 0;
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
        return false;
    }

    //region db_getters
    public static ArrayList<Book> getAllBooks() {
        String query = "select ISSN, titre, resume, nbPages, domaine, authorId from book;";
        return searchByQuery(query);
    }

    public static ArrayList<Book> getBooksByAuthorId(int id) {
        String query = "select ISSN, titre, resume, nbPages, domaine, authorId from book where authorId=" + id + ";";
        return searchByQuery(query);
    }

    public static ArrayList<Book> getBooksByTitle(String book) {
        String query = "select ISSN, titre, resume, nbPages, domaine, authorId from book where LOWER(titre) like '%" + book.toLowerCase() + "%';";
        return searchByQuery(query);
    }

    public static ArrayList<Book> getBooksByDomain(String keyword) {
        String query = "select ISSN, titre, resume, nbPages, domaine, authorId from book where LOWER(domaine) like '%" + keyword.toLowerCase() + "%';";
        return searchByQuery(query);
    }

    public static ArrayList<Book> getBooksByIssn(String keyword) {
        String query = "select ISSN, titre, resume, nbPages, domaine, authorId from book where ISSN = '" + keyword + "';";
        return searchByQuery(query);
    }

    public static ArrayList<Book> getBooksByAuthorName(String keyword) {
        String k = keyword.toLowerCase();
        String query = "select ISSN, titre, resume, nbPages, domaine, book.authorId from book,author where book.authorId=author.authorId and ( LOWER(author.authorFirstName) like '%" + k + "%' or LOWER(author.authorLastName) like '%" + k + "%');";
        return searchByQuery(query);
    }

    private static ArrayList<Book> searchByQuery(String query) {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection c = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement s = c.createStatement()) {
            ResultSet results = s.executeQuery(query);
            while (results.next()) {
                String t_issn = results.getString(1);
                String t_title = results.getString(2);
                String t_resume = results.getString(3);
                int t_pagesNumber = results.getInt(4);
                String t_domain = results.getString(5);
                int t_authorId = results.getInt(6);
                books.add(new Book(t_issn, t_title, t_resume, t_domain, t_pagesNumber, t_authorId));
            }

        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }

        return books;
    }

    //endregion

    public Book(String issn, String title, String resume, String domain, int pagesNumber, int authorId) {
        this.issn = issn;
        this.title = title;
        this.resume = resume;
        this.domain = domain;
        this.pagesNumber = pagesNumber;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getDomain() {
        return domain;
    }

    public String getAuthorName() {
        String name = "";
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery("select authorFirstName,authorLastName from author where authorId=" + authorId + ";");
            while (rs.next()) {
                name = rs.getString(1) + " " + rs.getString(2);
            }
        } catch (Exception ignored) {

        }
        return name;
    }

    public String getIssn() {
        return this.issn;
    }

    public String getResume() {
        return resume;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public static String updateBook(String json) {
        JSONObject object = new JSONObject(json);
        String id;
        Book b;
        try {
            id = object.getString("book_old_issn");
            if (!id.matches("^\\s*$")) {
                b = bookExists(id);
                if (b != null) {
                    return b.update(id, json);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String update(String old_issn, String json) {
        JSONObject obj = new JSONObject(json);

        try {
            String newISSN = obj.getString("book_new_issn");
            if (!newISSN.matches("^\\s*$")) {
                issn = newISSN;
            }
        } catch (Exception ignored) {
        }

        try {
            String t = obj.getString("book_title");
            if (!t.matches("^\\s*$")) {
                this.title = t;
            }
        } catch (Exception ignored) {
        }

        try {
            String r = obj.getString("book_resume");
            if (!r.matches("^\\s*$")) {
                resume = r;
            }
        } catch (Exception ignored) {
        }

        try {
            int pgn = obj.getInt("book_page_numbers");
            if (pgn > 0) {
                pagesNumber = pgn;
            }
        } catch (Exception ignored) {
        }

        try {
            String d = obj.getString("book_domain");
            if (!d.matches("^\\s*$")) {
                domain = d;
            }
        } catch (Exception ignored) {
        }
        int i = -1;
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement s = connection.createStatement()) {
            i = s.executeUpdate(String.format("update book set ISSN = '%s',titre='%s',resume='%s',nbPages=%d,domaine='%s' where ISSN='%s';"
                    , issn, title, resume, pagesNumber, domain, old_issn));
        } catch (Exception ignored) {

        }
        return i > 0 ? buildObjectJson() : null;
    }

    private static Book bookExists(String id) {
        Book exists = null;
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select ISSN, titre, resume, domaine,nbPages, authorId from book where ISSN='" + id + "';");
            if (rs.next()) {
                exists = new Book(id,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6));
            }

        } catch (Exception ignored) {

        }
        return exists;
    }

    @Override
    public String buildObjectJson() {
        return new JsonObjectBuilder()
                .addString("book_issn", issn)
                .addString("book_title", title)
                .addString("book_resume", resume)
                .addInt("book_page_numbers", pagesNumber)
                .addString("book_domain", domain)
                .addInt("book_author_id", authorId)
                .buildObject();
    }
}
