package utils;

import models.Administrator;
import utils.LibraryDbUtils;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CookiesHandler {
    private static String cookieName = "token";

    public static Cookie createCookie(String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(24 * 3600 * 30);
        return cookie;
    }

    public static boolean verifyAuthenticationCookie(Cookie[] requestCookies) {
        boolean exists = false;
        for (Cookie c : requestCookies) {
            if (c.getName().equals(cookieName)) {
                String token = c.getValue();
                return Administrator.getInstance().validToken(token);
            }
        }
        return exists;
    }

    public enum MessageType {
        ERROR,
        MESSAGE
    }

    public static Cookie getMessageCookie(String message) {
        Cookie c = new Cookie("message", message);
        c.setMaxAge(24 * 3600);
        return c;
    }

    public static Cookie getErrorCookie(String message) {
        Cookie c = new Cookie("error", message);
        c.setMaxAge(24 * 3600);
        return c;
    }

    public static Cookie getAuthenticationErrorCookie() {
        return getErrorCookie("User not authenticated");
    }
}
