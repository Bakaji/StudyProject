package controllers.api.book;

import utils.CookiesHandler;
import utils.LibraryDbUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;


public class DeleteBookAPIServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
                String book_issn = req.getRequestURI().substring((req.getContextPath() + "/api/books/delete/").length());
                if (book_issn.length() > 0 && book_issn.charAt(book_issn.length() - 1) == '/') {
                    book_issn = book_issn.substring(0, book_issn.length() - 1);
                }
                if (book_issn.length() != 0) {
                    int i = statement.executeUpdate(String.format("delete from book where ISSN='%s';", book_issn));
                    resp.setStatus(i > 0 ? 200 : 400);
                } else {
                    resp.setStatus(400);
                }
            } catch (Exception e) {
                resp.setStatus(401);
            }
        } else {
            resp.setStatus(401);
        }
    }
}
