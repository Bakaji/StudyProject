package controllers;

import models.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class BookDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String issn = request.getParameter("book_issn");
            ArrayList<Book> books = Book.getBooksByIssn(issn);
            if (books.size() > 0) {
                Book book = books.get(0);
                request.setAttribute("book", book);
                request.getRequestDispatcher("detailsDocument.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/rechercher");
        }
    }
}
