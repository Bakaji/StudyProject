package controllers.api.author;


import models.Author;
import utils.serializer.JsonListBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/api/authors/all/")
public class GetAuthorsAPIServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Author> authors = Author.getAllAuthors();
        JsonListBuilder listBuilder = new JsonListBuilder();
        for (Author a : authors)
            listBuilder.addNestedObject(a.buildObjectJson());
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter w = resp.getWriter();
        w.write(listBuilder.buildListJson());
        w.flush();
    }
}
