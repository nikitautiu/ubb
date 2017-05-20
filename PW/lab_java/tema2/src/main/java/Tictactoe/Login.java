package Tictactoe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/**
 * Created by vitiv on 5/13/17.
 */
@javax.servlet.annotation.WebServlet(name = "Login")
public class Login extends javax.servlet.http.HttpServlet {
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
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");

        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM users WHERE user=?")) {
            preStmt.setString(1, user);
            ResultSet rs = preStmt.executeQuery();
            boolean success = false;
            if (rs.next()) {
                String dbpass = rs.getString("pass");
                if(dbpass.equals(password)) {
                    // password ok
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("welcome.jsp");
                    success = true;
                }
            }
            if(!success) {
                response.sendRedirect("login.jsp");
            }
        }
        catch (SQLException e) {
            System.err.println(e);
            response.sendRedirect("login.jsp");
        }


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

