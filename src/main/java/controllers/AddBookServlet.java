package controllers;

import models.Book;
import utils.CookiesHandler;
import utils.LibraryDbUtils;
import utils.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class AddBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");
        try (Connection connection = LibraryDbUtils.getInstance().getDatabaseConnection(); Statement s = connection.createStatement()) {
            int authorId = Integer.parseInt(req.getParameter("authorId"));
            String query = "select count(*),authorFirstName,authorLastName from author where authorId=" + authorId + ";";
            ResultSet rs = s.executeQuery(query);
            rs.next();
            if (rs.getInt(1) > 0) {
                Tools.printError(String.valueOf(rs.getInt(1)));
                req.setAttribute("authorId", authorId);
                req.setAttribute("authorFullName", rs.getString(2) + " " + rs.getString(3));
                req.getRequestDispatcher("/ajouterDocument.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin");
            }
        } catch (Exception e) {
            Tools.printError(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            resp.setContentType("text/html;charset=UTF-8");
            try {
                int authorId = Integer.parseInt(req.getParameter("authorId"));
                String issn = req.getParameter("book-issn");
                String title = req.getParameter("book-title");
                String resume = req.getParameter("book-resume");
                int pagesNumber = Integer.parseInt(req.getParameter("book-page-nbr"));
                String domain = req.getParameter("book-domain");
                assert issn != null && title != null && resume != null && domain != null;
                if (Book.create(issn, title, resume, pagesNumber, domain, authorId)) {
                    resp.addCookie(CookiesHandler.getMessageCookie("Book " + title + " created successfully"));
                } else {
                    resp.addCookie(CookiesHandler.getErrorCookie("Something went wrong : book not created"));
                }
            } catch (Exception e) {
                Tools.printError(e.getMessage());
                resp.setStatus(400);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/");
        } else {
            resp.addCookie(CookiesHandler.getErrorCookie("User not authenticated"));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
