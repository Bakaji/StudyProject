package controllers;

import models.Author;
import utils.CookiesHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAuthorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //traitement d'une requete pour ajouter un nouveau auteur
        try {
            //dans certain cas le traitement peut génère un erreur
            boolean exists = CookiesHandler.verifyAuthenticationCookie(req.getCookies());
            // verification d'authentification
            if (exists) {
                //recuperation des données de la requete
                String AuthorFirstName = req.getParameter("author-first-name");
                String AuthorLastName = req.getParameter("author-last-name");
                String AuthorBirthDay = req.getParameter("author-birth-day");
                assert AuthorFirstName != null && AuthorLastName != null && AuthorBirthDay != null;
                //création d'auteur et retourne le succès ou échoue de traitement
                if (Author.create(AuthorFirstName, AuthorLastName, AuthorBirthDay)) {
                    resp.addCookie(CookiesHandler.getMessageCookie("author created successfully"));
                    resp.sendRedirect(req.getContextPath() + "/admin");

                }
            } else {
                resp.addCookie(CookiesHandler.getErrorCookie("admin not authenticated"));
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (AssertionError | IOException error) {
            resp.addCookie(CookiesHandler.getErrorCookie("something went wrong"));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //retourner la page du creation des auteurs (View Model)
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/ajouterAuteur.jsp").forward(req, resp);
    }

}
