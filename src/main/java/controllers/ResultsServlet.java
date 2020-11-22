package controllers;

import models.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/result")
public class ResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<Book> books;
            String searchType = req.getParameter("search_type");
            String keyword = req.getParameter("keyword");
            String st;
            switch (searchType) {
                case "title": {
                    books = Book.getBooksByTitle(keyword);
                    st = "par titre";
                    break;
                }
                case "author": {
                    books = Book.getBooksByAuthorName(keyword);
                    st = "par auteur";
                    break;
                }
                case "domain": {
                    books = Book.getBooksByDomain(keyword);
                    st = "par domaine";
                    break;
                }
                default: {
                    throw new Exception("Search type not recognized");
                }
            }
            req.setAttribute("books", books);
            req.setAttribute("length", books.size());
            req.setAttribute("keyword", keyword);
            req.setAttribute("type", searchType);
            req.setAttribute("search_type", st);
            req.getRequestDispatcher("resultats.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/rechercher");
        }
    }
}
