package controllers.api.author;

import utils.CookiesHandler;
import models.Author;
import utils.Tools;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/authors/edit/")
public class UpdateAuthorAPIServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            try {
                String json = Tools.readInputStream(req.getInputStream());
                if (!json.isEmpty()) {
                    String done = Author.updateAuthor(json);
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
            resp.setStatus(401);
        }
    }
}
