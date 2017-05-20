package Tictactoe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;

/**
 * Created by vitiv on 5/12/17.
 */
@javax.servlet.annotation.WebServlet(name = "Tictactoe")
public class Tictactoe extends javax.servlet.http.HttpServlet {
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
        // only allow logged in users
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        // is logged in
        Integer id = getGameId(request);

        Game game = null;
        if(id != null) {
            // good id so try to get game
            game = getGame(id);
        }

        // user logged in but game doesn't exist
        if(game == null) {
            // create a new one if id unspecified or wrong
            Game newGame = new Game((String) request.getSession().getAttribute("user"));
            Integer gameId = saveGame(newGame);
            response.sendRedirect("/tictactoe?id=" + gameId.toString());
            return;
        }

        // just one player
        else if(game.getPlayer2() == null) {
            // add another player
            if(!game.getPlayer1().equals(request.getSession().getAttribute("user"))) {
                game.setPlayer2((String)request.getSession().getAttribute("user"));
                saveGame(game, id);
            }
        }

        // two player game(thrid player)
        else if(!hasPermission(request, game)) {
                response.sendError(403, "You are not part of this game");
                return;
        }
        request.setAttribute("game", game);
        request.getRequestDispatcher("/tictactoe.jsp").forward(request, response);

    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // only allow logged in users
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login.jsp");
            return;
        }
        // is logged in
        Integer id = getGameId(request);

        Game game = null;http://localhost:8080/tictactoe?id=439
        if(id != null) {
            // good id so try to get game
            game = getGame(id);
        }

        if(game == null) {
            response.sendRedirect("/tictactoe");
        }
        else {
            if(game.getPlayer2() == null) {
                response.sendError(400, "Game not started yet");
                return;
            }
            if(!hasPermission(request, game)) {
                response.sendError(403, "You are not part of this game");
                return;
            }
            String rowStr = request.getParameter("row");
            String colStr = request.getParameter("col");

            Integer row = null, col = null;
            try {
                row = Integer.parseInt(rowStr);
                col = Integer.parseInt(colStr);
            } catch (Exception ignored) {}

            if(row == null || col == null || row < 1 || row > 3 || col < 1 || col > 3 || game.getCell(row, col) != null) {
                // invalid
                response.sendError(400, "Bad row/column");
                return;
            }


            Integer playerId = (request.getSession().getAttribute("user").equals(game.getPlayer1()) ? 1 : 2);
            if(game.getWinner() == null && playerId.equals(game.getCurrentPlayer())) {
                // your turn so set the variable and the game has not been won yet
                game.setCell(row, col);
                saveGame(game, id);

            }
            response.sendRedirect("/tictactoe?id=" + id.toString());
        }

    }

    private Integer getGameId(HttpServletRequest request) {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch(Exception ignored) {}
        return id;
    }

    private boolean hasPermission(HttpServletRequest request, Game game) {
        return game.getPlayer1().equals(request.getSession().getAttribute("user")) ||
                game.getPlayer2().equals(request.getSession().getAttribute("user"));
    }

    private Integer saveGame(Game newGame) {
        // save a game with a new id and return it
        try(PreparedStatement preStmt = con.prepareStatement("INSERT INTO games(player1) VALUES(NULL)", Statement.RETURN_GENERATED_KEYS)) {
            preStmt.executeUpdate();
            try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // update it with the proper id
                    return saveGame(newGame, generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Integer saveGame(Game updatedGame, Integer id) {
        // update a previous game
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE games SET player1=(SELECT id FROM users WHERE user=?), player2=(SELECT id FROM users WHERE user=?),  cell11=?, cell12=?, cell13=?, cell21=?, cell22=?, cell23=?, cell31=?, cell32=?, cell33=? WHERE id=?")) {
            preStmt.setString(1, updatedGame.getPlayer1());
            preStmt.setString(2, updatedGame.getPlayer2());

            for(int i=1; i <=3; ++i)
                for(int j=1; j <= 3; ++j) {
                    Integer cell = updatedGame.getCell(i, j);
                    preStmt.setObject(3 + (i - 1) * 3 + (j - 1), cell);
                }
            preStmt.setInt(12, id);
            preStmt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }


    private Game getGame(int id) {
        try (PreparedStatement preStmt = con.prepareStatement("SELECT u1.user as user1, u2.user as user2, cell11, cell12, cell13, cell21, cell22, cell23, cell31, cell32, cell33 FROM games AS g JOIN users AS u1 ON u1.id = g.player1 LEFT JOIN users as u2 ON u2.id = g.player2 WHERE g.id=?")) {
            preStmt.setInt(1, id);
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                String player1 = rs.getString("user1");
                String player2 = rs.getString("user2");

                Game game = new Game(player1, player2);
                for(int i = 1; i <= 3; ++i)
                    for(int j = 1; j <= 3; ++j) {
                        Integer cellValue = (Integer)rs.getObject("cell" + i + j);
                        game.setCell(i, j, cellValue);
                    }

                return game;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }
}
