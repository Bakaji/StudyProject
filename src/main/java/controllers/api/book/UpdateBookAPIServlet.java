package controllers.api.book;

import utils.CookiesHandler;
import models.Book;
import utils.Tools;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/books/edit/")
public class UpdateBookAPIServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            try {
                String json = Tools.readInputStream(req.getInputStream());
                if (!json.isEmpty()) {
                    String done = Book.updateBook(json);
                    if (done != null) {
                        resp.getWriter().write(done);
                        resp.setStatus(200);
                    } else {
                        resp.setStatus(401);
                    }
                    resp.setContentType("application/json");
                }
            } catch (Exception ignored) {

            }
        } else {
            resp.setStatus(403);
        }
    }
}
