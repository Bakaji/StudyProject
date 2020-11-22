package controllers;

import models.Administrator;
import utils.CookiesHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        resp.setContentType("text/html;charset=UTF-8");
        String password = req.getParameter("password");
        Cookie cookie;
        String token = Administrator.getInstance().validCredentials(username, password);
        if (token != null) {
            //set cookies
            resp.addCookie(CookiesHandler.createCookie(token));
            //redirect to admin page
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } else {
            //delete cookies
            cookie = new Cookie("token", "");
            cookie.setMaxAge(24 * 3600 * -1);
            resp.addCookie(cookie);
            //redirect to home page
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        if (CookiesHandler.verifyAuthenticationCookie(req.getCookies())) {
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } else {
            resp.addCookie(CookiesHandler.getAuthenticationErrorCookie());
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
