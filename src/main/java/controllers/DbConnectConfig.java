package controllers;

import org.json.JSONObject;
import utils.DBConnect;
import utils.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@WebServlet("/config/database/")
public class DbConnectConfig extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/configBDD.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = Tools.readInputStream(req.getInputStream());

        boolean errorOccurred = false;
        try {
            JSONObject object = new JSONObject(data);
            String url = object.getString("url");
            String port = object.getString("port");
            String username = object.getString("username");
            String password = object.getString("password");
            try {
                //load driver
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Tools.printError(e.getMessage());
            }
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/", username, password);
                 Statement statement = connection.createStatement()) {
                DBConnect.getInstance().setConfigurations(url, port, username, password);
            } catch (Exception e) {
                errorOccurred = true;
            }
        } catch (Exception e) {
            errorOccurred = true;
        }

        resp.setStatus(errorOccurred ? 400 : 200);
        resp.setContentType("application/json");
    }
}
