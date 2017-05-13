package Hangman;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by vitiv on 5/12/17.
 */
@javax.servlet.annotation.WebServlet(name = "Hangman")
public class Hangman extends javax.servlet.http.HttpServlet {
    private String dataBaseURL;
    private Connection con;

    public void init() throws ServletException {
        dataBaseURL = getInitParameter("DataBaseURL");
        try {
            Class.forName(getInitParameter("DataBaseDriver"));
            con = DriverManager.getConnection(dataBaseURL);
            System.err.println("Connection established");

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            con.close();
            System.err.println("Connection closed");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String word = this.getWord();
        request.setAttribute("word", word);
        request.getRequestDispatcher("/hangman.jsp").forward(request, response);
    }

    private String getWord() {
        try {
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM Words ORDER BY RANDOM() LIMIT 1");
            rs.next();
            return rs.getString("word");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
