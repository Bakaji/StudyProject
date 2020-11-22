package controllers.api.book;

import models.Book;
import utils.Tools;
import utils.serializer.JsonListBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/api/books")
public class GetBooksAPIServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("authorId"));
            ArrayList<Book> books = Book.getBooksByAuthorId(id);
            JsonListBuilder listBuilder = new JsonListBuilder();
            for (Book b : books)
                listBuilder.addNestedObject(b.buildObjectJson());
            resp.setContentType("application/json");
            PrintWriter w = resp.getWriter();
            w.write(listBuilder.buildListJson());
            w.flush();
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
    }
}
