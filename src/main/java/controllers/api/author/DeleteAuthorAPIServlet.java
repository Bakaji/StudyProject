package controllers.api.author;

import utils.CookiesHandler;
import utils.LibraryDbUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;

public class DeleteAuthorAPIServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement statement = connection.createStatement()) {
                String author = req.getRequestURI().substring((req.getContextPath() + "/api/authors/delete/").length());
                if (author.length() != 0) {
                    if (author.charAt(author.length() - 1) == '/') {
                        author = author.substring(0, author.length() - 1);
                    }
                    int authorId = Integer.parseInt(author);
                    assert authorId >= 0;
                    int i = statement.executeUpdate(String.format("delete from author where authorId=%d;", authorId));
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
