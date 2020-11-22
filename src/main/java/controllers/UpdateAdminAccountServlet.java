package controllers;

import utils.CookiesHandler;
import models.Administrator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/edit-admin")
public class UpdateAdminAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            try {
                String username = req.getParameter("username");
                ArrayList<String> errors = new ArrayList<>();
                if (username.matches("^\\s$")) {
                    throw new Exception("username invalid");
                }
                String password = req.getParameter("password");
                String conf_password = req.getParameter("conf_password");

                if (password.matches("^\\s$") || !password.equals(conf_password)) {
                    throw new Exception("password or password confirmation invalid");
                }
                boolean done = Administrator.getInstance().updateUsernameAndPassword(username, password);
                if(done){
                    resp.addCookie(CookiesHandler.getMessageCookie( "administrateur change les cordonnées"));
                    resp.sendRedirect(req.getContextPath() + "/");
                }else {
                    resp.addCookie(CookiesHandler.getErrorCookie("la mise à jour n'est pas effectuée"));
                    resp.sendRedirect(req.getContextPath() + "/");
                }

            } catch (Exception ignored) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            }
        } else {
            resp.addCookie(CookiesHandler.getAuthenticationErrorCookie());
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
